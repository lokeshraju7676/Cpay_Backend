package com.cpay.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cpay.security.jwt.AuthEntryPointJwt;
import com.cpay.security.jwt.AuthTokenFilter;
import com.cpay.service.UserDetailsServiceImpl;

@Configuration
public class SecurityConfig {
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	public final static String[] PUBLIC_REQUEST_MATCHERS = { "/api/auth/**","/swagger-ui/**","/v3/api-docs/**"};

	
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    
    @Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}
   
    @Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}
    
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Authorize requests
            .authorizeHttpRequests(authorize -> authorize
            		 .requestMatchers("/api/applications/**").hasRole("Customer") // Secure all application-related endpoints
                     .requestMatchers("/api/orders/track/**").hasRole("Customer") // Secure order tracking
                     .requestMatchers("/api/payments/**").hasRole("Customer") // Secure payment endpoints
                     .requestMatchers("/api/transactions/**").hasRole("Customer") // Secure transaction endpoints
                     .requestMatchers("/api/applications/apply").hasRole("Customer") // Apply for credit card
                     
                  // Admin only endpoints
                     .requestMatchers("/api/customer/**").hasRole("ADMIN") // Admin can manage customers
                     .requestMatchers("/api/applications/approve/**").hasRole("ADMIN") // Admin can approve applications

                     // Customer and Admin shared access
                     .requestMatchers("/api/payments/process").hasAnyRole("CUSTOMER", "ADMIN") // Both can process payments
                     
                     .requestMatchers(PUBLIC_REQUEST_MATCHERS).permitAll() // Allow public access to auth, swagger, etc.
                     .anyRequest().authenticated() // All other requests must be authenticated
            )
            // Enable HTTP Basic Authentication
            //.httpBasic(Customizer.withDefaults())
            // Disable CSRF for simplicity (not recommended for production)
            .csrf(csrf -> csrf.disable())
            .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authenticationProvider(authenticationProvider())
			.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	        return http.build();
    }

	/*
	 * @Bean public UserDetailsService userDetailsService(PasswordEncoder
	 * passwordEncoder) { // Define in-memory users InMemoryUserDetailsManager
	 * manager = new InMemoryUserDetailsManager();
	 * manager.createUser(User.withUsername("user")
	 * .password(passwordEncoder.encode("password")) .roles("USER") .build());
	 * manager.createUser(User.withUsername("admin")
	 * .password(passwordEncoder.encode("admin")) .roles("ADMIN") .build()); return
	 * manager; }
	 */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Use BCrypt password encoder
        return new BCryptPasswordEncoder();
    }
   
    
}
