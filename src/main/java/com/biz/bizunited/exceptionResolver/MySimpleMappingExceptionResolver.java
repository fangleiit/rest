/**
 * @Description: 
 * @ClassName: com.biz.centerdocker.common.handler.exceptionResolver.SimpleMappingExceptionResolver
 * @author: Omar(OmarZhang)
 * @date: 2016年1月13日 下午1:26:42 
 */
package com.biz.bizunited.exceptionResolver;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * @Description:
 * @ClassName: com.biz.centerdocker.common.handler.exceptionResolver.
 *             SimpleMappingExceptionResolver
 * @author: Omar(OmarZhang)
 * @date: 2016年1月13日 下午1:26:42
 *
 */
public class MySimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,Exception ex) {
		// Expose ModelAndView for chosen error view.
		String viewName = determineViewName(ex, request);
		if (viewName != null) {// JSP格式返回
			if (!(request.getHeader("accept").indexOf("application/json") > -1
					|| (request.getHeader("X-Requested-With") != null
					&& request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
				// 如果不是异步请求
				// Apply HTTP status code for error views, if specified.
				// Only apply it if we're processing a top-level request.
				Integer statusCode = determineStatusCode(request, viewName);
				if (statusCode != null) {
					applyStatusCodeIfPossible(request, response, statusCode);
				}
				return getModelAndView(viewName, ex, request);
			} else {// JSON格式返回
				return null;

			}
		} else {
			return null;
		}
	}
}
