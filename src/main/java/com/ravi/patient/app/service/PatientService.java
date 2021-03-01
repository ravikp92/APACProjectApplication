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

	PatientDAO patientDAO = new PatientDAO();

	public String addPatient(Patient patient, PatientMedicalHistory patientMedicalHistory) {
		return patientDAO.createPatient(patient, patientMedicalHistory);
	}

	public int updatePatient(Patient patient) {
		return patientDAO.updatePatient(patient);

	}

	public int updatePatientMedicalHistory(PatientMedicalHistory patientMedicalHistory) {
		return patientDAO.updatePatientMedicalHistory(patientMedicalHistory);

	}

	public Optional<Patient> searchPatientById(int id) {
		return patientDAO.searchPatientById(id);
	}

	public Optional<PatientMedicalHistory> searchPatientHistoryByPatientId(int id) {
		return patientDAO.searchPatientMedicalHistoryByPatientId(id);
	}

	public int deletePatient(int id) {
		int patientHistoryResult = patientDAO.deletePatientMedicalHistoryByPatientId(id);
		int patientResult = patientDAO.deletePatientById(id);
		if (patientResult > 0 && patientHistoryResult > 0) {
			return patientHistoryResult;
		} else {
			return 0;
		}
	}

}
