package ar.com.lodev.medical_manager.component.service;

import java.util.List;

import ar.com.lodev.medical_manager.exception.DoctorPlaceAssociationException;
import ar.com.lodev.medical_manager.exception.PracticePlaceException;
import ar.com.lodev.medical_manager.model.Doctor;
import ar.com.lodev.medical_manager.model.DoctorPracticePlaceAssociation;
import ar.com.lodev.medical_manager.model.PracticePlace;
import ar.com.lodev.medical_manager.model.PracticePlaceDoctorRequestStatus;
import ar.com.lodev.medical_manager.model.dto.DoctorDTO;
import ar.com.lodev.medical_manager.model.dto.DoctorPracticePlaceAssociationDTO;

/**
 * Manage the interaction of the association between a {@link Doctor} and a {@link PracticePlace}
 * @author leandro
 *
 */
public interface PracticePlaceAssociationService {

	/**
	 * return the {@link DoctorPracticePlaceAssociation} related to the practicePlace
	 * if it is inside the associations
	 * @param associations
	 * @param practicePlace
	 * @return
	 */
	DoctorPracticePlaceAssociation existAssociation(List<DoctorPracticePlaceAssociation> associations,
			PracticePlace practicePlace);

	/**
	 * create a request from a {@link Doctor} to a {@link PracticePlace}
	 * @param practiceId
	 * @return
	 * @throws DoctorPlaceAssociationException
	 */
	DoctorPracticePlaceAssociationDTO createRequest(long practiceId)
			throws DoctorPlaceAssociationException;
	
	/**
	 * cancel a request made by a {@link Doctor}
	 * @param associationId
	 * @throws DoctorPlaceAssociationException
	 */
	void deleteRequestFromDoctor(long associationId)
			throws DoctorPlaceAssociationException;

	/**
	 * accept or denied a request 
	 * @param associationId
	 * @param status
	 * @return
	 * @throws DoctorPlaceAssociationException
	 */
	DoctorPracticePlaceAssociationDTO updateRequestStatus(long associationId,
			PracticePlaceDoctorRequestStatus status)
			throws DoctorPlaceAssociationException;

	long countSearch();

	List<DoctorPracticePlaceAssociationDTO> search(int offset);

	DoctorDTO login(long practiceAssociationId) throws DoctorPlaceAssociationException, PracticePlaceException;

	DoctorDTO logout();

}
