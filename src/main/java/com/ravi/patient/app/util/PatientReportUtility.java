package com.ravi.patient.app.util;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
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
	 * 
	 * @param patientObj
	 * @param patientMedicalHistoryObj
	 */
	public static void generatePatientReportPDF(Optional<Patient> patientObj,
			Optional<PatientMedicalHistory> patientMedicalHistoryObj) {
		try {
			Document document = new Document();
			PdfWriter.getInstance(document,
					new FileOutputStream(PATIENT_FILE + SEPARATOR + patientObj.get().getId() + SEPARATOR
							+ patientObj.get().getFirstName() + SEPARATOR + patientObj.get().getLastName() + SEPARATOR
							+ new Date().getTime()));
			document.open();
			addPatientDataIntoPdf(document, patientObj, patientMedicalHistoryObj);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void addPatientDataIntoPdf(Document document, Optional<Patient> patientObj,
			Optional<PatientMedicalHistory> patientMedicalHistoryObj) throws DocumentException {
	
		Paragraph para = new Paragraph();
		para.add(new Paragraph(Font.BOLD, PATIENT_REPORT_TITLE));
		addEmptyLine(para, 1);
		para.add(new Paragraph("--------------------------------"));
		addEmptyLine(para, 2);
		para.add(new Paragraph("Report Generated Date :"));
		addEmptyLine(para, 1);
		para.add(new Paragraph(Font.BOLD, LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/YYYY")).toString()));
		addEmptyLine(para, 1);
		para.add(new Paragraph(
				"--------------------------------------------------------------------------------------------------------------------"));
		addEmptyLine(para, 2);
		document.add(para);
		createTableForPatientReport(document, patientObj, patientMedicalHistoryObj);
	}

	private static void createTableForPatientReport(Document document, Optional<Patient> patientObj,
			Optional<PatientMedicalHistory> patientMedicalHistoryObj) throws DocumentException {
		// Create two columns
		PdfPTable table = new PdfPTable(2);

		// Add patient data header column1 and align it in center. Add in row1
		PdfPCell c1 = new PdfPCell(new Phrase(Font.BOLD,"Patient Demographic"));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase(Font.BOLD,"Patient Details"));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(c1);

		// add 12 rows more for data
		table.setHeaderRows(12);

		table.addCell("Patient Id");
		table.addCell(patientObj.get().getId().toString());

		table.addCell("Full Name");
		table.addCell(patientObj.get().getFirstName() + " " + patientObj.get().getLastName());

		table.addCell("Date of Birth");
		table.addCell(patientObj.get().getDob().toString());

		table.addCell("Gender");
		table.addCell(patientObj.get().getGender());

		table.addCell("Address");
		table.addCell(patientObj.get().getAddress());

		table.addCell("Phone Number");
		table.addCell(patientObj.get().getPhoneNumber());

		table.addCell("Email Address");
		table.addCell(patientObj.get().getEmail());

		table.addCell("Height");
		table.addCell(String.valueOf(patientMedicalHistoryObj.get().getHeight()));

		table.addCell("Weight");
		table.addCell(String.valueOf(patientMedicalHistoryObj.get().getWeight()));

		table.addCell("Blood Pressure");
		table.addCell(String.valueOf(patientMedicalHistoryObj.get().getBloodPressure()));

		table.addCell("Pulse Rate");
		table.addCell(String.valueOf(patientMedicalHistoryObj.get().getPulseRate()));

		table.addCell("Affected Organ");
		table.addCell(patientMedicalHistoryObj.get().getAffectedOrgan());
		document.add(table);

	}

	/**
	 * Generating Appointment report pdf
	 * 
	 * @param appointmentObj
	 * @param patientObj
	 */
	public static void generateAppointmentReportPDF(Optional<Appointment> appointmentObj,
			Optional<Patient> patientObj) {
		try {
			Document document = new Document();
			PdfWriter.getInstance(document,
					new FileOutputStream(APPOINTMENT_FILE + SEPARATOR + appointmentObj.get().getId() + SEPARATOR
							+ patientObj.get().getFirstName() + SEPARATOR + patientObj.get().getLastName() + SEPARATOR
							+ new Date().getTime()));
			document.open();
			addAppointmentDataIntoPdf(document, appointmentObj, patientObj);

			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void addAppointmentDataIntoPdf(Document document, Optional<Appointment> appointmentObj,
			Optional<Patient> patientObj) throws DocumentException {

		Paragraph para = new Paragraph();
		para.add(new Paragraph(Font.BOLD, APPOINTMENT_REPORT_TITLE));
		addEmptyLine(para, 1);
		para.add(new Paragraph("-----------------------------------"));
		addEmptyLine(para, 2);
		para.add(new Paragraph("Report Generated Date :"));
		addEmptyLine(para, 1);
		para.add(new Paragraph(Font.BOLD, LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/YYYY")).toString()));
		addEmptyLine(para, 1);
		para.add(new Paragraph(
				"--------------------------------------------------------------------------------------------------------------------"));
		addEmptyLine(para, 2);
		document.add(para);
		// create a table to add
		createTableForAppointmentReport(document, appointmentObj, patientObj);
	}

	private static void createTableForAppointmentReport(Document document, Optional<Appointment> appointmentObj,
			Optional<Patient> patientObj) throws DocumentException {
		PdfPTable pdfTable = new PdfPTable(2);

		PdfPCell pdfCell = new PdfPCell(new Phrase("Appointment Info"));
		pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pdfTable.addCell(pdfCell);

		pdfCell = new PdfPCell(new Phrase("Details"));
		pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pdfTable.addCell(pdfCell);

		pdfTable.setHeaderRows(8);

		pdfTable.addCell("Appointment Id");
		pdfTable.addCell(appointmentObj.get().getAppointmentId().toString());

		pdfTable.addCell("Patient Id");
		pdfTable.addCell(patientObj.get().getId().toString());

		pdfTable.addCell("Full Name");
		pdfTable.addCell(patientObj.get().getFirstName() + " " + patientObj.get().getLastName());

		pdfTable.addCell("Gender");
		pdfTable.addCell(patientObj.get().getGender());

		pdfTable.addCell("Phone Number");
		pdfTable.addCell(patientObj.get().getPhoneNumber());

		pdfTable.addCell("Email Address");
		pdfTable.addCell(patientObj.get().getEmail());

		pdfTable.addCell("Appointment Slot");
		pdfTable.addCell(appointmentObj.get().getAppointmentSlot());

		pdfTable.addCell("Appointment Date");
		pdfTable.addCell(appointmentObj.get().getAppointmentDate().toString());

		document.add(pdfTable);

	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
}
