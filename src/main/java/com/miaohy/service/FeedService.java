package com.miaohy.service;

import com.miaohy.dao.FeedMapper;
import com.miaohy.pojo.Feed;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FeedService{

    @Resource
    private FeedMapper feedMapper;

    
    public int deleteByPrimaryKey(Integer id) {
        return feedMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(Feed record) {
        return feedMapper.insert(record);
    }

    
    public boolean insertSelective(Feed record) {
       feedMapper.insertSelective(record);
       return record.getId()>0;
    }

    
    public Feed selectByPrimaryKey(Integer id) {
        return feedMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(Feed record) {
        return feedMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(Feed record) {
        return feedMapper.updateByPrimaryKey(record);
    }

    public List<Feed> getUserFeeds(int maxId,List<Integer> userIds,int count){
        return feedMapper.selectUserFeeds(maxId,userIds,count);
    }

}
