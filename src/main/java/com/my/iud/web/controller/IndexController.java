package com.my.iud.web.controller;

import com.my.iud.dto.TaskQueryParameter;
import com.my.iud.dto.TopParameter;
import com.my.iud.entity.Task;
import com.my.iud.entity.User;
import com.my.iud.service.TaskService;
import com.my.iud.service.UserService;
import com.my.iud.util.Constants;
import com.my.iud.util.ItemsCodePower;
import com.my.iud.util.Util;
import com.taobao.api.domain.ArticleUserSubscribe;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.guzz.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Configuration
public class IndexController {

    private final static Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;
    private @Value("#{setting['develop.taobao.callback']}")
    String redirectUrl;

    @RequestMapping(value = "/index")
    public String index(TopParameter topParameter, HttpServletRequest request, ModelMap modelMap) {
        String topParameters = topParameter.getTop_parameters();
        String topSession = topParameter.getTop_session();

        if (StringUtil.notEmpty(topParameters) && StringUtil.notEmpty(topSession)) {
            try {
                Map<String, String> map = Util.convertBase64StringtoMap(topParameters.trim(), null);
                if (map == null) {
                    logger.info("Base64StringtoMap is null");
                    return "redirect:" + redirectUrl;
                }
                String userNick = (String) map.get("visitor_nick");
                String userId = (String) map.get("visitor_id");
                String expiresIn = (String) map.get("expires_in");
                String w1ExpiresIn = (String) map.get("w1_expires_in");
                String sessionKey = topParameter.getTop_session();

                User user = new User();
                user.setUserNick(userNick);
                user.setSellerId(Long.valueOf(userId));
                user.setSessionKey(sessionKey);

                Date date = Calendar.getInstance().getTime();
                user.setCreateTime(date);
                user.setModifyTime(date);

                User findUser = userService.getUser(user);

                List<ArticleUserSubscribe> listAus = userService.getSubscribe(user.getUserNick(), Constants.ARTICLE_CODE);
                if (listAus != null && listAus.size() > 0) {
                    ArticleUserSubscribe aus = listAus.get(0);
                    user.setItemCode(aus.getItemCode());
                    user.setDeadline(aus.getDeadline());
                    logger.info("订购到期日期:" + aus.getDeadline());
                    if (aus.getDeadline().before(date)) {
                        return "redirect:" + Constants.FUWU;
                    }
                } else {
                    logger.info(user.getUserNick() + "没有订购关系");
                }

                logger.info("用户登录:" + userNick + "------" + sessionKey + "------" + expiresIn + "------" + w1ExpiresIn);
                if (findUser == null) {
                    userService.saveUser(user);
                } else {
                    user.setId(findUser.getId());
                    user.setCreateTime(findUser.getCreateTime());
                    userService.updateUser(user);
                }

                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("role", ItemsCodePower.getPower(user.getItemCode()));

                TaskQueryParameter taskQueryParameter = new TaskQueryParameter();
                taskQueryParameter.setUid(user.getId());
                taskQueryParameter.setSellerId(user.getSellerId());
                List<Task> taskList = taskService.getTaskListBySellerId(taskQueryParameter);
                modelMap.put("taskList", taskList);

            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                logger.error(e.getMessage(), e);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                logger.error(e.getMessage(), e);
            }

        } else {
            logger.info("topsession and topParameters is null");
            return "redirect:" + redirectUrl;
        }

        return "index_n";
    }

    @RequestMapping(value = "/exit")
    public String exitls(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:" + redirectUrl;
    }
}
