package com.security.config;


import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.security.jwt.JwtAuthenticationEntryPoint;
import com.security.jwt.JwtAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	
	
	@Autowired
	public JwtAuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	public JwtAuthenticationFilter authenticationFilter;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new  BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
		return builder.getAuthenticationManager();
	}
	
	@Bean	
	public UserDetailsService userDetailService() {
		 return new CutomerUserDetailService();	
	}
	
	 @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity httsecurity) throws Exception {
		 httsecurity.csrf(csrf->csrf.disable())
	            .authorizeHttpRequests(authorize -> authorize.requestMatchers("/home/token").permitAll()
	            .anyRequest().authenticated()
	            )
	            .cors(cors->cors.disable())
	            .exceptionHandling(exceptionHandling -> exceptionHandling
	                .authenticationEntryPoint(authenticationEntryPoint)
	            )
	            .sessionManagement(sessionManagement -> sessionManagement
	                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	            )
	            .formLogin(withDefaults());

		 httsecurity.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
	     return httsecurity.build();
	    }
	 
		
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	/* 
	 @Bean	
		public UserDetailsService userDetailService() {
			
			
			 UserDetails normal = User.withUsername("normal")
					 					.password(passwordEncoder().encode("normal"))
					 						.roles("NORMAL").build();
			 UserDetails user = User.withUsername("admin")
					 					.password(passwordEncoder().encode("admin"))
					 					 	.roles("ADMIN").build();	
			
			return new  InMemoryUserDetailsManager(normal,user);
		} 
	 */
	 
    /*
	@Bean 
	public SecurityFilterChain filterChain(HttpSecurity httsecurity) throws Exception {
		
		httsecurity
		.csrf().disable()
		.authorizeRequests()
		.requestMatchers("/home/normal")
		.hasRole("NORMAL")
		.requestMatchers("/home/public")
		.hasRole("PUBLIC")
		.requestMatchers("/home/welcome")
		.permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.formLogin();
		
		return httsecurity.build();
	}
	*/
}
