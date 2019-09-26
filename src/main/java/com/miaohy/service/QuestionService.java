/**
 * Author : MIAOHY
 * Time :2019/7/17 14:43
 * Beauty is better than ugly!
 */
package com.miaohy.service;

import com.miaohy.dao.QuestionMapper;
import com.miaohy.pojo.Question;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QuestionService {
    @Resource
    private QuestionMapper questionMapper;

    public int deleteByPrimaryKey(Integer id) {
        return questionMapper.deleteByPrimaryKey(id);
    }

    public int insert(Question record) {
        return questionMapper.insert(record);
    }

    public int insertSelective(Question record) {
        return questionMapper.insertSelective(record);
    }

    public Question selectByPrimaryKey(Integer id) {
        return questionMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Question record) {
        return questionMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Question record) {
        return questionMapper.updateByPrimaryKey(record);
    }

    public List<Question> selectLatestQuestions(int userId, int offset, int limit) {
        return questionMapper.selectLatestQuestions(userId, offset, limit);
    }


}


