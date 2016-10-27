package ar.com.lodev.medical_manager;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
@Order(1)
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private DataSource datasource;
	
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
 
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/api/**");
    	http.csrf().disable();
        
        http.httpBasic()
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.formLogin().loginProcessingUrl("/api/login").permitAll();
        
        http.authorizeRequests().antMatchers("/api/**").permitAll();
        
        
    } 
    

}
