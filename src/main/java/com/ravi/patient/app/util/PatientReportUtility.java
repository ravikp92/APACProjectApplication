package com.ravi.patient.app.util;

import java.io.FileOutputStream;
import java.util.Date;
import java.util.Optional;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ravi.patient.app.constant.PatientAppConstant;
import com.ravi.patient.app.model.Appointment;
import com.ravi.patient.app.model.Patient;
import com.ravi.patient.app.model.PatientMedicalHistory;

/**
 * @author RaviP
 *
 */
public class PatientReportUtility implements PatientAppConstant {

	/**
	 * 
	 * Generating patient report pdf
	 * @param patientObj
	 * @param patientMedicalHistoryObj
	 */
	public static void generatePatientReportPDF(Optional<Patient> patientObj,
			Optional<PatientMedicalHistory> patientMedicalHistoryObj) {
		try {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream("./Patient_"+patientObj.get().getId()+"_"+patientObj.get().getFirstName()+"_"+patientObj.get().getLastName() + "_" + new Date().getTime()));
			document.open();
			addContentForPatientReport(document, patientObj, patientMedicalHistoryObj);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private static void addContentForPatientReport(Document document, Optional<Patient> patientObj,
			Optional<PatientMedicalHistory> patientMedicalHistoryObj) throws DocumentException {
		Anchor anchor = new Anchor("Patient Report Details:", catFont);
		anchor.setName("Patient Report Details:");
		Chapter sectionChapter = new Chapter(new Paragraph(anchor), 1);
		Paragraph paragraph = new Paragraph();
		addEmptyLine(paragraph, 5);
		sectionChapter.add(paragraph);
		createTableForPatientReport(sectionChapter, patientObj, patientMedicalHistoryObj);
		document.add(sectionChapter);
	}


	private static void createTableForPatientReport(Section section, Optional<Patient> patientObj,
			Optional<PatientMedicalHistory> patientMedicalHistoryObj) throws BadElementException {
		PdfPTable table = new PdfPTable(2);

		PdfPCell c1 = new PdfPCell(new Phrase("Patient Data"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase(""));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		table.setHeaderRows(12);

		table.addCell("Patient Id");
		table.addCell(patientObj.get().getId().toString());

		table.addCell("Patient Full Name");
		table.addCell(patientObj.get().getFirstName() + " " + patientObj.get().getLastName());

		table.addCell("Patient DOB");
		table.addCell(patientObj.get().getDob().toString());

		table.addCell("Patient Gender");
		table.addCell(patientObj.get().getGender());

		table.addCell("Patient Address");
		table.addCell(patientObj.get().getAddress());

		table.addCell("Patient Phone Number");
		table.addCell(patientObj.get().getPhoneNumber());

		table.addCell("Patient Email Address");
		table.addCell(patientObj.get().getEmail());

		table.addCell("Patient Height");
		table.addCell(String.valueOf(patientMedicalHistoryObj.get().getHeight()));

		table.addCell("Patient Weight");
		table.addCell(String.valueOf(patientMedicalHistoryObj.get().getWeight()));

		table.addCell("Patient Blood Pressure");
		table.addCell(String.valueOf(patientMedicalHistoryObj.get().getBloodPressure()));

		table.addCell("Patient Pulse Rate");
		table.addCell(String.valueOf(patientMedicalHistoryObj.get().getPulseRate()));

		table.addCell("Patient Affected Organ");
		table.addCell(patientMedicalHistoryObj.get().getAffectedOrgan());
		section.add(table);

	}
	

	/**
	 * Generating Appointment report pdf
	 * @param appointmentObj
	 * @param patientObj
	 */
	public static void generateAppointmentReportPDF(Optional<Appointment> appointmentObj,
			Optional<Patient> patientObj) {
		try {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream("./Appointment_"+appointmentObj.get().getId()+"_"+patientObj.get().getFirstName()+"_"+patientObj.get().getLastName() + "_" + new Date().getTime()));
			document.open();
			addContentForAppointmentReport(document, appointmentObj, patientObj);

			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void addContentForAppointmentReport(Document document, Optional<Appointment> appointmentObj,
			Optional<Patient> patientObj) throws DocumentException {
		Anchor anchor = new Anchor("Appointment Report Details:", catFont);
		anchor.setName("Appointment Report Details:");

		// Second parameter is the number of the chapter
		Chapter chapter = new Chapter(new Paragraph(anchor), 1);
		Paragraph paragraph = new Paragraph();
		addEmptyLine(paragraph, 5);
		chapter.add(paragraph);
		//create a table to add
		createTableForAppointmentReport(chapter, appointmentObj, patientObj);

		//Adding data to document
		document.add(chapter);
	}
	private static void createTableForAppointmentReport(Section subCatPart, Optional<Appointment> appointmentObj,
			Optional<Patient> patientObj) {
		PdfPTable pdfTable = new PdfPTable(2);

		PdfPCell pdfCell = new PdfPCell(new Phrase("Appointment Details"));
		pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pdfTable.addCell(pdfCell);

		pdfCell = new PdfPCell(new Phrase(""));
		pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		pdfTable.addCell(pdfCell);

		pdfTable.setHeaderRows(8);

		pdfTable.addCell("Appointment Id");
		pdfTable.addCell(appointmentObj.get().getAppointmentId().toString());

		pdfTable.addCell("Patient Id");
		pdfTable.addCell(patientObj.get().getId().toString());

		pdfTable.addCell("Patient Full Name");
		pdfTable.addCell(patientObj.get().getFirstName() + " " + patientObj.get().getLastName());

		pdfTable.addCell("Patient Gender");
		pdfTable.addCell(patientObj.get().getGender());

		pdfTable.addCell("Patient Phone Number");
		pdfTable.addCell(patientObj.get().getPhoneNumber());

		pdfTable.addCell("Patient Email Address");
		pdfTable.addCell(patientObj.get().getEmail());

		pdfTable.addCell("Appointment Slot");
		pdfTable.addCell(appointmentObj.get().getAppointmentSlot());

		pdfTable.addCell("Appointment Date");
		pdfTable.addCell(appointmentObj.get().getAppointmentDate().toString());

		subCatPart.add(pdfTable);

	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
}
