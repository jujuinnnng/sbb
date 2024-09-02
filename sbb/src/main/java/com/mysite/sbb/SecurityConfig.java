package com.mysite.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration //스프링의 환경 설정 파일
@EnableWebSecurity //모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만드는 애너테이션
public class SecurityConfig {
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
		    .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
		        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
		    .csrf((csrf) -> csrf
	                .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
		    .headers((headers) -> headers
	                .addHeaderWriter((HeaderWriter) new XFrameOptionsHeaderWriter(
	                    XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
		    .formLogin((formLogin) -> formLogin
	                .loginPage("/user/login")
	                .defaultSuccessUrl("/"))
		    .logout((logout) -> logout
	                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
	                .logoutSuccessUrl("/")
	                .invalidateHttpSession(true)) //로그아웃 시 생성된 사용자 세션도 삭제
	        ;
		return http.build();
	}
	
	@Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	
	@Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager(); //AuthenticationManager는 스프링 시큐리티의 인증을 처리
		
	}
	
	
}
