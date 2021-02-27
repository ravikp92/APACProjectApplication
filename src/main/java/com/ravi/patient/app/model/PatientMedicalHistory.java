/**
 * 
 */
package com.ravi.patient.app.model;

import java.io.Serializable;

/**
 * @author RaviP
 *
 */
@SuppressWarnings("serial")
public class PatientMedicalHistory implements Serializable{

	
	private int id;
	private int patientId;
	private double height;
	private double weight;
	private double bloodPressure;
	private double pulseRate;
	private String affectedOrgan;
	public PatientMedicalHistory() {
		
	}
	public PatientMedicalHistory(int id, int patientId, double height, double weight, double bloodPressure,
			double pulseRate, String affectedOrgan) {
		super();
		this.id = id;
		this.patientId = patientId;
		this.height = height;
		this.weight = weight;
		this.bloodPressure = bloodPressure;
		this.pulseRate = pulseRate;
		this.affectedOrgan = affectedOrgan;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPatientId() {
		return patientId;
	}
	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getBloodPressure() {
		return bloodPressure;
	}
	public void setBloodPressure(double bloodPressure) {
		this.bloodPressure = bloodPressure;
	}
	public double getPulseRate() {
		return pulseRate;
	}
	public void setPulseRate(double pulseRate) {
		this.pulseRate = pulseRate;
	}
	public String getAffectedOrgan() {
		return affectedOrgan;
	}
	public void setAffectedOrgan(String affectedOrgan) {
		this.affectedOrgan = affectedOrgan;
	}
	@Override
	public String toString() {
		return "PatientMedicalHistory [id=" + id + ", patientId=" + patientId + ", height=" + height + ", weight="
				+ weight + ", bloodPressure=" + bloodPressure + ", pulseRate=" + pulseRate + ", affectedOrgan="
				+ affectedOrgan + "]";
	}
	
}
