package ar.com.lodev.medical_manager.component.service;

public interface EmailService {

	void enviarEmail(String email, String asunto, String cuerpoMensaje,
			String filePath, String files, String file_path);

}
