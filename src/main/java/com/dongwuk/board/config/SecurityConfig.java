package com.dongwuk.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.dongwuk.board.service.MemberService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private MemberService memberService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// 정적 파일 목록 인증 무시(always pass)
		web.ignoring().antMatchers("/css/**", "/img/**", "/js/**", "/lib/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		// page 권한
		//	.antMatchers("/admin/**").hasAnyRole("ADMIN", "MEMBER")
			.antMatchers("/admin/**").hasRole("ADMIN")
			.antMatchers("/user/myinfo").hasRole("MEMBER")
			.antMatchers("/post").hasAnyRole("ADMIN", "MEMBER")
			.antMatchers("/post/**").permitAll()
			.antMatchers("/**").permitAll()
		// login
		.and()
			.formLogin()
			.loginPage("/user/login")
			.defaultSuccessUrl("/user/login/result")
			.failureUrl("/user/login")
			.permitAll()
		// logout
		.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
			.logoutSuccessUrl("/user/logout/result")
			.invalidateHttpSession(true)
		// 403 error handling
		.and()
			.exceptionHandling().accessDeniedPage("/user/denied")
		.and()
			.csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("!/console/**")) 
		.and()
			.headers()
			.addHeaderWriter(new StaticHeadersWriter("X-Content-Security-Policy","script-src 'self'"))
			.frameOptions().disable();
		
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
	}
}
