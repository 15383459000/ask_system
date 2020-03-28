package com.yunding.answer.service.impl;

import com.yunding.answer.core.exception.SysException;
import com.yunding.answer.dto.*;
import com.yunding.answer.form.*;
import com.yunding.answer.mapper.AskingMapper;
import com.yunding.answer.service.AskingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author ycSong
 * @create 2020/3/5 15:22
 */
@Service
public class AskingServiceImpl implements AskingService {

    @Autowired
    private AskingMapper askingMapper;


    @Override
    public List<QuestionLibDto> getQuestionLib() {
        return askingMapper.getQuestionLib();
    }

    @Override
    public List<AskingQuestionDto> starAsking(LibIdForm libIdForm) {

        //存放将返回的题目id
        List<String> tempIds = new ArrayList<>();
        //获取题集包含题目
        List<String> qids = askingMapper.getQuestionsFromLib(libIdForm);
        //检查该题库题目是否超过10个
        if (qids.size()<10){
            throw new SysException("该题集题目不超过10个");
        }
        //随机出10个数字
        for (int i1 = 0 ; i1<10;i1++){
            //检验该数是否出现过了
            int t;
            while (true) {
                t = (int) (Math.abs(System.currentTimeMillis() % qids.size()));
                int i3 = 0;
                for ( ;i3<tempIds.size();i3++){
                    if (tempIds.get(i3).equals(qids.get(t))){
                        break;
                    }
                }
                //如果没有重复的跳出循环
                if (i3==tempIds.size()){
                    break;
                }
            }
            //将随机数添加进去
            tempIds.add(qids.get(t));

        }

        return askingMapper.getQuestionsInfo(tempIds);
    }

    /**
     * 开始闯关
     * @param libIdForm
     * @return
     */
    public AskingQuestionDto starStage(LibIdForm libIdForm){
        //获取题集包含题目
        List<String> qids = askingMapper.getNorQuesFromLib(libIdForm);
        //检查该题库题目是否超过1个
        if (qids.size()<1){
            throw new SysException("该题集不能闯关");
        }
        //随机出一个题号
        int t = (int)(Math.abs(System.currentTimeMillis() % qids.size()));
        System.out.println();
        return askingMapper.getQuestionInfo(qids.get(t));

    }

