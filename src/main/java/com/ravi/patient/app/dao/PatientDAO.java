/**
 * 
 */
package com.ravi.patient.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.ravi.patient.app.config.DBconfiguration;
import com.ravi.patient.app.constant.DBConstants;
import com.ravi.patient.app.model.Patient;
import com.ravi.patient.app.model.PatientMedicalHistory;

/**
 * @author RaviP
 *
 */
public class PatientDAO implements DBConstants {

	private static Logger logger = Logger.getLogger(PatientDAO.class);

	PreparedStatement prepStmt = null;
	Connection databaseConnection = null;

	public PatientDAO() {
		try {
			databaseConnection = DBconfiguration.getInstance().getConnection();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}

	public Optional<Patient> searchPatientById(int id) {

		Optional<Patient> opt = Optional.ofNullable(null);

		try {
			prepStmt = databaseConnection.prepareStatement(QUERY_SEARCHPATIENTBYID);
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

	public Optional<PatientMedicalHistory> searchPatientMedicalHistoryByPatientId(int id) {
		Optional<PatientMedicalHistory> opt = Optional.ofNullable(null);

		try {

			prepStmt = databaseConnection.prepareStatement(QUERY_SEARCHPATIENTMEDICALHISTBYPATIENTID);
			prepStmt.setInt(1, id);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {

				opt = Optional.ofNullable(new PatientMedicalHistory(rs.getInt(1), rs.getInt(2), rs.getDouble(3),
						rs.getDouble(4), rs.getDouble(5), rs.getDouble(6), rs.getString(7)));

			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return opt;

	}

	public int getPatientsMaxId() {

		try {

			prepStmt = databaseConnection.prepareStatement(QUERY_GETMAXPATIENTID);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}

		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return 0;

	}

	public int getPatientMedicalHistoryMaxId() {
		try {

			prepStmt = databaseConnection.prepareStatement(QUERY_GETMAXPATIENTMEDICALHISTORYID);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}

		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return 0;

	}

	public int deletePatientById(int id) {

		int result = 0;

		try {

			prepStmt = databaseConnection.prepareStatement(QUERY_DELETEPATIENTBYID);
			prepStmt.setInt(1, id);
			result = prepStmt.executeUpdate();

		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return result;

	}

	public int deletePatientMedicalHistoryByPatientId(int id) {

		int result = 0;

		try {
			prepStmt = databaseConnection.prepareStatement(QUERY_DELETEPATIENTMEDHISTORYBYID);
			prepStmt.setInt(1, id);
			result = prepStmt.executeUpdate();

		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return result;

	}

	public String createPatient(Patient patient, PatientMedicalHistory PatientMedicalHistory) {
		patient.setId(getPatientsMaxId() + 1);
		PatientMedicalHistory.setId(getPatientMedicalHistoryMaxId() + 1);
		int status = 0;

		try {
			prepStmt = databaseConnection.prepareStatement(QUERY_INSERTPATIENT);
			prepStmt.setInt(1, patient.getId());
			prepStmt.setString(2, patient.getFirstName());
			prepStmt.setString(3, patient.getLastName());
			prepStmt.setString(4, patient.getAddress());
			prepStmt.setString(5, patient.getEmail());
			prepStmt.setString(6, patient.getPhoneNumber());
			prepStmt.setString(7, patient.getCity());
			prepStmt.setString(8, patient.getState());
			prepStmt.setString(9, patient.getGender());
			prepStmt.setTimestamp(10, new java.sql.Timestamp(patient.getDob().getTime()));
			prepStmt.setString(11, patient.getSsn());

			status = prepStmt.executeUpdate();

			if (status > 0) {

				prepStmt = databaseConnection.prepareStatement(QUERY_INSERTPATIENTMEDIHISTORY);
				prepStmt.setInt(1, PatientMedicalHistory.getId());
				prepStmt.setInt(2, patient.getId());
				prepStmt.setDouble(3, PatientMedicalHistory.getHeight());
				prepStmt.setDouble(4, PatientMedicalHistory.getWeight());
				prepStmt.setDouble(5, PatientMedicalHistory.getBloodPressure());
				prepStmt.setDouble(6, PatientMedicalHistory.getPulseRate());
				prepStmt.setString(7, PatientMedicalHistory.getAffectedOrgan());

				status = prepStmt.executeUpdate();

				if (status > 0) {
					return "Patient and Medical History Record is added successfully!!";
				} else {
					return "Record addition failed!!";
				}

			} else {
				return "Patient Record addition failed!!";
			}

		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return null;

	}

	public int updatePatient(Patient patient) {
		try {

			prepStmt = databaseConnection.prepareStatement(QUERY_UPDATEPATIENT);

			prepStmt.setString(1, patient.getFirstName());
			prepStmt.setString(2, patient.getLastName());
			prepStmt.setString(3, patient.getAddress());
			prepStmt.setString(4, patient.getEmail());
			prepStmt.setString(5, patient.getPhoneNumber());
			prepStmt.setString(6, patient.getCity());
			prepStmt.setString(7, patient.getState());
			prepStmt.setString(8, patient.getGender());
			prepStmt.setTimestamp(9, new java.sql.Timestamp(patient.getDob().getTime()));
			prepStmt.setString(10, patient.getSsn());
			prepStmt.setInt(11, patient.getId());

			return prepStmt.executeUpdate();

		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return 0;

	}

	public int updatePatientMedicalHistory(PatientMedicalHistory PatientMedicalHistory) {
		try {

			prepStmt = databaseConnection.prepareStatement(QUERY_UPDATEPATIENTMEDHISTORY);

			prepStmt.setDouble(1, PatientMedicalHistory.getHeight());
			prepStmt.setDouble(2, PatientMedicalHistory.getWeight());
			prepStmt.setDouble(3, PatientMedicalHistory.getBloodPressure());
			prepStmt.setDouble(4, PatientMedicalHistory.getPulseRate());
			prepStmt.setString(5, PatientMedicalHistory.getAffectedOrgan());
			prepStmt.setInt(6, PatientMedicalHistory.getPatientId());

			return prepStmt.executeUpdate();

		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return 0;

	}

}
