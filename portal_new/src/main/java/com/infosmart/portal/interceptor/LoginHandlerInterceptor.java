package com.infosmart.portal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.infosmart.portal.pojo.User;
import com.infosmart.portal.util.Constants;

public class LoginHandlerInterceptor extends HandlerInterceptorAdapter {
	 private Logger logger=Logger.getLogger(this.getClass());
	public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(quit)).*";
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		this.logger.info("------->"+request.getServletPath());
		String path = request.getServletPath();
		if(path.matches(NO_INTERCEPTOR_PATH)){
			return true;
		}else{
			Object user=  request.getSession().getAttribute(Constants.SESSION_USER_INFO);
			if(user!=null){
				return true;
			} else {
				response.sendRedirect(request.getContextPath()+"/quit");
				this.logger.info("------->"+request.getContextPath());
				return false;
			}
			
	}
		
	}

}
