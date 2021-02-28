package com.ravi.patient.service;

import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ravi.patient.app.dao.PatientDAO;
import com.ravi.patient.app.enums.AffectedOrgan;
import com.ravi.patient.app.enums.Gender;
import com.ravi.patient.app.model.Patient;
import com.ravi.patient.app.model.PatientMedicalHistory;
import com.ravi.patient.app.service.PatientService;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class TestPatientService {

	//We have annotated PatientService class with @InjectMocks, 
	//so mockito will create the mock object for PatientService class and inject the mock dependency of PatientDAO into it.
    @InjectMocks
    PatientService patientService;
    
    @Mock
    PatientDAO patientDao;
    
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testAddPatient() throws ParseException {
    	Patient patient=new Patient(2, "Ravik", "Puri", "address1", "rak@g.com", "123-456-7890", "mumbai", "Maharashtra", Gender.MALE.toString(), 
    			new SimpleDateFormat("dd/MM/yyyy").parse("04/02/1992")
    			, "1234568");
    	PatientMedicalHistory patientMedicalHistory = new PatientMedicalHistory(2, 2, 5.8, 70, 80, 2.5, AffectedOrgan.HANDS.toString());
    	
    	when(patientDao.addPatient(patient, patientMedicalHistory)).
    	thenReturn("Patient and Medical History Record is added successfully!!");
    }
    
    @Test
    public void testUpdatePatient() throws ParseException {
    	Patient patient=new Patient(2, "Ravik", "Puri", "address1", "rak@g.com", "123-456-7890", "mumbai", "Maharashtra", Gender.MALE.toString(), 
    			new SimpleDateFormat("dd/MM/yyyy").parse("04/02/1992")
    			, "1234568");
    	PatientMedicalHistory patientMedicalHistory = new PatientMedicalHistory(2, 2, 5.8, 70, 80, 2.5, AffectedOrgan.HANDS.toString());
    	when(patientDao.updatePatient(patient)).thenReturn(1);
    	when(patientDao.updatePatientMedicalHistory(patientMedicalHistory)).thenReturn(1);
    }
    
    @Test
    public void testDeletePatient() throws ParseException {
    	when(patientDao.deletePatientById(2)).thenReturn(1);
    	when(patientDao.deletePatientMedicalHistoryByPatientId(2)).thenReturn(1);
    }
    
    @Test
    public void testSearchPatient() throws ParseException {
    	Optional<Patient> patient=Optional.ofNullable(new Patient(2, "Ravik", "Puri", "address1", "rak@g.com", "123-456-7890", "mumbai", "Maharashtra", Gender.MALE.toString(), 
    			new SimpleDateFormat("dd/MM/yyyy").parse("04/02/1992")
    			, "1234568"));
    	Optional<PatientMedicalHistory> patientMedicalHistory = Optional.ofNullable(new PatientMedicalHistory(2, 2, 5.8, 70, 80, 2.5, AffectedOrgan.HANDS.toString()));
    	
    	when(patientDao.searchPatientById(2)).thenReturn(patient);
    	when(patientDao.searchPatientMedicalHistoryByPatientId((2))).thenReturn(patientMedicalHistory);
    }
    
}
