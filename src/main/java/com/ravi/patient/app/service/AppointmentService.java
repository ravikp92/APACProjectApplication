/**
 * 
 */
package com.ravi.patient.app.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.ravi.patient.app.dao.AppointmentDAO;
import com.ravi.patient.app.model.Appointment;
import com.ravi.patient.app.model.Patient;
import com.ravi.patient.app.model.Physician;


/**
 * @author RaviP
 *
 */
public class AppointmentService {

	
	
	AppointmentDAO appointmentDAO=new AppointmentDAO();
	
	public List<Physician> getAllPhysicians() {
		return appointmentDAO.getAllPhysicians();
	}

	public String bookAppointmentWithPhysician(Physician physician, Optional<Patient> patientObj,
			Date appointmentDateConverted, String appointmentSlot) {
		
		return appointmentDAO.bookAppointmentForPatient( physician,  patientObj,
				 appointmentDateConverted,  appointmentSlot);
	}
	
	public boolean cancelAppointmentForPatientId(int patientId){
		return appointmentDAO.cancelAppointmentForPatientId( patientId);
	}

	public String rescheduleAppointmentForExisitngPatient(Physician physician, Optional<Patient> patientObj,
			Date appointmentDateConverted, String appointmentSlot) {
		return appointmentDAO.rescheduleAppointmentForPatientId(physician, patientObj,
				 appointmentDateConverted,  appointmentSlot); 
	}
	
	
	public boolean checkAppointmentSlotForPhysician(Physician physician, Optional<Patient> patientObj,
			Date appointmentDate, String appointmentSlot) throws ParseException {
		
		return appointmentDAO.checkIfAppointmentSlotAvailable( physician,  patientObj,
				 appointmentDate,  appointmentSlot);
	}
	
	public boolean checkAppointmentByPatientId(int patientId){
		return appointmentDAO.checkAppointmentExistsForPatientId( patientId);
	}
	
	public Optional<Appointment> getAppointmentByPatientId(int patientIdInt) {
		return appointmentDAO.getAppointmentByPatientId(patientIdInt);
	}
}
