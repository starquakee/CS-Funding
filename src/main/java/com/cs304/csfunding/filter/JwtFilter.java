package com.cs304.csfunding.filter;

import com.auth0.jwt.interfaces.Claim;
import com.cs304.csfunding.util.JwtUtil;
import org.slf4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import com.cs304.csfunding.util.HttpContextUtil;

@WebFilter(filterName = "jwtFilter", urlPatterns = "/*")
public class JwtFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.setCharacterEncoding("UTF-8");
        //获取header里的token
        String token = request.getHeader("authorization");
        if (this.bypassUri(request.getRequestURI())) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        } else if ("OPTIONS".equals(request.getMethod())) {              //除了 OPTIONS请求以外, 其它请求应该被JWT检查
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(request, response);
        } else {
            if (token == null) {
                HttpContextUtil.setReturn(response, 401, "no token");
                return;
            }
        }

        Map<String, Claim> userData = JwtUtil.verifyToken(token);

        if (userData == null) {
            HttpContextUtil.setReturn(response, 401, "invalid token");
            return;
        }
        Integer uuid = userData.get("uuid").asInt();
        String name = userData.get("name").asString();
        //拦截器 拿到用户信息，放到request中
        request.setAttribute("uuid", uuid);
        request.setAttribute("name", name);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean bypassUri(String uri){
        if (uri.equals("/api/login")) return true;
        else if (uri.startsWith("/register/")) return true;
        else return false;
    }
}
