package com.linger.example.common.service;

import com.linger.example.common.model.User;

/**
 * @author linger
 * @date 2024/3/21 16:46
 */
public interface UserService {

    /**
     * 获取用户
     *
     * @param user
     * @return
     */
    User getUser(User user);

    /**
     * 新方法 - 获取数字
     * @return
     */
    default Integer getNumber() {
        return 1;
    }
}
