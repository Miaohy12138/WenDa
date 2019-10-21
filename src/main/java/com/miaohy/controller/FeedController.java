/**
 * Author : MIAOHY
 * Time :2019/10/20 12:42
 * Beauty is better than ugly!
 */
package com.miaohy.controller;

import com.miaohy.pojo.EntityType;
import com.miaohy.pojo.Feed;
import com.miaohy.pojo.HostHolder;
import com.miaohy.service.FeedService;
import com.miaohy.service.FollowService;
import com.miaohy.utils.JedisAdapter;
import com.miaohy.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FeedController {
    @Autowired
    FeedService feedService;
    @Autowired
    FollowService followService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    JedisAdapter jedisAdapter;

    @RequestMapping("/pushfeeds")
    private String getPushFeeds(Model model){
        int localUserId = hostHolder.getUser()!=null ? hostHolder.getUser().getId() :0;
        List<String> feedIds = jedisAdapter.lrange(RedisKeyUtil.getTimelineKey(localUserId),0,10);
        List<Feed> feeds = new ArrayList<>();
        for(String feedId :feedIds){
            Feed feed = feedService.selectByPrimaryKey(Integer.parseInt(feedId));
            if(feed!=null){
                feeds.add(feed);
            }
        }
        model.addAttribute("feeds",feeds);
        return "feeds";
    }

    @RequestMapping("/pullfeeds")
    private String getPullFeeds(Model model){
        int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;
        List<Integer> followees = new ArrayList<>();
        if(localUserId!=0){
            followees = followService.getFollowees(localUserId, EntityType.ENTITY_USER,Integer.MAX_VALUE);
        }
        List<Feed> feeds = feedService.getUserFeeds(Integer.MAX_VALUE,followees,10);
        model.addAttribute("feeds",feeds);
        return "feeds";
    }


}
