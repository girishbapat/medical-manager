package ar.com.lodev.medical_manager.component.web.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ar.com.lodev.medical_manager.component.service.PracticePlaceAssociationService;
import ar.com.lodev.medical_manager.component.service.PracticePlaceService;
import ar.com.lodev.medical_manager.model.PracticePlaceDoctorRequestStatus;
import ar.com.lodev.medical_manager.model.dto.DoctorPracticePlaceAssociationDTO;
import ar.com.lodev.medical_manager.ui.model.DataTableData;

@Controller
@RequestMapping(value="/doctor-admin")
public class DoctorAdminController extends BaseController{
	
	static final Logger logger = Logger.getLogger(DoctorAdminController.class);
	
	@Autowired
	private PracticePlaceService practicePlaceService;
	@Autowired
	private PracticePlaceAssociationService practicePlaceAssociationService;
	
	@RequestMapping(value="/list-doctors" , method=RequestMethod.GET)
	public String dashboard(Model model){
		model.addAttribute("section", PANEL_SECTION_DOCTORS);
		return "practice-admin/list-doctors";
	}
	
	@RequestMapping(value="/json/doctor/list", method=RequestMethod.GET)
	public @ResponseBody DataTableData listJson(@RequestParam int start,@RequestParam long draw){
		List<DoctorPracticePlaceAssociationDTO> dtos = practicePlaceService.listDoctors(start);
		long recordsTotal = practicePlaceService.countDoctors();
		return new DataTableData(draw,recordsTotal, recordsTotal, dtos);
	}
	
	@RequestMapping(value="/json/request/updateStatus", method=RequestMethod.POST)
	public @ResponseBody DoctorPracticePlaceAssociationDTO updateRequestStatus(@RequestParam long associationId,
			@RequestParam String status) throws Exception{
		try{
			PracticePlaceDoctorRequestStatus statusEnum = PracticePlaceDoctorRequestStatus.valueOf(status);
			DoctorPracticePlaceAssociationDTO dto = practicePlaceAssociationService.updateRequestStatus(associationId, statusEnum);
			return dto;
		}catch(Exception e){
			logger.error(ERROR, e);
			throw new Exception(e.getMessage());
		}
	}
}
