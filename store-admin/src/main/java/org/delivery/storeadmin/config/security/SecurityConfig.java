package org.delivery.storeadmin.config.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private List<String> SWAGGER = List.of(
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    );

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                PathRequest.toStaticResources().atCommonLocations()
                        ).permitAll() // 정적 리소스에 대한 모든 요청 허용

                        // swagger 경로는 인증 없이 허용
                        .requestMatchers(
                                SWAGGER.toArray(new String[0])
                        ).permitAll()

                        // open-api/** 경로에 대한 모든 요청을 인증 없이 허용
                        .requestMatchers("/open-api/**").permitAll()

                        // 그 외의 모든 요청은 인증 필요
                        .anyRequest().authenticated()
                )
                .formLogin(withDefaults()); // 기본 폼 로그인 사용

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        // BCrypt를 사용하여 비밀번호 인코딩
        return new BCryptPasswordEncoder();
    }

}
