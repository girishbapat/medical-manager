package ar.com.lodev.medical_manager.component.web.controller;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ar.com.lodev.medical_manager.component.service.ReportService;
import ar.com.lodev.medical_manager.model.report.dto.BriefConsultReport;

import com.google.gson.Gson;

@Controller
@RequestMapping(value="/report")
public class ReportContoller {
	
	static final Logger logger = Logger.getLogger(ReportContoller.class);
	
	@Autowired
	private ReportService reportService;
	
	@RequestMapping(value="/brief-outline-consultation/build" , method=RequestMethod.POST)
	public @ResponseBody String buildReport(@RequestParam long sessionId,
			@RequestParam String report) throws Exception{
		Gson gson = new Gson();
		BriefConsultReport reportObject = gson.fromJson(report, BriefConsultReport.class);
		try {
			reportService.buildBriefConsultReport(sessionId,reportObject);
		} catch (IOException e) {
			logger.error("ERROR", e);
			throw new Exception(e);
		}
		return "OK";
	}

}
