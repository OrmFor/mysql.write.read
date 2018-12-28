package com.test.springboot.config.common;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

   /* @Bean
    SecurityInterceptor securityInterceptor() {
        return new SecurityInterceptor();
    }*/

   /* public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityInterceptor()).addPathPatterns("/payapi/3.0/**").excludePathPatterns("/payapi/3.0/extend/notify")
                .excludePathPatterns("/payapi/3.0/prepay/notify").excludePathPatterns("/payapi/3.0/bankrepay/notify");
    }
*/  
   
   /**
   * @Description  用这个能够用fastjson输出成json格式
   **/
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter4 converter = new FastJsonHttpMessageConverter4();
        converters.add(converter);
    }
}
