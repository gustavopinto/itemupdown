package com.my.iud.service.imp;

import com.my.iud.dao.UserDAO;
import com.my.iud.entity.User;
import com.my.iud.service.UserService;
import com.taobao.api.domain.ArticleUserSubscribe;
import com.taobao.api.response.VasSubscribeGetResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDAO userDAO;

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void saveUser(User user) throws Exception {
		// TODO Auto-generated method stub
		userDAO.saveUser(user);
	}

	@Override
	public User getUser(User user) throws Exception {
		// TODO Auto-generated method stub
		return userDAO.getUserBySellerId(user);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateUser(User user) throws Exception {
		// TODO Auto-generated method stub
	    userDAO.updateUser(user);
	}

	@Override
	public User getUserBySellerId(User user) throws Exception {
		// TODO Auto-generated method stub
		return userDAO.getUserBySellerId(user);
	}

    @Override
    public List<ArticleUserSubscribe> getSubscribe(String userNick, String articleCode) throws Exception {
        VasSubscribeGetResponse response = userDAO.getSubscribe(userNick, articleCode);
        if(response.isSuccess()){
            List<ArticleUserSubscribe> ausList = response.getArticleUserSubscribes();
            return ausList;
        }else{
            return null;
        }
        
    }

    @Override
    public User getUserByUserNick(User user) throws Exception {
        return userDAO.getUserByNick(user);
    }

    @Override
    public List<User> getDeadlineUser() throws Exception {
        return userDAO.getDeadlineUser();
    }

}
