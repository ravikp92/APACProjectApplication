package com.ravi.patient.app.constant;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;

public interface PatientAppConstant {

	String SEPARATOR = "_";
	String APPOINTMENT_FILE = "./Appointment";
	String CONFIRM = "Appointment Confirmed!! ";
	String AUTHOR = "Ravi Puri";
	Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
	Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	String APPOINTMENT_PREFIX = "Appointment_";

}
