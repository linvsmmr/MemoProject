package com.eunbi.memo.config;

import com.eunbi.memo.common.FileManager;
import com.eunbi.memo.intercepter.PermissionInterceptor;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private PermissionInterceptor interceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:///" + FileManager.FILE_UPLOAD_PATH);

    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/**").excludePathPatterns("/user/logout", "/css/**");
    }
}
