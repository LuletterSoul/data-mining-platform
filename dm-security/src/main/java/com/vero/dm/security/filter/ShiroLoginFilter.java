//package com.dm.org.security.filter;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.subject.Subject;
//import org.apache.shiro.web.servlet.AdviceFilter;
//
///**
// * @author XiangDe Liu qq313700046@icloud.com .
// * @version 1.5
// *          created in  17:58 2017/7/31.
// * @since data-minning-platform
// */
//
//public class ShiroLoginFilter extends AdviceFilter
//{
//    @Override
//    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception
//    {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//        Subject subject = SecurityUtils.getSubject();
//        if (!subject.isAuthenticated() && !StringUtils.contains(httpServletRequest.getRequestURI(), "/login"))
//        {
//            String requestedWith = httpServletRequest.getHeader("X-Requested-With");
//            if (StringUtils.isNotEmpty(requestedWith) && StringUtils.equals(requestedWith, "XMLHttpRequest")) {//如果是ajax返回指定数据
//                ResponseHeader responseHeader = new ResponseHeader();
//                responseHeader.setResponse(ResponseHeader.SC_MOVED_TEMPORARILY, null);
//                httpServletResponse.setCharacterEncoding("UTF-8");
//                httpServletResponse.setContentType("application/json");
//                httpServletResponse.getWriter().write(JSONObject.toJSONString(responseHeader));
//                return false;
//            } else {//不是ajax进行重定向处理
//                httpServletResponse.sendRedirect("/login/local");
//                return false;
//            }
//        }
//        return true;
//    }
//}
