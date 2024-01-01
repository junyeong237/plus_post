package com.example.plus_assignment.global.config;

import com.example.plus_assignment.global.jwt.JwtUtil;
import com.example.plus_assignment.global.redis.RedisUtil;
import com.example.plus_assignment.global.security.JwtAuthenticationFilter;
import com.example.plus_assignment.global.security.JwtAuthorizationFilter;
import com.example.plus_assignment.global.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final RedisUtil redisUtil;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
        throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil,redisUtil);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtil, userDetailsService,redisUtil);
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf((csrf) -> csrf.disable());

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement((sessionManagement) ->
            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests((authorizeHttpRequests) ->
            authorizeHttpRequests
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // resources 접근 허용 설정
                .requestMatchers("/api/users/signup/**").permitAll()
                .requestMatchers("/api/users/login").permitAll()
                .requestMatchers("/api/email/**").permitAll()
                .requestMatchers("/api/post/**").permitAll()
                //.requestMatchers("/api/menus").permitAll()
                .anyRequest().authenticated() // 그 외 모든 요청 인증처리
        );
//        http.formLogin((formLogin) ->
//                formLogin
//                    //.loginPage("/").permitAll() // 이 경우엔 진짜 index.html로 감
//                    //.defaultSuccessUrl("/")
//                    .loginPage("/user/login-page").permitAll()
//            //로그인 인증이 필요할 때 이동하는 페이지 설정하는 api
//            //.failureUrl("/api/user/login-page?error").permitAll()
//            //.loginProcessingUrl("/user/login")
//        );
        //JwtAuthenticationFilter
        // 필터 관리
        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
