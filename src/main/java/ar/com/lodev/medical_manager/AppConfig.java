package ar.com.lodev.medical_manager;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class AppConfig extends SpringBootServletInitializer{
	
	@Value("${spring.mail.host}")
	private String host;
	@Value("${spring.mail.port}")
	private int port;
	@Value("${spring.mail.username}")
	private String username;
	@Value("${spring.mail.password}")
	private String password;
	@Value("${app.mail.smtp.auth}")
	private boolean auth;
	@Value("${app.mail.debug}")
	private boolean debug;
	@Value("${app.mail.smtp.starttls.enable}")
	private boolean ttlsEnable;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
    /**
     *  Thymeleaf - Spring Security Integration
     */
    @Bean
    public SpringSecurityDialect springSecurityDialect() {
    	return new SpringSecurityDialect();
    }
    
    
	@Bean
	public JavaMailSender javaMailSender(){
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(host);
		mailSender.setPort(port);
		//Set gmail email id
		mailSender.setUsername(username);
		//Set gmail email password
		mailSender.setPassword(password);
		Properties prop = mailSender.getJavaMailProperties();
//		prop.put("mail.transport.protocol", protocol);
		prop.put("mail.smtp.auth", auth);
		prop.put("mail.smtp.starttls.enable", ttlsEnable);
		prop.put("mail.debug", debug);
		return mailSender;
	}
}
