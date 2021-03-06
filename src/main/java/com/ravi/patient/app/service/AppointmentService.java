package com.ravi.patient.app.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.ravi.patient.app.model.Appointment;
import com.ravi.patient.app.model.Patient;
import com.ravi.patient.app.model.Physician;

public interface AppointmentService {

	 List<Physician> getAllPhysicians();
	 String bookAppointmentWithPhysician(Physician physician, Optional<Patient> patientObj,
			Date appointmentDateConverted, String appointmentSlot);

	 boolean cancelAppointmentForPatientId(int patientId);

	 String rescheduleAppointmentForExisitngPatient(Physician physician, Optional<Patient> patientObj,
			Date appointmentDateConverted, String appointmentSlot);

	 boolean checkAppointmentSlotForPhysician(Physician physician, Optional<Patient> patientObj,
			Date appointmentDate, String appointmentSlot) throws ParseException;

	 boolean checkAppointmentByPatientId(int patientId);

	 Optional<Appointment> getAppointmentByPatientId(int patientIdInt);
}
