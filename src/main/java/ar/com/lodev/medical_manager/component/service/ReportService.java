package ar.com.lodev.medical_manager.component.service;

import java.io.IOException;

import net.sf.jasperreports.engine.JRException;
import ar.com.lodev.medical_manager.model.report.dto.BriefConsultReport;


public interface ReportService {

	void buildBriefConsultReport(long sessionId, BriefConsultReport reportData, String files)
			throws JRException, IOException;


}
