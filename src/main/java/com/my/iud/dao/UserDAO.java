package com.my.iud.dao;

import com.my.iud.entity.User;
import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.VasSubscribeGetRequest;
import com.taobao.api.response.VasSubscribeGetResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.guzz.orm.se.SearchExpression;
import org.guzz.orm.se.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("userDAO")
public class UserDAO extends SpGuzzBaseDao {

    @Autowired
    private TaobaoClient taoBaoClient;

    public void saveUser(User user) throws Exception {
        super.insert(user);
    }

    public User getUserByNick(User user) throws Exception {
        SearchExpression se = SearchExpression.forClass(User.class);
        se.and(Terms.eq("userNick", user.getUserNick()));
        return (User) super.findObject(se);
    }

    public User getUserBySellerId(User user) throws Exception {
        SearchExpression se = SearchExpression.forClass(User.class);
        se.and(Terms.eq("sellerId", user.getSellerId()));
        return (User) super.findObject(se);
    }

    public void updateUser(User user) throws Exception {
        super.update(user);
    }

    public List<User> getDeadlineUser() throws Exception {
        SearchExpression se = SearchExpression.forLoadAll(User.class);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        se.and(Terms.smallerOrEq("deadline", dateFormat.format(new Date())));
        return super.list(se);
    }

    public VasSubscribeGetResponse getSubscribe(String userNick,
            String articleCode) throws ApiException {
        VasSubscribeGetRequest req = new VasSubscribeGetRequest();
        req.setNick(userNick);
        req.setArticleCode(articleCode);
        VasSubscribeGetResponse response = taoBaoClient.execute(req);
        return response;
    }
}
