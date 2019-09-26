/**
 * Author : MIAOHY
 * Time :2019/9/1 9:22
 * Beauty is better than ugly!
 */
package com.miaohy.async.handler;

import com.miaohy.async.EventHandler;
import com.miaohy.async.EventModel;
import com.miaohy.async.EventType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LoginExceptionHandler implements EventHandler {
    @Autowired

    @Override
    public void doHandle(EventModel model) {

    }

    @Override
    public List<EventType> getSupportEventType() {
        return null;
    }
}
