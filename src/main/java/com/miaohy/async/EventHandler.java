/**
 * Author : MIAOHY
 * Time :2019/8/30 17:33
 * Beauty is better than ugly!
 */
package com.miaohy.async;

import java.util.List;

public interface EventHandler {
    void doHandle(EventModel model);

    List<EventType> getSupportEventType();
}
