package ar.com.lodev.medical_manager;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/js/**")
		.addResourceLocations("/js/").setCachePeriod(31556926);
		registry.addResourceHandler("/css/**")
		.addResourceLocations("/css/").setCachePeriod(31556926);
		registry.addResourceHandler("/image/**")
		.addResourceLocations("/image/").setCachePeriod(31556926);
		registry.addResourceHandler("/assets/**")
		.addResourceLocations("/assets/").setCachePeriod(31556926);
	}


	
}
