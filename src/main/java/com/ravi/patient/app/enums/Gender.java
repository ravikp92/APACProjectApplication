package com.ravi.patient.app.enums;

public enum Gender {
	  MALE("M"),FEMALE("F");

	private String gender; 
	  
    // getter method 
    public String getGender() 
    { 
        return this.gender; 
    } 
  
    // enum constructor - cannot be public or protected 
    private Gender(String gender) 
    { 
        this.gender = gender; 
    } 
}
