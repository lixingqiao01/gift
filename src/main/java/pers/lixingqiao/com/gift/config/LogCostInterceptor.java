package pers.lixingqiao.com.gift.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import pers.lixingqiao.com.gift.until.JwtUntil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Service
public class LogCostInterceptor implements HandlerInterceptor {
//    long start = System.currentTimeMillis();

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
//        System.out.println(httpServletRequest.getParameter("username"));
        System.out.println(httpServletRequest.getRequestURL());
        System.out.println(httpServletRequest.getRequestURI());
        String token = httpServletRequest.getHeader("authorization");
        if (JwtUntil.userVerify(token)) {
            return true;
        }
        PrintWriter writer = null;
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        try {
            writer = httpServletResponse.getWriter();
            JSONObject res = new JSONObject();
            res.put("status",403);
            res.put("msg","没有权限访问");
            res.put("response", "");
            res.put("ok",true);
            res.put("success",false);
            writer.append(res.toString());
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object, @Nullable ModelAndView modelAndView) throws Exception{

        System.out.println("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

        System.out.println("afterCompletion");
    }
}
