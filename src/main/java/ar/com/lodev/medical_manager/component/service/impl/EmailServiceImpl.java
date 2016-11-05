package ar.com.lodev.medical_manager.component.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import ar.com.lodev.medical_manager.component.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService{
	
	static final Logger logger = Logger.getLogger(EmailServiceImpl.class);
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
    private String fromEmail;
	
    @Override
    public void enviarEmail(String email, String asunto, String cuerpoMensaje,String filePath, String files,String file_path){
    	try {
    		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    		MimeMessageHelper mailMsg = new MimeMessageHelper(mimeMessage, true);
    		
    		mailMsg.setFrom(fromEmail);
			mailMsg.setTo(email);
			mailMsg.setSubject(asunto);
			
	        mailMsg.setText(cuerpoMensaje, false /* isHtml */);
			
	        FileSystemResource file = null;
			if(filePath != null){
				file = new FileSystemResource(filePath);
				mailMsg.addAttachment(file.getFilename(), file);
			}
			
			if(files != null && files.length()>0){
				for(String singleFile : files.split(",")){
					file = new FileSystemResource(file_path+"/"+singleFile);
					mailMsg.addAttachment(file.getFilename(), file);
				}
			}
			
			javaMailSender.send(mimeMessage);
		} catch (MessagingException e) {
			logger.error("ERROR", e);
			throw new RuntimeException(e.getMessage());
		}

    }
	
}
