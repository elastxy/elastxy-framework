package org.elastxy.web.application;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;


/**
 * TODO1-2: Security: add custom claims for low granularity permission
 * TODO1-2: Security: add mysql db and APIs for hosting users and roles
 * 
 * @author red
 *
 */
@Configuration
@EnableAutoConfiguration
@Profile("local")
@ConditionalOnExpression("!${web.security.enabled}")
public class SecurityLocalConfig extends WebSecurityConfigurerAdapter {
	  @Override
      protected void configure(HttpSecurity http) throws Exception {
          http
              .authorizeRequests()
                  .antMatchers("/**").permitAll()
                  .anyRequest().permitAll();
          http
                  .sessionManagement()
                  .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                  .and()
                  .csrf()
                  .disable();          
      }

      @Override
      protected void configure(AuthenticationManagerBuilder auth) throws Exception {
          auth
          .inMemoryAuthentication()
              .withUser("adminuser").password("secretxy").roles("ADMIN_USER");
      }

}
