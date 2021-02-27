package com.ravi.patient.app.util;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.ravi.patient.app.constant.PatientAppConstant;
import com.ravi.patient.app.model.Patient;
import com.ravi.patient.app.model.Physician;

public class AppointmentUtility implements PatientAppConstant {

	/**
	 * Generating Appointment Pdf
	 * 
	 * @param physician
	 * @param patientObj
	 * @param appointmentDateConverted
	 * @param appointmentSlot
	 * @param resultMessage
	 */
	public static void generateAppointmentPDF(Physician physician, Optional<Patient> patientObj,
			Date appointmentDateConverted, String appointmentSlot, String resultMessage) {
		try {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(APPOINTMENT_FILE + SEPARATOR + new Date().getTime()));
			document.open();
			addMetaData(document, CONFIRM, CONFIRM);
			addTitlePage(document, physician, patientObj, appointmentDateConverted, appointmentSlot, resultMessage);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void addMetaData(Document document, String title, String subject) {
		document.addTitle(title);
		document.addSubject(subject);
		document.addKeywords("Java, PDF, iText");
		document.addAuthor(AUTHOR);
		document.addCreator(AUTHOR);
	}

	private static void addTitlePage(Document document, Physician physician, Optional<Patient> patientObj,
			Date appointmentDateConverted, String appointmentSlot, String resultMessage) throws DocumentException {

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		Paragraph para = new Paragraph();
		// We add one empty line
		addEmptyLine(para, 1);
		// Lets write a big header
		para.setAlignment(Paragraph.ALIGN_CENTER);
		para.add(new Paragraph("Receipt", catFont));

		addEmptyLine(para, 1);

		para.add(new Paragraph("Appointment Id: " + resultMessage, smallBold));

		addEmptyLine(para, 1);

		para.add(new Paragraph("Patient Id: " + patientObj.get().getId(), smallBold));

		addEmptyLine(para, 1);

		para.add(new Paragraph(
				"Patient Name: " + patientObj.get().getFirstName() + " " + patientObj.get().getLastName(), smallBold));

		addEmptyLine(para, 1);

		para.add(new Paragraph("Doctor Name: " + physician.getName(), smallBold));

		addEmptyLine(para, 1);

		para.add(new Paragraph("Appointment Date: " + dateFormat.format(appointmentDateConverted), smallBold));

		addEmptyLine(para, 1);

		para.add(new Paragraph("Appointment Slot: " + appointmentSlot, smallBold));

		addEmptyLine(para, 25);

		para.add(new Paragraph("Appointments are subjected to availability of doctor", redFont));

		document.add(para);
	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
}
