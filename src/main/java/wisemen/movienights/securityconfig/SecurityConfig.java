package wisemen.movienights.securityconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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
}
