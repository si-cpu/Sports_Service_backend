package SportsService.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 설정을 위한 구성 클래스입니다.
 * 이 클래스에서는 보안 필터 체인을 구성하고, 비밀번호 암호화를 위한 PasswordEncoder를 설정합니다.
 *
 * @author minus43
 * @since 2024-10-23
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * 스프링 시큐리티의 보안 필터 체인을 설정하는 메서드입니다.
     * CSRF 보호를 비활성화하고, 모든 요청을 인증 없이 허용하는 설정을 구성합니다.
     *
     * @param http 스프링 시큐리티의 HTTP 보안 설정을 위한 객체
     * @return SecurityFilterChain 구성된 보안 필터 체인 객체로, 애플리케이션의 보안 정책을 적용합니다
     * @throws Exception 보안 구성 중 발생할 수 있는 인증/인가 관련 예외
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CSRF 공격 방지 비활성화
        http.csrf(csrfConfig -> csrfConfig.disable())
                .cors(Customizer.withDefaults());

        // 모든 요청에 대해 인증을 요구하지 않음
        http.authorizeHttpRequests(
                auth -> auth.requestMatchers("/**").permitAll()
        );

        return http.build();
    }

    /**
     * 비밀번호 암호화를 위한 BCryptPasswordEncoder 빈을 생성합니다.
     * 회원가입 시 비밀번호를 암호화하는 데 사용됩니다.
     *
     * @return PasswordEncoder BCrypt 알고리즘을 사용하는 비밀번호 암호화 인코더
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
