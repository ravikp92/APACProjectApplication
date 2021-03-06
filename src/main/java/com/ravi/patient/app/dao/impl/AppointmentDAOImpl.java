package com.ravi.patient.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.ravi.patient.app.config.DBconfiguration;
import com.ravi.patient.app.constant.DBConstants;
import com.ravi.patient.app.constant.PatientAppConstant;
import com.ravi.patient.app.dao.AppointmentDAO;
import com.ravi.patient.app.model.Appointment;
import com.ravi.patient.app.model.Patient;
import com.ravi.patient.app.model.Physician;

/**
 * @author RaviP
 *
 */
public class AppointmentDAOImpl implements AppointmentDAO,PatientAppConstant, DBConstants {

	private static Logger logger = Logger.getLogger(AppointmentDAOImpl.class);

	PreparedStatement prepStmt = null;
	Connection databaseConnection = null;

	public AppointmentDAOImpl() {
		try {
			databaseConnection = DBconfiguration.getInstance().getConnection();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}

	public List<Physician> getAllPhysicians() {
		prepStmt = null;
		List<Physician> physicianList = new ArrayList<Physician>();

		try {
			prepStmt = databaseConnection.prepareStatement(QUERY_GETALLPHYSICIANS);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				physicianList.add(new Physician(rs.getInt(1), rs.getString(2)));
			}

		} catch (SQLException e) {

			logger.error(e.getMessage());
		}
		return physicianList;
	}

	public boolean checkIfAppointmentSlotAvailable(Physician physician, Optional<Patient> patientObj,
			Date appointmentDate, String appointmentSlot) throws ParseException {

		prepStmt = null;

		try {

			prepStmt = databaseConnection.prepareStatement(QUERY_CHECKAPPOINTMENTSLOTAVAILABLE);
			prepStmt.setString(1, physician.getName());
			prepStmt.setTimestamp(2, new java.sql.Timestamp(appointmentDate.getTime()));
			prepStmt.setString(3, appointmentSlot);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				return true;
			}

		} catch (SQLException e) {

			logger.error(e.getMessage());
		}
		return false;

	}

	public String bookAppointmentForPatient(Physician physician, Optional<Patient> patientObj,
			Date appointmentDateConverted, String appointmentSlot) {

		int appointmentId = (getAppointmentsMaxId() + 1);
		prepStmt = null;

		int status = 0;

		try {

			prepStmt = databaseConnection.prepareStatement(QUERY_BOOKAPPOINTMENTFORPATIENT);
			prepStmt.setInt(1, appointmentId);
			prepStmt.setString(2, APPOINTMENT_PREFIX + appointmentId);
			prepStmt.setInt(3, patientObj.get().getId());
			prepStmt.setInt(4, physician.getId());
			prepStmt.setString(5, physician.getName());
			prepStmt.setTimestamp(6, new java.sql.Timestamp(appointmentDateConverted.getTime()));
			prepStmt.setString(7, appointmentSlot);

			status = prepStmt.executeUpdate();

			if (status > 0) {
				return APPOINTMENT_PREFIX + appointmentId;
			}
		}

		catch (SQLException e) {

			logger.error(e.getMessage());
		}
		return null;

	}

	public int getAppointmentsMaxId() {

		try {

			prepStmt = databaseConnection.prepareStatement(QUERY_GETMAXIDFORAPPOINTMENTS);

			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {

				return rs.getInt(1);

			}

		} catch (SQLException e) {

			logger.error(e.getMessage());
		}
		return 0;
	}

	public boolean checkAppointmentExistsForPatientId(int patientId) {

		try {

			prepStmt = databaseConnection.prepareStatement(QUERY_CHECKAPPOINTMENTEXISTSFORPATIENTID);

			prepStmt.setInt(1, patientId);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {

				return rs.getInt(1) > 0 ? true : false;

			}

		} catch (SQLException e) {

			logger.error(e.getMessage());
		}
		return false;
	}

	public boolean cancelAppointmentForPatientId(int patientId) {

		try {

			prepStmt = databaseConnection.prepareStatement(QUERY_CANCELAPPOINTMENTFORPATIENTID);

			prepStmt.setInt(1, patientId);

			int status = prepStmt.executeUpdate();

			if (status > 0) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {

			logger.error(e.getMessage());
		}
		return false;

	}

	public String rescheduleAppointmentForPatientId(Physician physician, Optional<Patient> patientObj,
			Date appointmentDateConverted, String appointmentSlot) {

		prepStmt = null;

		int status = 0;

		try {

			prepStmt = databaseConnection.prepareStatement(QUERY_RESCHEDULEAPPOINTMENTFORPATIENTID);

			prepStmt.setInt(1, physician.getId());
			prepStmt.setString(2, physician.getName());
			prepStmt.setTimestamp(3, new java.sql.Timestamp(appointmentDateConverted.getTime()));
			prepStmt.setString(4, appointmentSlot);
			prepStmt.setInt(5, patientObj.get().getId());

			status = prepStmt.executeUpdate();

			if (status > 0) {
				return getAppointmentIdByPatientId(patientObj.get().getId());
			}
		}

		catch (SQLException e) {

			logger.error(e.getMessage());
		}
		return null;

	}

	public String getAppointmentIdByPatientId(int patientId) {

		try {

			prepStmt = databaseConnection.prepareStatement(QUERY_GETAPPOINTMENTBYPATIENTID);

			prepStmt.setInt(1, patientId);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {

				String appointmentId = rs.getString(1);
				return appointmentId;

			}

		} catch (SQLException e) {

			logger.error(e.getMessage());
		}
		return "";
	}

	public Optional<Appointment> getAllAppointmentsByPatientId(int patientId) {

		Optional<Appointment> opt = Optional.ofNullable(null);
		try {

			prepStmt = databaseConnection.prepareStatement(QUERY_GETALLAPPOINTMENTSFORPATIENTID);

			prepStmt.setInt(1, patientId);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {

				opt = Optional.ofNullable(new Appointment(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4),
						rs.getString(5), rs.getDate(6), rs.getString(7)));

			}

		} catch (SQLException e) {

			logger.error(e.getMessage());
		}
		return opt;
	}

}
