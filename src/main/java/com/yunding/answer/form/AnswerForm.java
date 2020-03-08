package com.yunding.answer.form;

import lombok.Data;

import java.util.List;

/**
 * @Author ycSong
 * @create 2020/3/8 14:54
 */

@Data
public class AnswerForm {

    //题目id
    private List<String> questionIdList;
    //用户答案集合
    private List<String> userAnswerList;
    //答题用时
    private String usedTime;
    //题集id
    private String libraryId;

}
