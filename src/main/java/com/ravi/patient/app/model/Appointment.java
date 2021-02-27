package com.ravi.patient.app.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author RaviP
 *
 */
@SuppressWarnings("serial")
public class Appointment implements Serializable{

	
	private Integer id;
	private String appointmentId;
	private Integer patientId;
	private Integer doctorId;
	private String doctorName;
	private Date appointmentDate;
	private String appointmentSlot;

	public Appointment() {
	}

	public Appointment(Integer id, String appointmentId, Integer patientId, Integer doctorId,
			String doctorName, Date appointmentDate, String appointmentSlot) {
		super();
		this.id = id;
		this.appointmentId = appointmentId;
		this.patientId = patientId;
		this.doctorId = doctorId;
		this.doctorName = doctorName;
		this.appointmentDate = appointmentDate;
		this.appointmentSlot = appointmentSlot;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}

	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	public Integer getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public Date getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public String getAppointmentSlot() {
		return appointmentSlot;
	}

	public void setAppointmentSlot(String appointmentSlot) {
		this.appointmentSlot = appointmentSlot;
	}

	@Override
	public String toString() {
		return "Appointment [id=" + id + ", appointmentId=" + appointmentId + ", patientId=" + patientId + ", doctorId="
				+ doctorId + ", doctorName=" + doctorName + ", appointmentDate=" + appointmentDate
				+ ", appointmentSlot=" + appointmentSlot + "]";
	}

}
