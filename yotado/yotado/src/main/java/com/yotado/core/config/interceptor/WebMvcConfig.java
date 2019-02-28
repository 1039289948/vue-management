package com.yotado.core.config.interceptor;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import java.util.List;

/**
 * @author wangle
 * @date 2018/11/29
 * @description 自定义WebMvcConfigurer
 */

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(signInterceptor()).addPathPatterns("/**"); // 拦截所有请求，通过判断是否有 @SignRequired 注解 决定是否需要签名
        registry.addInterceptor(wxLoginInterceptor()).addPathPatterns("/**"); // 拦截所有请求，通过判断是否有 @WxLoginRequired 注解 决定是否需要签名
        registry.addInterceptor(webLoginInterceptor()).addPathPatterns("/**"); // 拦截所有请求，通过判断是否有 @WebLoginRequired 注解 决定是否需要签名
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentWxUserResolver());
        argumentResolvers.add(currentWebUserResolver());
        argumentResolvers.add(sessionResolver());
    }

    @Bean
    public CurrentWxUserResolver currentWxUserResolver() {
        return new CurrentWxUserResolver();
    }

    @Bean
    public CurrentWebUserResolver currentWebUserResolver() {
        return new CurrentWebUserResolver();
    }

    @Bean
    public SessionResolver sessionResolver() {
        return new SessionResolver();
    }

    @Bean
    public SignInterceptor signInterceptor() {
        return new SignInterceptor();
    }

    @Bean
    public WxLoginInterceptor wxLoginInterceptor() {
        return new WxLoginInterceptor();
    }

    @Bean
    public WebLoginInterceptor webLoginInterceptor() {
        return new WebLoginInterceptor();
    }

    /*@Bean
    public RequestRecorder requestRecorder() {
        return new RequestRecorder();
    }*/

    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        return new HttpMessageConverters((HttpMessageConverter<?>) fastConverter);
    }

}
