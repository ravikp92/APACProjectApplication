package com.ravi.patient.app.dao;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.ravi.patient.app.model.Appointment;
import com.ravi.patient.app.model.Patient;
import com.ravi.patient.app.model.Physician;

public interface AppointmentDAO {

	List<Physician> getAllPhysicians();

	boolean checkIfAppointmentSlotAvailable(Physician physician, Optional<Patient> patientObj, Date appointmentDate,
			String appointmentSlot) throws ParseException;

	String bookAppointmentForPatient(Physician physician, Optional<Patient> patientObj, Date appointmentDateConverted,
			String appointmentSlot);

	int getAppointmentsMaxId();

	boolean checkAppointmentExistsForPatientId(int patientId);

	boolean cancelAppointmentForPatientId(int patientId);

	String rescheduleAppointmentForPatientId(Physician physician, Optional<Patient> patientObj,
			Date appointmentDateConverted, String appointmentSlot);

	String getAppointmentIdByPatientId(int patientId);

	Optional<Appointment> getAllAppointmentsByPatientId(int patientId);

}
