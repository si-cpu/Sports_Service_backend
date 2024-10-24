package SportsService.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // CORS 설정을 적용할 url
                .allowedOriginPatterns("https://*", "http://*")
                .allowedMethods("*") // 요청 방식
                .maxAge(300) // 원하는 시간만큼 기존에 허락했던 요청 정보를 기억할 시간
                .allowedHeaders("*")
                .allowCredentials(true); // 요청을 허락할 헤더 정보 종류


    }
}
