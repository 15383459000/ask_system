package com.yunding.answer.form;

import lombok.Data;
import org.apache.tomcat.jni.Library;

/**
 * @Author ycSong
 * @create 2020/3/8 19:42
 */
@Data
public class AnswerRecordForm {

    private String userId;

    private String usedTime;

    private String accuracy;

    private String libraryId;

    public AnswerRecordForm(String userId, String usedTime, String accuracy, String libraryId) {
        this.userId = userId;
        this.usedTime = usedTime;
        this.accuracy = accuracy;
        this.libraryId = libraryId;
    }
}
