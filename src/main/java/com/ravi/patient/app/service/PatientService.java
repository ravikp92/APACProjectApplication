/**
 * 
 */
package com.ravi.patient.app.service;

import java.util.Optional;

import com.ravi.patient.app.dao.PatientDAO;
import com.ravi.patient.app.model.Patient;
import com.ravi.patient.app.model.PatientMedicalHistory;


/**
 * @author RaviP
 *
 */
public class PatientService {

	PatientDAO patientDAO=new PatientDAO();

	public Optional<Patient> searchPatientById(int id) {
		return patientDAO.searchPatientById(id);
	}
	
	public Optional<PatientMedicalHistory> searchPatientHistoryByPatientId(int id) {
		return patientDAO.searchPatientMedicalHistoryByPatientId(id);
	}

	public int deletePatient(int id) {
		int patientHistoryResult= patientDAO.deletePatientMedicalHistoryByPatientId(id) ;
		int patientResult= patientDAO.deletePatientById(id) ;
		if(patientResult>0 && patientHistoryResult>0 ) {
			return patientHistoryResult;
		}
		else {
			
			return 0;
		}
	}
	
	public String addPatient(Patient patient,PatientMedicalHistory patientMedicalHistory) {
		return patientDAO.addPatient(patient,patientMedicalHistory);
	}
	
	public int updatePatient(Patient patient) {
		return patientDAO.updatePatient(patient);
				
	}
	
	public int updatePatientHistory(PatientMedicalHistory patientMedicalHistory) {
		return patientDAO.updatePatientMedicalHistory(patientMedicalHistory);
				
	}

	

}
