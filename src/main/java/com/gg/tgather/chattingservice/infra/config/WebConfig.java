package com.gg.tgather.chattingservice.infra.config;

import com.gg.tgather.chattingservice.infra.properties.CustomProperties;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final CustomProperties customProperties;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        List<String> hosts = customProperties.getHosts();
//        String[] array = hosts.toArray(new String[0]);
//        registry.addMapping("/**")
//            .allowedOrigins(array)
//            .allowCredentials(false)
//            .maxAge(3600)
//            .allowedHeaders("Accept", "Content-Type", "Origin",
//                "Authorization", "X-Auth-Token")
//            .exposedHeaders("X-Auth-Token", "Authorization")
//            .allowedMethods("POST", "GET", "DELETE", "PUT", "OPTIONS");
    }
}