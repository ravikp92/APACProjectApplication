package com.ravi.patient.app.dao;

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
import com.ravi.patient.app.model.Appointment;
import com.ravi.patient.app.model.Patient;
import com.ravi.patient.app.model.Physician;

/**
 * @author RaviP
 *
 */
public class AppointmentDAO {

	private static Logger logger = Logger.getLogger(AppointmentDAO.class);

	PreparedStatement prepStmt = null;
	Connection databaseConnection = null;

	public AppointmentDAO() {
		try {
			databaseConnection = DBconfiguration.getInstance().getConnection();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}

	private static final String APPOINTMENT_PREFIX = "Appointment_";

	public List<Physician> getAllPhysicians() {
		prepStmt = null;
		List<Physician> physicianList = new ArrayList<Physician>();

		try {
			prepStmt = databaseConnection.prepareStatement("SELECT * from PHYSICIAN");
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

			prepStmt = databaseConnection.prepareStatement(
					"SELECT * from APPOINTMENTSLOTS WHERE DRNAME=? AND APPOINTMENTDATE=? AND APPOINTMENTSLOT=?");
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

	public Optional<Patient> searchPatientById(int id) {
		prepStmt = null;
		Optional<Patient> opt = Optional.ofNullable(null);

		try {
			prepStmt = databaseConnection.prepareStatement("SELECT * from PATIENT WHERE ID=?");
			prepStmt.setInt(1, id);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				opt = Optional.ofNullable(new Patient(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
						rs.getDate(10), rs.getString(11)));
			}

		} catch (SQLException e) {

			logger.error(e.getMessage());
		}
		return opt;

	}

	public String bookAppointmentForPatient(Physician physician, Optional<Patient> patientObj,
			Date appointmentDateConverted, String appointmentSlot) {

		int appointmentId = (getMaxIdForAPPOINTMENTSLOTSTable() + 1);
		prepStmt = null;

		int status = 0;

		try {
			prepStmt = databaseConnection.prepareStatement(
					"INSERT INTO APPOINTMENTSLOTS(ID,APPOINTMENTID,PATIENTID,DRID,DRNAME,APPOINTMENTDATE,APPOINTMENTSLOT)"
							+ " VALUES(?,?,?,?,?,?,?);");
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
			} else {
				return "";
			}

		}

		catch (SQLException e) {

			logger.error(e.getMessage());
		}
		return null;

	}

	public int getMaxIdForAPPOINTMENTSLOTSTable() {

		try {
			prepStmt = databaseConnection.prepareStatement("SELECT MAX(ID) from APPOINTMENTSLOTS");

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
			prepStmt = databaseConnection.prepareStatement("SELECT ID from APPOINTMENTSLOTS WHERE PATIENTID=?");

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
			prepStmt = databaseConnection.prepareStatement("DELETE FROM APPOINTMENTSLOTS WHERE PATIENTID=? ");

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
			prepStmt = databaseConnection.prepareStatement(
					"UPDATE APPOINTMENTSLOTS SET DRID=?,DRNAME=?,APPOINTMENTDATE=?,APPOINTMENTSLOT=? "
							+ " WHERE PATIENTID=?;");

			prepStmt.setInt(1, physician.getId());
			prepStmt.setString(2, physician.getName());
			prepStmt.setTimestamp(3, new java.sql.Timestamp(appointmentDateConverted.getTime()));
			prepStmt.setString(4, appointmentSlot);
			prepStmt.setInt(5, patientObj.get().getId());

			status = prepStmt.executeUpdate();

			if (status > 0) {
				return getAppointmentIdByPatientId(patientObj.get().getId());
			} else {
				return "";
			}

		}

		catch (SQLException e) {

			logger.error(e.getMessage());
		}
		return null;

	}

	public String getAppointmentIdByPatientId(int patientId) {

		try {
			prepStmt = databaseConnection.prepareStatement("SELECT APPOINTMENTID from APPOINTMENTSLOTS WHERE PATIENTID=?");

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

	public Optional<Appointment> getAppointmentByPatientId(int patientId) {

		Optional<Appointment> opt = Optional.ofNullable(null);
		try {
			prepStmt = databaseConnection.prepareStatement("SELECT * from APPOINTMENTSLOTS WHERE PATIENTID=?");

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
