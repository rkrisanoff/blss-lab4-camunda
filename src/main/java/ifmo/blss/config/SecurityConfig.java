package ifmo.blss.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/camunda/app/**").authenticated() // Secure Camunda URLs
                .and()
                .httpBasic(); // Use Basic Authentication

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("drukhary")
//                .password("{noop}$2a$12$OfqngPEHsEhMfiZz7IndOO12EvXXHj5sX0VwA1xEM4x2E.NDcmxqq") // {noop} means no password encoder
                .password("{noop}drukhary") // {noop} means no password
                .roles("ADMIN");
    }
//
//    private static final String REGISTER_ENDPOINT = "/api/v1/auth/signup";
//
////    @Bean
////    public AuthenticationManager authenticationManagerBean() throws Exception {
////        return super.authenticationManagerBean();
////    }
//
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//        return authConfig.getAuthenticationManager();
//    }

//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
//        corsConfiguration.setAllowedOrigins(List.of("*"));
//        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
//        corsConfiguration.setAllowCredentials(true);
//        corsConfiguration.setExposedHeaders(List.of("Authorization"));
//
//        httpSecurity
//                .csrf().disable()
//                .cors().configurationSource(request -> corsConfiguration)
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
////                .antMatchers(REGISTER_ENDPOINT).permitAll()
//                .antMatchers("/**").permitAll()
//
////                .antMatchers("/swagger-ui/**").permitAll()
////                .antMatchers("/api-docs/**").permitAll()
////                .antMatchers("/api/v1/admin/**").hasRole("ADMIN")
////                .antMatchers("/api/v1/user/**").hasRole("USER")
////                .antMatchers("/*").permitAll()
//                .anyRequest().authenticated()
//                .and().httpBasic();
//    }

}