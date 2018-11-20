 package cn.authDemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;


/**
 * @author Li Chaoyang
 * @date 2018/09/05
 */
@EnableWebSecurity
public class MultiSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        
        manager.createUser(User.withUsername("admin").password("admin").roles("ADMIN","USER").build());
        
        return manager;
    }
    
    @Configuration
    public static class FormLoginSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {


        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .csrf()
                    .ignoringAntMatchers("/api/**")
                    .and()
                .authorizeRequests()
                    .antMatchers("/css/**", "/js/**", "/img/**", "/webjars/**").permitAll()
                    .antMatchers("/login**").permitAll()
                    .antMatchers("/**").hasRole("USER")
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    //.loginPage("/login.html")
                    .loginProcessingUrl("/login")
                    .failureUrl("/login.html?error")
                    .and()
                .logout()
                    .logoutSuccessUrl("/login.html")
                    .deleteCookies("SESSION")
                    .invalidateHttpSession(true)
                    .and()
                .rememberMe()
                    .key("unsecureKey")
                    .rememberMeParameter("remember-me")
                    .tokenValiditySeconds(86400)
                    .and()
                .httpBasic();

        }
    }
    
    @Configuration
    @Order(1)
    public static class RestLoginSecurityConfiguationAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .antMatcher("/api/**")
                .csrf().disable()
                .exceptionHandling()
                    .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                    .and()
                .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginProcessingUrl("/api/login")
                    .successHandler(new CustomAuthenticationSuccessHandler())
                    .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                    .and()
                .logout();
            
        }
    }
    


}
