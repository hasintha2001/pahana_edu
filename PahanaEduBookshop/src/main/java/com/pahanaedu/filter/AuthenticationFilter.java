package com.pahanaedu.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {
    
    public void init(FilterConfig filterConfig) throws ServletException {}
    
    public void doFilter(ServletRequest request, ServletResponse response, 
                        FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        
        String loginURI = httpRequest.getContextPath() + "/login";
        String requestURI = httpRequest.getRequestURI();
        
        boolean loggedIn = session != null && session.getAttribute("user") != null;
        boolean loginRequest = requestURI.equals(loginURI) || 
                              requestURI.endsWith("login.jsp");
        boolean resourceRequest = requestURI.contains("/css/") || 
                                 requestURI.contains("/js/") || 
                                 requestURI.contains("/images/");
        
        if (loggedIn || loginRequest || resourceRequest) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendRedirect(loginURI);
        }
    }
    
    public void destroy() {}
}