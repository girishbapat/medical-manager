package ar.com.lodev.medical_manager.component.web.controller;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ar.com.lodev.medical_manager.component.service.ImageEntityService;
import ar.com.lodev.medical_manager.model.dto.ImageEntityDTO;

@Controller
@RequestMapping("download/image")
public class ImageDownloadController {

	@Autowired
	private ImageEntityService imageEntityService;
	
	@RequestMapping(value = "/{imageId}",method = RequestMethod.GET)
    public void doDownload(HttpServletResponse response,@PathVariable Long imageId){
		try{
			ImageEntityDTO imageEntity = imageEntityService.get(imageId);
			if(imageEntity.getPhoto() == null){
				return;
			}
			// get MIME type of the file
	        String mimeType = "image/"+imageEntity.getExtension();
	        
	        // set content attributes for the response
	        response.setContentType(mimeType);
	        response.setContentLength(imageEntity.getPhoto().length);
	 
	        // set headers for the response
	        response.setHeader("Content-disposition", "attachment; filename=file");
	 
	        // get output stream of the response
	        OutputStream outStream = response.getOutputStream();
	 
	        
	        outStream.write(imageEntity.getPhoto(), 0, imageEntity.getPhoto().length);
	        
	        outStream.close();
		}catch(Exception e){
			e.printStackTrace();
		}
 
    }
}
