package hieu.shopappudemyhoang.config;

import hieu.shopappudemyhoang.filter.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
@Slf4j
public class WebSecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(request -> {
                    request.requestMatchers("/api/v1/users/**").permitAll()
                            .requestMatchers(HttpMethod.POST,"/api/v1/categories/**").hasAnyAuthority("ADMIN")
                            .anyRequest().authenticated();
                })
                .exceptionHandling(handling -> handling
                    .accessDeniedHandler((request, response, accessDeniedException) -> {
                        // Log the denied access details
                        System.out.println("Access Denied: " + accessDeniedException.getMessage());
                        System.out.println("User: " + request.getUserPrincipal());
                        // You can add more logging here
                    })
                )
                .build();
    }
}
