package com.my.iud.service;

import com.my.iud.entity.User;
import com.taobao.api.domain.ArticleUserSubscribe;
import java.util.List;

public interface UserService {

    void saveUser(User user) throws Exception;

    User getUser(User user) throws Exception;

    User getUserByUserNick(User user) throws Exception;

    void updateUser(User user) throws Exception;

    User getUserBySellerId(User user) throws Exception;

    List<ArticleUserSubscribe> getSubscribe(String userNick, String articleCode) throws Exception;

    List<User> getDeadlineUser() throws Exception;
}
