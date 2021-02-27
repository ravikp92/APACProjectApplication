package com.ravi.patient.app.validation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Validations {

	public static boolean validateNum(String height) {
		try {
			Double.parseDouble(height);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static String validateName(Scanner sc, String name) {
		while (name.length() < 5) {
			System.out.println("Name must be atleast 5 character in length !! ");
			System.out.print("Please try again : ");
			name = sc.nextLine();
		}
		return name;
	}

	public static String validateCityState(Scanner sc, String attribute, boolean flag) {
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
		DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		try {
			sdf.parse(dob);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}
}
