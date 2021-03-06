package com.ravi.patient.app.dao;

import java.util.Optional;

import com.ravi.patient.app.model.Patient;
import com.ravi.patient.app.model.PatientMedicalHistory;
public interface PatientDAO {
	
	Optional<Patient> searchPatientById(int id);
	Optional<PatientMedicalHistory> searchPatientMedicalHistoryByPatientId(int id);
	
	int getPatientsMaxId();
	int getPatientMedicalHistoryMaxId();
	
	int deletePatientById(int id);
	int deletePatientMedicalHistoryByPatientId(int id);
	
	String createPatient(Patient patient, PatientMedicalHistory PatientMedicalHistory);
	
	int updatePatient(Patient patient);
	int updatePatientMedicalHistory(PatientMedicalHistory PatientMedicalHistory);
}