    //进行事务管理
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor=Exception.class)
    public String judgePaper(AnswerForm answerForm,String userId) {

        //正确题数
        int numOfRight = 0;
        //错题id集合
        List<WrongQuestionForm> wrongQidAndAnswers = new ArrayList<>();
        //建立答题记录详情表单集合
        List<AnswerRecordInfoForm> answerRecordInfoForms =
                new ArrayList<>();
        for (int i = 0 ; i<10;i++){
            boolean isTrue = false;
            //通过题目id获取答案
            String questionAnswer = askingMapper.getAnswerById(answerForm.getQuestionIdList().get(i));
            //判断用户答案是否正确
            if (questionAnswer==null
                    ||questionAnswer.equals("")
                    ||questionAnswer.equals(answerForm.getUserAnswerList().get(i))){
                isTrue = true;
                numOfRight++;
            } else {
                //如果错误，添加到错题集合
                wrongQidAndAnswers.add(new WrongQuestionForm(answerForm.getUserAnswerList().get(i),
                        answerForm.getUserAnswerList().get(i)));
            }
            //根据获取的信息去填充答题记录详情表单集合
            answerRecordInfoForms.add(
                    new AnswerRecordInfoForm(answerForm.getQuestionIdList().get(i),
                            isTrue,answerForm.getUserAnswerList().get(i)));
        }
        //构建答题记录表单对象
        AnswerRecordForm answerRecordForm =
                new AnswerRecordForm(userId,
                        answerForm.getUsedTime(),numOfRight+"0%",
                        answerForm.getLibraryId());
        //插入答题记录
        askingMapper.insertAskingRecord(answerRecordForm);
        //查询该用户新生成的答题记录id
        String newRecordId = askingMapper.getNewRecordId(userId);
        //插入答题记录详情
        askingMapper.insertAskingRecordInfo(answerRecordInfoForms,newRecordId);
        //获取总做题量
        Integer totalExiceNum = askingMapper.getTotalAskNum(userId);
        //每日学习时间
        Integer dailyTime = askingMapper.getDailyTime(userId);
        //更新每日做题时间以及总做题量
        //如果每日做题时间为空跳过
        if (dailyTime!=null) {
            askingMapper.updateDailyTime(userId,
                    String.valueOf(dailyTime + Integer.parseInt(answerForm.getUsedTime())));
        }
        askingMapper.updateTotalAskNum(userId,
                String.valueOf(totalExiceNum+10));
        //创建错题的次数和id集合
        List<QidAndWrongTimeDto> qidAndWrongTimeDtos = askingMapper.getQidAndWrongTime(userId);
        //查找是否已经错过，已经错过的更新字段，没有的插入
        for (int i = 0;i<wrongQidAndAnswers.size();i++) {
            boolean isExist = false;
            for (int t = 0;t<qidAndWrongTimeDtos.size();t++){
                //如果错题集中已经存在该题更新字段
                if (wrongQidAndAnswers.get(i).getQuestionId().equals(
                        qidAndWrongTimeDtos.get(t).getQuestionId())
                ){
                    askingMapper.updateWrongTime(qidAndWrongTimeDtos.get(t).getWrongTime(),
                            qidAndWrongTimeDtos.get(t).getQuestionId(),userId);
                    isExist = true;
                }
            }
            if (!isExist){
                askingMapper.insertWrongQuestionsRecord(wrongQidAndAnswers.get(i),
                        userId,answerForm.getLibraryId());
            }
        }

        //返回新生成的记录id
        return newRecordId;
    }

    /**
     * 闯关判卷
     * @param stageAnswerForm
     * @param userId
     * @return
     */
    public boolean judgeStage(StageAnswerForm stageAnswerForm,String userId){
        //获取题目答案
        String answer =  askingMapper.getAnswerById(stageAnswerForm.getQuestionId());
        //判断是否对
        if (answer.equals(stageAnswerForm.getUserAnswer())){
            String stageNum = askingMapper.getStageNum(userId);
            if (stageNum==null){
                askingMapper.insertStageNum(userId);
            }else {
                askingMapper.updateStageNum(userId,stageNum);
            }
            return true;
        }else {
            return false;
        }
    }

    @Override
    public AnswAndAnalyDto getAnswAndAna(String questionId) {
        return askingMapper.getAnswerAndAnalysis(questionId);
    }

    @Override
    public List<AnswerRecordDto> getAnswerRecord(RecordByTimeForm recordByTimeForm,String userId) {
        //通过时间段获取答题记录
        return askingMapper.getAnswerRecord(
                Long.valueOf(recordByTimeForm.getStartTime()),Long.valueOf(recordByTimeForm.getStopTime())
                ,userId);
    }

    @Override
    public List<AnswerRecordInfoDto> getAnswRecorInfo(String answerId) {
        return askingMapper.getAnswRecoInfo(answerId);
    }

    /**
     * 获取题目 - 快速刷题（选择填空）
     * @param practiceForm
     * @return
     */
    @Override
    public List<QuickPracticeDto> getQuickPracticeList(PracticeForm practiceForm) {
        int libraryId = practiceForm.getLibraryId();
        return askingMapper.selectQuickPracticeList(libraryId);
    }

    /**
     * v获取题目 - 快速刷题（问答）
     * @param practiceForm
     * @return
     */
    @Override
    public List<AskPracticeDto> getAskPracticeList(PracticeForm practiceForm) {
        int libraryId = practiceForm.getLibraryId();
        return askingMapper.selectAskPracticeList(libraryId);
    }

    /**
     * 练习题 - 判卷
     * @param checkAnswersForm
     * @param userId
     * @return
     */
    @Override
    public List<CheckAnswersDto> checkAnswers(CheckAnswersForm checkAnswersForm, String userId){

        // 创建用于向前端返回信息的 list 列表
        List<CheckAnswersDto> list = new ArrayList<>();

        // 检验答案是否正确
        for (int i = 0; i < 10; i++) {
            CheckAnswersDto dto = new CheckAnswersDto();
            // 获取题目 id
            String questionId = checkAnswersForm.getQuestionIdList().get(i);
            // 通过题目id获取答案
            String answer = askingMapper.selectAnswerById(questionId);
            // 获取用户答案
            String userAnswer = checkAnswersForm.getUserAnswerList().get(i);
            // 判断用户答案是否正确
            if (answer.equals(userAnswer)) {
                dto.setIsRight("1");
            } else {
                dto.setIsRight("0");
            }
            // 设置返回值
            dto.setQuestionId(questionId);
            System.out.println(dto);
            // 补充返回列表
            list.add(dto);
        }

        // 增加做题量
        try {
            askingMapper.updateExercisesQuantity(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 增加学习天数
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(new Date());
        Date updateDate = askingMapper.selectLearningDaysUpdateTime(userId);
        String updateTime = df.format(updateDate);
        if (!date.equals(updateTime)) {
            try {
                askingMapper.updateLearningDays(userId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 返回信息
        System.out.println(list);
        return list;

    }

}
