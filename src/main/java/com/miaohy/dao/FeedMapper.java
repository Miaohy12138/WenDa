package com.miaohy.dao;

import com.miaohy.pojo.Feed;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FeedMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Feed record);

    int insertSelective(Feed record);

    Feed selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Feed record);

    int updateByPrimaryKey(Feed record);

    List<Feed> selectUserFeeds (@Param("maxId")int maxId, @Param("userIds") List<Integer> userIds,
                                @Param("count") int count);

}