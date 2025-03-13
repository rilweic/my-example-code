package com.bigdata.graphqldemov2.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 使用lambda表达式禁用CSRF
                .csrf(csrf -> csrf.disable())
                // 请求授权配置
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/graphql/**", "/graphiql/**").permitAll()
                        .anyRequest().authenticated()
                )
                // 基本认证配置
                .httpBasic(httpBasic -> {});

        return http.build();
    }
}
