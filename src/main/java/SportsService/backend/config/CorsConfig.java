package SportsService.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring 애플리케이션에서 Cross-Origin Resource Sharing(CORS)을 설정하는 구성 클래스.
 * {@link WebMvcConfigurer} 인터페이스를 구현하여 CORS 설정을 커스터마이징한다.
 *
 * @since 2024-10-23
 * @author minus43
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * 애플리케이션에 대한 CORS 설정을 구성한다.
     * HTTP 및 HTTPS를 포함한 모든 출처에서 API에 접근할 수 있도록 허용한다.
     *
     * @param registry {@link CorsRegistry} 객체로, CORS 매핑을 정의하는 데 사용된다.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 URL 패턴에 대해 CORS 설정 적용
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("*") // 모든 HTTP 메서드 허용 (GET, POST, PUT, DELETE 등)
                .maxAge(300) // 사전 요청 결과를 300초 동안 캐시
                .allowedHeaders("*") // 모든 요청 헤더 허용
                .allowCredentials(true); // 자격 증명(쿠키, 인증 헤더 등)을 포함한 요청 허용
    }
}
