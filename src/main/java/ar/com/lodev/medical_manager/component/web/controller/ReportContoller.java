package ar.com.lodev.medical_manager.component.web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import ar.com.lodev.medical_manager.component.service.DoctorService;
import ar.com.lodev.medical_manager.component.service.ReportService;
import ar.com.lodev.medical_manager.model.dto.DoctorDTO;
import ar.com.lodev.medical_manager.model.report.dto.BriefConsultReport;

import com.google.gson.Gson;

@Controller
@RequestMapping(value="/report")
public class ReportContoller {
	
	static final Logger logger = Logger.getLogger(ReportContoller.class);
	
	private static String FILE_SEPARATOR = "/";
	
	@Value("${app.file-system.temporal.root}")
	private String REPORT_EXPORT_PATH;
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private DoctorService doctorService;
	
	
	@RequestMapping(value="/brief-outline-consultation/build" , method=RequestMethod.POST)
	public @ResponseBody String buildReport(@RequestParam long sessionId,
			@RequestParam String report,@RequestParam String files) throws Exception{
		Gson gson = new Gson();
		BriefConsultReport reportObject = gson.fromJson(report, BriefConsultReport.class);
		try {
			DoctorDTO doctor = doctorService.getFromSession(); 
			boolean haspermission = doctorService.hasPermissionToListDoctor(doctor.getId(), doctor.getPracticeLogged().getId());
			if(haspermission){
				reportService.buildBriefConsultReport(sessionId,reportObject,files);
			}else{
				return "401";			
			}
		} catch (IOException e) {
			logger.error("ERROR", e);
			throw new Exception(e);
		}
		return "OK";
	}
	
	@RequestMapping(value = "/upload-ajax", method = RequestMethod.POST)
    @ResponseBody
    public String uploadMultipleFiles(MultipartHttpServletRequest request) {
      //  CommonsMultipartFile multipartFile = null;
		DoctorDTO doctor = doctorService.getFromSession();
		boolean haspermission = doctorService.hasPermissionToListDoctor(doctor.getId(), doctor.getPracticeLogged().getId());
		if(!haspermission){
			return "401";
		}
        Iterator<String> iterator = request.getFileNames();
        String filePaths = "";
        String fileName = "";
        while (iterator.hasNext()) {
            String key = iterator.next();
            // create multipartFile array if you upload multiple files
            MultipartFile multipartFile = (MultipartFile) request.getFile(key);
            String uploadedFileName = multipartFile.getOriginalFilename();
            try {
                byte[] bytes = multipartFile.getBytes();
 
                String storedFileLocation = REPORT_EXPORT_PATH + FILE_SEPARATOR + uploadedFileName;
                // Create the file on server
                File serverFile = new File(storedFileLocation);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                filePaths += uploadedFileName+",";

 
            } catch (Exception e) {
                return e.getMessage();
            }
        }
        return filePaths.length() > 0 ? filePaths.substring(0, filePaths.length() - 1): "";
    }
}
