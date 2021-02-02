package wisemen.movienights.securityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .antMatcher("/restricted").authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.POST,"/rest/v1/calendar/events").permitAll()
                .antMatchers(HttpMethod.GET,"/rest/v1/calendar/events").permitAll()
                .anyRequest().authenticated()
                .and().oauth2Login();
    }

//    @Autowired
//    private MyUserDetailsService myUserDetailsService;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.GET ,"/rest/**").authenticated()
//                .antMatchers(HttpMethod.GET ,"/rest/v1/whoami").authenticated()
//                .antMatchers(HttpMethod.POST, "/rest/v1/user").permitAll()
//                .antMatchers(HttpMethod.POST, "/login").permitAll()
//                .and()
//                .formLogin();
////                .loginPage("http://localhost:3000/");
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .userDetailsService(myUserDetailsService)
//                .passwordEncoder(myUserDetailsService.getEncoder());
//    }
}
