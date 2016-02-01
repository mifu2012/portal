package com.infosmart.interceptor;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.aop.ThrowsAdvice;

/**
 * 业务层错误拦截器(不处理异常，只复杂拦截异常输出日志)
 * 
 * @author Winker.Yang
 * 
 */
public class ExceptionInterceptor implements ThrowsAdvice {
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * Owner 参数解释 Method method 执行的方法 Object[] args 方法参数 Object target 代理的目标对象
	 * Throwable throwable 产生的异常 Jan 18, 2010 3:21:46 PM
	 */
	public void afterThrowing(Method method, Object[] args, Object target,
			RuntimeException throwable) {
		// 错误信息
		StringBuffer errorMsg = new StringBuffer("");
		StackTraceElement[] stackTraces = throwable.getStackTrace();
		if (stackTraces != null) {
			for (StackTraceElement st : stackTraces) {
				errorMsg.append(st.getClassName())
						.append(".")
						.append(st.getMethodName())
						.append("(" + target.getClass().getSimpleName()
								+ ".java:" + st.getLineNumber() + ")")
						.append("\n\t");
			}
		}
		// 输出异常日志
		this.logger.error(errorMsg);
	}
}