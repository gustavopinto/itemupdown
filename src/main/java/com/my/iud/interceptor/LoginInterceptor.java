package com.my.iud.interceptor;


import com.my.iud.entity.User;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Configuration
public class LoginInterceptor extends HandlerInterceptorAdapter{
    
    @SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
    
    private @Value("#{setting['develop.taobao.callback']}") String redirectUrl;

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		User user = (User)request.getSession().getAttribute("user");
		if (user == null) {
			request.setAttribute("redirectUrl", redirectUrl);
			RequestDispatcher view = request.getRequestDispatcher("/toLogin.jsp");
			view.forward(request, response);
			return false;
		}
		String mid = (String)request.getParameter("mid");
		if(mid == null){
			mid = (String)request.getSession().getAttribute("mid");
			if(mid == null){
				mid = "11";
			}
		}
		request.getSession().setAttribute("mid", mid);
		return super.preHandle(request, response, handler);
	}
	
}
