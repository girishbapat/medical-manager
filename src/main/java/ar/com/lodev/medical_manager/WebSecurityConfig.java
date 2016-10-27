package ar.com.lodev.medical_manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
@Order(2)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
    private CustomAuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;
    
        
    @Override
    public void configure(WebSecurity web) throws Exception {
      web.ignoring().antMatchers("/js/**");
      web.ignoring().antMatchers("/image/**");
      web.ignoring().antMatchers("/css/**");
      web.ignoring().antMatchers("/fonts/**");
      web.ignoring().antMatchers("/assets/**");
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.csrf().disable();
        
        http.formLogin().loginProcessingUrl("/auth/login").loginPage("/login")
        .usernameParameter("username").passwordParameter("password").failureUrl("/login?error=user_pass_do_not_match")
        .permitAll().successHandler(authenticationSuccessHandler);
        
        http.logout().logoutUrl("/auth/logout");
        
        http.authorizeRequests().antMatchers("/signup").permitAll();
        http.authorizeRequests().antMatchers("/practice-admin/create").permitAll();
        http.authorizeRequests().antMatchers("/doctor/create").permitAll();
        http.authorizeRequests().antMatchers("/download/image/*").permitAll();
        http.authorizeRequests().antMatchers("/user/emailUnique").permitAll();
        http.authorizeRequests().antMatchers("/user/usernameUnique").permitAll();
        http.authorizeRequests().antMatchers("/user/practiceIdUnique").permitAll();
        http.authorizeRequests().antMatchers("/user/forgotPassword").permitAll();
        http.authorizeRequests().antMatchers("/user/forgotUsername").permitAll();
        
        //http.authorizeRequests().antMatchers("/report/**").permitAll();
        
        http.authorizeRequests().antMatchers("/**").authenticated();
        
    } 
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
//    	        auth.jdbcAuthentication()
//                .dataSource(datasource)
//                .usersByUsernameQuery("select username, password, activated from user where username = ?")
//                .authoritiesByUsernameQuery("select u.username, r.name from user u, role r where u.role_id = r.id and u.username = ?")
//                .passwordEncoder(bCryptPasswordEncoder);
    }
    
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//            .inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER");
//    }
    
    @Bean(name="myAuthenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
}
