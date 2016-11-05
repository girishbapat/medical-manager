package ar.com.lodev.medical_manager.component.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import ar.com.lodev.medical_manager.component.dao.MedicalSessionRepository;
import ar.com.lodev.medical_manager.component.service.EmailService;
import ar.com.lodev.medical_manager.component.service.MedicalSessionDoctorAdminService;
import ar.com.lodev.medical_manager.component.service.ReportService;
import ar.com.lodev.medical_manager.model.MedicalSession;
import ar.com.lodev.medical_manager.model.report.dto.BriefConsultReport;

@Service
public class ReportServiceImpl implements ReportService {
	
//	private static final String REPORT_TEMPLATE_PATH_BRIEF_OUTLINE_CONSULT = "reports/brief_outline_consultation.jrxml";//testing
	private static final String REPORT_TEMPLATE_PATH_BRIEF_OUTLINE_CONSULT = "jasper/brief_outline_consultation.jasper";
	private static final String REPORT_BRIEF_OUTLINE_CONSULT_RELATIVE_NAME = "brief_outline_consultation.pdf";
	private static final String REPORT_SUBJECT = "ConsultPal - Report";
	private static final String REPORT_DEFAULT_BODY = "Thank you for using ConsultPal";
	
	@Value("${app.file-system.temporal.root}")
	private String REPORT_EXPORT_PATH;
	
	
	@Autowired
	private MedicalSessionDoctorAdminService medicalSessionDoctorService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private MedicalSessionRepository medicalSessionRepository;
	
	@Override
	public void buildBriefConsultReport(long sessionId,BriefConsultReport reportData, String files) throws JRException, IOException{
		if(!medicalSessionDoctorService.hasAccessToSession(sessionId)){
			throw new SecurityException("User unauthorized");
		}
		MedicalSession session = medicalSessionRepository.findOne(sessionId);
		String fileName = "/"+session.getId().toString()+"_"+REPORT_BRIEF_OUTLINE_CONSULT_RELATIVE_NAME;
		List<BriefConsultReport> listData = new ArrayList<BriefConsultReport>();
		listData.add(reportData);
		JRBeanCollectionDataSource ds =new JRBeanCollectionDataSource(listData);
		
		Resource resource = new ClassPathResource(REPORT_TEMPLATE_PATH_BRIEF_OUTLINE_CONSULT);
		File reportTemplate = resource.getFile();
//		JasperDesign jd = JRXmlLoader.load(reportTemplate); //just for testing
//		JasperReport report = JasperCompileManager.compileReport(jd); //just for testing
		JasperReport report = (JasperReport) JRLoader.loadObject(reportTemplate);
		
		JasperPrint print = JasperFillManager.fillReport(report,new HashMap<String, Object>(), ds);
		JasperExportManager.exportReportToPdfFile(print, REPORT_EXPORT_PATH+fileName);
		emailService.enviarEmail(session.getPatient().getEmail(), 
				REPORT_SUBJECT, REPORT_DEFAULT_BODY, REPORT_EXPORT_PATH+fileName,files,REPORT_EXPORT_PATH);
		
		File reportGenerated = new File(REPORT_EXPORT_PATH+fileName);
		reportGenerated.delete();
		File directory = new File(REPORT_EXPORT_PATH);

		// Get all files in directory

		File[] allFiles = directory.listFiles();
		for (File file : allFiles)
		{
		   file.delete();
		   if (!file.delete())
		   {
		       System.out.println("Failed to delete "+file);
		   }
		} 
 
	}
}
