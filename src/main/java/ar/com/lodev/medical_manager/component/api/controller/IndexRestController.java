package ar.com.lodev.medical_manager.component.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api")
public class IndexRestController {

	@RequestMapping(value="/" , method=RequestMethod.GET)
	public void index(){

	}
	
}
