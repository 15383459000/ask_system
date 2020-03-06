package com.yunding.answer.service.impl;

import com.yunding.answer.dto.QuestionLibDto;
import com.yunding.answer.mapper.AskingMapper;
import com.yunding.answer.service.AskingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
