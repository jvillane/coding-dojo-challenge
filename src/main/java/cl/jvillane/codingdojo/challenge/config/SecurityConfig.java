package cl.jvillane.codingdojo.challenge.config;

import cl.jvillane.codingdojo.challenge.security.CustomAccessDeniedHandler;
import cl.jvillane.codingdojo.challenge.security.MySavedRequestAwareAuthenticationSuccessHandler;
import cl.jvillane.codingdojo.challenge.security.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private CustomAccessDeniedHandler accessDeniedHandler;

  @Autowired
  private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

  @Autowired
  private MySavedRequestAwareAuthenticationSuccessHandler mySuccessHandler;

  private SimpleUrlAuthenticationFailureHandler myFailureHandler = new SimpleUrlAuthenticationFailureHandler();

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .csrf().disable()
        .authorizeRequests()
        .and()
        .exceptionHandling()
        .accessDeniedHandler(accessDeniedHandler)
        .authenticationEntryPoint(restAuthenticationEntryPoint)
        .and()
        .authorizeRequests()
        .antMatchers("/auth/**").permitAll()
        .antMatchers("/register/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .successHandler(mySuccessHandler)
        .failureHandler(myFailureHandler)
        .and()
        .httpBasic()
        .and()
        .logout();
  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder(11);
  }
}
