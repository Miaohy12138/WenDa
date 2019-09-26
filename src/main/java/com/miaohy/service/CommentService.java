package com.miaohy.service;

import com.miaohy.dao.CommentMapper;
import com.miaohy.pojo.Comment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommentService{

    @Resource
    private CommentMapper commentMapper;

    
    public int deleteByPrimaryKey(Integer id) {
        return commentMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(Comment record) {
        return commentMapper.insert(record);
    }

    
    public int insertSelective(Comment record) {
        return commentMapper.insertSelective(record);
    }

    
    public Comment selectByPrimaryKey(Integer id) {
        return commentMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(Comment record) {
        return commentMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(Comment record) {
        return commentMapper.updateByPrimaryKey(record);
    }

    public int getCommentCount(int entityId,int entityType){return commentMapper.getCommentCount(entityId,entityType);}

    public List<Comment> getCommentList(Comment comment){return commentMapper.getCommentList(comment);}
}
