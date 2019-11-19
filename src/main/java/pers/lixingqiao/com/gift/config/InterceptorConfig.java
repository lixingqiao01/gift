package pers.lixingqiao.com.gift.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    LogCostInterceptor logCostInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logCostInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/login")
                .excludePathPatterns("/api/regist");
    }
}
