package com.ravi.patient.app.service;

import java.util.Optional;

import com.ravi.patient.app.model.Patient;
import com.ravi.patient.app.model.PatientMedicalHistory;

public interface PatientService {

	 String addPatient(Patient patient, PatientMedicalHistory patientMedicalHistory);

	 int updatePatient(Patient patient);

	 int updatePatientMedicalHistory(PatientMedicalHistory patientMedicalHistory);

	 Optional<Patient> searchPatientById(int id);

	 Optional<PatientMedicalHistory> searchPatientHistoryByPatientId(int id);

	 int deletePatient(int id);
}
