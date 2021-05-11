package com.tedu.filter;

import javax.servlet.*;
import java.io.IOException;

public class CharacterFilter implements Filter {
    private static String charset;
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        req.setCharacterEncoding(charset);//硬编码
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        charset = config.getInitParameter("charset");
        if(charset == null || charset.equals("")) {
            charset = "utf-8";
        }
    }

}
