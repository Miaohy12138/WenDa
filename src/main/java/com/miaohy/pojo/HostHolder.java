/**
 * Author : MIAOHY
 * Time :2019/7/25 11:08
 * Beauty is better than ugly!
 */
package com.miaohy.pojo;

import org.springframework.stereotype.Component;

@Component
public class HostHolder {
    private static ThreadLocal<User> users = new ThreadLocal<>();


    public User getUser() {
        return users.get();
    }

    public void setUser(User user) {
        users.set(user);
    }

    public void clear() {
        users.remove();
    }
}
