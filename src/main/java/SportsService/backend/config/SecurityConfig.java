package SportsService.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // 시큐리티 기본 설정 (권한 처리, 초기 로그인 화면 없애기 등등...)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 스프링 시큐리티에서 기본으로 제공하는 CSRF 토큰 공격을 방지하기 위한 장치 해제.
        // CSRF(Cross Site Request Forgery) 사이트 간 요청 위조
        http.csrf(csrfConfig -> csrfConfig.disable());

        http.authorizeHttpRequests(
                // 모든 요청에 대해 인증을 하지 않겠다.
                auth -> auth.requestMatchers("/**").permitAll()
        );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
