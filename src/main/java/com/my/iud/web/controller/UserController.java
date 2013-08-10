/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.my.iud.web.controller;

import com.my.iud.dto.LogQueryParameter;
import com.my.iud.dto.TaskQueryParameter;
import com.my.iud.entity.User;
import com.my.iud.service.LogService;
import com.my.iud.service.TaskService;
import com.my.iud.service.UserService;
import com.my.iud.util.Constants;
import com.taobao.api.domain.ArticleUserSubscribe;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.guzz.util.StringUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author dell
 */
@Controller
@RequestMapping("/user")
public class UserController {
    
    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;
    @Autowired
    private TaskService taskService;
    
    @RequestMapping("/prepareClearUser")
    public String prepareClearUser(HttpServletRequest request, ModelMap modelMap) {
        return "clear_user";
    }
    
    @RequestMapping("/clearUser")
    public String clearUser(@RequestParam("userNick") String userNick, HttpServletRequest request, ModelMap modelMap) {
        User userSession = (User) request.getSession().getAttribute("user");
        String loginUserName = userSession.getUserNick();
        if (!StringUtils.isEmpty(userNick)) {
            try {
                User user = new User();
                user.setUserNick(userNick);
                user = userService.getUserByUserNick(user);
                if (user != null && "czbabl".equals(loginUserName)) {
                    TaskQueryParameter tqp = new TaskQueryParameter();
                    LogQueryParameter lqp = new LogQueryParameter();
                    tqp.setSellerId(user.getSellerId());
                    tqp.setUid(user.getId());
                    lqp.setSellerId(user.getSellerId());
                    lqp.setUid(user.getId());
                    taskService.deleteTaskBySellerId(tqp);
                    taskService.deleteGtaskBySellerId(tqp);
                    logService.delLogBySellerId(lqp);
                    logService.delGLogBySellerId(lqp);
                    logger.info("delete user sucess:" + userNick);
                }
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        } else {
            if ("czbabl".equals(loginUserName)) {
                try {
                    List<User> userList = userService.getDeadlineUser();
                    for (User user : userList) {
                        TaskQueryParameter tqp = new TaskQueryParameter();
                        LogQueryParameter lqp = new LogQueryParameter();
                        tqp.setSellerId(user.getSellerId());
                        tqp.setUid(user.getId());
                        lqp.setSellerId(user.getSellerId());
                        lqp.setUid(user.getId());
                        taskService.updateTaskStatusBySellerId(tqp);
                        taskService.updateGTaskStatusBySellerId(tqp);
                        logService.delLogBySellerId(lqp);
                        logService.delGLogBySellerId(lqp);
                        logger.info("delete user sucess:" + user.getUserNick());
                    }
                } catch (Exception ex) {
                    logger.error(ex.getMessage(), ex);
                }
            }
        }
        return "clear_user";
    }
    
    @RequestMapping("/getUserSub")
    public String getUserSub(@RequestParam("userNick") String userNick, HttpServletRequest request, ModelMap modelMap) {
        if (StringUtil.notEmpty(userNick)) {
            userNick = userNick.trim();
            User user = new User();
            user.setUserNick(userNick);
            try {
                User fUser = userService.getUserByUserNick(user);
                
                List<ArticleUserSubscribe> listAus = userService.getSubscribe(user.getUserNick(), Constants.ARTICLE_CODE);
                if (listAus != null && listAus.size() > 0) {
                    ArticleUserSubscribe aus = listAus.get(0);
                    fUser.setItemCode(aus.getItemCode());
                    fUser.setDeadline(aus.getDeadline());
                    userService.updateUser(fUser);
                    logger.info("手动更新订购日期:" + aus.getDeadline());
                } else {
                    logger.info(user.getUserNick() + "没有订购关系");
                }
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
        return "clear_user";
        
    }
}
