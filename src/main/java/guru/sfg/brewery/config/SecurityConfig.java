package guru.sfg.brewery.config;

import guru.sfg.brewery.security.SfgPasswordEncoderFactories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(expressionInterceptUrlRegistry -> expressionInterceptUrlRegistry
                        .antMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll()
                        .antMatchers("/beers/find/**", "/beers*").permitAll()
                        .antMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll()
                        .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").permitAll())
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and().formLogin()
                .and().httpBasic();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return SfgPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("spring")
                .password("{bcrypt}$2a$10$X6SqHBUQN6fBIZo.UGLKtupihrnufktC3L9BeX5j3HsR2Ix12WX36")
                .roles("ADMIN")
                .and()
                .withUser("user")
                .password("{sha256}7785a517ed0f8f2f4797bf296ec45797826b414611b150c6b009542937ae03ecd5c63cdae82e15e7")
                .roles("USER")
                .and()
                .withUser("scott")
                .password("{bcrypt15}$2a$15$4vOtz3j55NRAsk0LHUia1OAogMOSVh1udQWYrun7fULQUm9TMmSKm")
                .roles("CUSTOMER");
    }
}
