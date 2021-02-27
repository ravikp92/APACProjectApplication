package com.ravi.patient.app.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.ravi.patient.app.enums.AffectedOrgan;
import com.ravi.patient.app.enums.Gender;
import com.ravi.patient.app.model.Patient;
import com.ravi.patient.app.model.PatientMedicalHistory;
import com.ravi.patient.app.service.PatientService;

public class PatientHelper {
	PatientService patientService = new PatientService();

	public void addPatient(Scanner scanner) throws ParseException {
		
		Scanner sc = new Scanner(System.in);

		Patient patient = new Patient();
		System.out.print("Enter Patient First Name: ");
		String firstName = sc.nextLine();
		firstName = validateName(sc, firstName);
		System.out.print("\nEnter Patient Last Name: ");
		String lastName = sc.nextLine();
		lastName = validateName(sc, lastName);
		System.out.print("\nEnter Email Id: ");

		String email = sc.nextLine();
		while (!isValidEmail(email)) {
			System.out.println("Please Enter Valid Email ID!! ");
			System.out.print("Please try again : ");
			email = sc.nextLine();
		}

		System.out.print("\nEnter Phone Number: ");

		String phoneNumber = sc.nextLine();
		while (!isValidPhoneNumber(phoneNumber)) {
			System.out.println("Please Enter Valid Phone Number!! ");
			System.out.print("Please try again : ");
			phoneNumber = sc.nextLine();
		}

		System.out.print("Enter City: ");
		String city = sc.nextLine();
		city = validateCityState(sc, city, true);

		System.out.print("Enter State: ");
		String state = sc.nextLine();
		state = validateCityState(sc, state, false);

		System.out.print("Enter Date of birth(MM/DD/YYYY): ");

		String dateOfBirth = sc.nextLine();
		while (!validateDob(dateOfBirth)) {
			System.out.println("Please Enter Valid DOB (MM/DD/YYYY)!! ");
			System.out.print("Please try again : ");
			dateOfBirth = sc.nextLine();
		}

		System.out.print("Enter Gender (M/F): ");
		String gender = sc.nextLine();
		boolean genderflag = false;
		while (!genderflag) {
			if (Gender.MALE.getGender().equalsIgnoreCase(gender)) {
				gender = Gender.MALE.name();
				genderflag = true;
			} else if (Gender.FEMALE.getGender().equalsIgnoreCase(gender)) {
				gender = Gender.FEMALE.name();
				genderflag = true;
			} else {
				System.out.println("Please Enter Valid Gender!! ");
				System.out.print("Please try again : ");
				gender = sc.nextLine();
				genderflag = false;
			}
		}

		System.out.print("Enter Height: ");
		String height = sc.nextLine();
		while (!validateNum(height)) {
			System.out.println("Please Enter Valid Height!! ");
			System.out.print("Please try again : ");
			height = sc.nextLine();
		}

		System.out.print("Enter Weight: ");
		String weight = sc.nextLine();
		while (!validateNum(weight)) {
			System.out.println("Please Enter Valid Weight!! ");
			System.out.print("Please try again : ");
			weight = sc.nextLine();
		}

		System.out.print("Enter Pulse Rate: ");
		String pr = sc.nextLine();
		while (!validateNum(pr)) {
			System.out.println("Please Enter Valid Pulse rate!! ");
			System.out.print("Please try again : ");
			pr = sc.nextLine();
		}

		System.out.print("Enter Blood Pressure: ");
		String bloodPressure = sc.nextLine();
		while (!validateNum(bloodPressure)) {
			System.out.println("Please Enter Valid Blood Pressure!! ");
			System.out.print("Please try again : ");
			bloodPressure = sc.nextLine();
		}

		System.out
				.print("Enter affected organ number :" + "LUNGS(1), HEART(2), LEG(3), HANDS(4), KIDNEY(5),OTHERS(6) ");
		int affectedOrgan = sc.nextInt();
		String affectedOrganName = "";
		boolean affectedOrganflag = false;
		while (!affectedOrganflag) {
			if (AffectedOrgan.LUNGS.getOrgan() == affectedOrgan) {
				affectedOrganName = AffectedOrgan.LUNGS.name();
				affectedOrganflag = true;
			} else if (AffectedOrgan.HEART.getOrgan() == affectedOrgan) {
				affectedOrganName = AffectedOrgan.HEART.name();
				affectedOrganflag = true;
			} else if (AffectedOrgan.LEG.getOrgan() == affectedOrgan) {
				affectedOrganName = AffectedOrgan.LEG.name();
				affectedOrganflag = true;
			} else if (AffectedOrgan.KIDNEY.getOrgan() == affectedOrgan) {
				affectedOrganName = AffectedOrgan.KIDNEY.name();
				affectedOrganflag = true;
			} else if (AffectedOrgan.HANDS.getOrgan() == affectedOrgan) {
				affectedOrganName = AffectedOrgan.HANDS.name();
				affectedOrganflag = true;
			} else if (AffectedOrgan.OTHERS.getOrgan() == affectedOrgan) {
				affectedOrganName = AffectedOrgan.OTHERS.name();
				affectedOrganflag = true;
			} else {
				System.out.println("Please Enter Valid Affected Organ Number!! ");
				System.out.print("Please try again : ");
				affectedOrgan = sc.nextInt();
				affectedOrganflag = false;
			}
		}

		patient.setFirstName(firstName);
		patient.setLastName(lastName);
		patient.setEmail(email);
		patient.setPhoneNumber(phoneNumber);
		patient.setCity(city);
		patient.setState(state);
		patient.setDob(new SimpleDateFormat("dd/MM/yyyy").parse(dateOfBirth));
		patient.setGender(gender);
		PatientMedicalHistory pateintMedicalHistory = new PatientMedicalHistory();
		pateintMedicalHistory.setHeight(Double.parseDouble(height));
		pateintMedicalHistory.setWeight(Double.parseDouble(weight));
		pateintMedicalHistory.setPulseRate(Double.parseDouble(pr));
		pateintMedicalHistory.setBloodPressure(Double.parseDouble(bloodPressure));
		pateintMedicalHistory.setAffectedOrgan(affectedOrganName);

		
		
	}

	private boolean validateNum(String height) {
		try {
			Double.parseDouble(height);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	private String validateName(Scanner sc, String name) {
		while (name.length() < 5) {
			System.out.println("Name must be atleast 5 character in length !! ");
			System.out.print("Please try again : ");
			name = sc.nextLine();
		}
		return name;
	}

	private String validateCityState(Scanner sc, String attribute, boolean flag) {
		while (attribute.length() < 3) {
			if (flag)
				System.out.println("City must be atleast 3 character in length !! ");
			else
				System.out.println("State must be atleast 3 character in length !! ");
			System.out.print("Please try again : ");
			attribute = sc.nextLine();
		}
		return attribute;
	}

	public static boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
	}

	public static boolean isValidPhoneNumber(String number) {
		String phoneNumRegex = "^(\\d{3}[- .]?){2}\\d{4}$";
		Pattern pat = Pattern.compile(phoneNumRegex);
		if (number == null)
			return false;
		return pat.matcher(number).matches();
	}

	public static boolean validateDob(String dob) {
		DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		sdf.setLenient(false);
		try {
			sdf.parse(dob);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	public void updatePatient(Scanner scanner) {
		// TODO Auto-generated method stub
		
	}

	public void deletePatient(Scanner scanner) {
		// TODO Auto-generated method stub
		
	}

	public void searchPatient(Scanner scanner) {
		// TODO Auto-generated method stub
		
	}
}
