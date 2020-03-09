package com.yunding.answer.service.impl;

import com.yunding.answer.core.exception.SysException;
import com.yunding.answer.dto.AskingQuestionDto;
import com.yunding.answer.dto.DailyTimeAndTotalAskNumDto;
import com.yunding.answer.dto.QidAndWrongTimeDto;
import com.yunding.answer.dto.QuestionLibDto;
import com.yunding.answer.form.*;
import com.yunding.answer.mapper.AskingMapper;
import com.yunding.answer.service.AskingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        tempIds.add(String.valueOf(System.currentTimeMillis()%qids.size()));
        for (int i1 = 0 ; i1<10;i1++){
            //检验该数是否出现过了
            String t;
            while (true) {
                t = String.valueOf(System.currentTimeMillis() % qids.size());
                int i3 = 0;
                for ( ;i3<tempIds.size();i3++){
                    if (tempIds.get(i3).equals(t)){
                        break;
                    }
                }
                //如果没有重复的跳出循环
                if (i3==tempIds.size()){
                    break;
                }
            }
            //将随机数添加进去
            tempIds.add(t);


        }

        return askingMapper.getQuestionsInfo(tempIds);
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
        //获取总做题量和每日学习时间
        DailyTimeAndTotalAskNumDto dailyTimeAndTotalAskNumDto =
                askingMapper.getDailyTimeAndTotalAskNum(userId);
        //改写获取的做题量与每日学习时间
        dailyTimeAndTotalAskNumDto.setTotalExercisesQuantity(dailyTimeAndTotalAskNumDto.getTotalExercisesQuantity()+10);
        dailyTimeAndTotalAskNumDto.setDailyStudyTime(dailyTimeAndTotalAskNumDto.getDailyStudyTime()
                +Integer.valueOf(answerForm.getUsedTime()));
        //更新每日做题时间以及总做题量
        askingMapper.updateDailyTime(userId,
                String.valueOf(dailyTimeAndTotalAskNumDto.getDailyStudyTime()));
        askingMapper.updateTotalAskNum(userId,
                String.valueOf(dailyTimeAndTotalAskNumDto.getTotalExercisesQuantity()));
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

//        for (int i = 0;i<qidAndWrongTimeDtos.size()){
//            boolean isExist = false;
//            for (WrongQuestionForm i2:wrongQidAndAnswers){
//                if (i1.getQuestionId().equals(i2.getQuestionId())){
//                    isExist = true;
//                    askingMapper.insertWrongQuestionsRecord(i2,userId,
//                            answerForm.getLibraryId());
//                }
//            }
//            if (isExist = true){
//                askingMapper.updateWrongTime
//                        (i1.getWrongTime(),i1.getQuestionId(),userId);
//            }
//        }
        //返回新生成的记录id
        return newRecordId;
    }
}
