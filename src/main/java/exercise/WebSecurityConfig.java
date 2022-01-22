package exercise;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.DELETE;

import exercise.model.UserRole;
import exercise.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().and().sessionManagement().disable();

        http
                .authorizeRequests()
                .antMatchers(GET,"/").permitAll()
                .antMatchers(POST,"/users").permitAll()
                .antMatchers("/users").hasAnyAuthority(UserRole.USER.name(), UserRole.ADMIN.name())
                .antMatchers(DELETE,"/users/*").hasAuthority(UserRole.ADMIN.name())
                .antMatchers("/users/*").hasAuthority(UserRole.ADMIN.name())


                .antMatchers(POST,"/short").permitAll()
                .antMatchers(GET,"/short/*").permitAll()
                .antMatchers("/short/*/info").hasAuthority(UserRole.ADMIN.name())
                .antMatchers(DELETE,"/short/*").hasAuthority(UserRole.ADMIN.name())



                .antMatchers("/h2console/*").hasAuthority(UserRole.ADMIN.name())
                .and().httpBasic();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService);
    }
}
