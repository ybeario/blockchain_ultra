package com.cqupt.bear.blockchain.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Y.bear
 * @version 创建时间：2018年11月27日 下午3:52:02 类说明
 * addViewControllers可以方便的实现一个请求直接映射成视图，而无需书写controller
 * registry.addViewController("请求路径").setViewName("请求页面文件路径")
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/error").setViewName("error");
        registry.addViewController("/success").setViewName("success");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/admin").setViewName("admin/adminIndex");
        registry.addViewController("/admin/rightsManagementPage").setViewName("admin/rightsManagement");
        registry.addViewController("/researcher").setViewName("researcher/researcherIndex");
        registry.addViewController("/user").setViewName("user/userIndex");
        registry.addViewController("/user/upload").setViewName("user/upload");
        registry.addViewController("/user/queryPage").setViewName("user/query");
        registry.addViewController("/researcher/upload").setViewName("researcher/uploadAnalysisResult");
        registry.addViewController("/researcher/acquireKeyPage").setViewName("researcher/acquireKeyPage");
        registry.addViewController("/researcher/acquireEvidencePage").setViewName("researcher/acquireEvidence");
        registry.addViewController("/researcher/uploadResultPage").setViewName("researcher/uploadAnalysisResult");
    }
}
