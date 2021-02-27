package com.ravi.patient.app.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author RaviP
 *
 */
@SuppressWarnings("serial")
public class Patient implements Serializable {


	private Integer id;
	private String firstName;
	private String lastName;
	private String address;
	private String email;
	private String phoneNumber;
	private String city;
	private String state;
	private String gender;
	private Date dob;
	private String ssn;

	public Patient() {

	}

	public Patient(Integer id, String firstName, String lastName, String address, String email, String phoneNumber,
			String city, String state, String gender, Date dob, String ssn) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.city = city;
		this.state = state;
		this.gender = gender;
		this.dob = dob;
		this.ssn = ssn;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	@Override
	public String toString() {
		return "Patient [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", address=" + address
				+ ", email=" + email + ", phoneNumber=" + phoneNumber + ", city=" + city + ", state=" + state
				+ ", gender=" + gender + ", dob=" + dob + ", ssn=" + ssn + "]";
	}


}
