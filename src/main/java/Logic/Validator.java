package Logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import Dao.Dao;

public class Validator {

	private static Logger log = LogManager.getLogger(Validator.class);

	static Scanner i = new Scanner(System.in);

	// welcome
	public static int welcome() throws Exception {
		// Welcome Message
		log.info("~~~~  Welcome to Tamil Bank  ~~~~");
		log.info("For new Registeration - press 1");
		log.info("For Login - press 2");
		log.info("For Admin Login 3");
		int option = i.nextInt();

		if (option == 3) {
			adminlogin();
		}

		while (option >= 4 || option <= 0) {
			System.err.println("Press a valid number");
			option = i.nextInt();
		}

		return option;

	}

	// Name validation
	public static String checkname(String Name) {
		while (Name.contains("1") || Name.contains("2") || Name.contains("3") || Name.contains("4")
				|| Name.contains("5") || Name.contains("6") || Name.contains("7") || Name.contains("8")
				|| Name.contains("9") || Name.contains("0")) {
			System.err.println("Enter a valid name");
			Name = i.next();
		}

		while (Name.length() < 2 || Name.length() >= 20) {
			System.err.println("Enter a valid name");
			Name = i.next();

		}
		return Name;
	}

	// Age validation
	public static int checkAge(int age) throws Exception {
		while (age < 18 || age > 80) {
			System.err.println("Enter a valid number");
			age = i.nextInt();
		}
		return age;
	}

	// MobileNumber validation
	public static String checkMobile(String Mobile_Number) throws Exception {
		while (Mobile_Number.length() < 10 || Mobile_Number.length() > 10) {
			System.err.println("Enter a valid Number");
			Mobile_Number = i.next();
		}
		return Mobile_Number;
	}

	// Gender validator
	public static String checkGender(int option) throws Exception {
		while (option >= 3) {
			System.err.println("Enter a valid Number 1 or 2");
			option = i.nextInt();
		}
		if (option == 1) {
			String gender = "Male";
			return gender;
		}
		if (option == 2) {
			String gender = "Female";
			return gender;
		}
		return null;
	}

	// Amount validation
	public static int checkAmount(int Amount) throws Exception {
		while (Amount == 0 || Amount < 500) {
			System.err.println("Deposite amount above 500");
			Amount = i.nextInt();
		}

		return Amount;
	}

	// Password validator
	public static String checkPassword(String password) throws Exception {
		while (password.length() < 8 || password.length() > 16) {
			System.err.println("Enter a valid password ");
			password = i.next();
		}
		return password;
	}

	// Login validator
	@SuppressWarnings("finally")
	public static int loginValidation(String Mobile_Number, String Password) {
		String query = null;
		int result = 0;
		StringBuilder querystr = new StringBuilder();
		try {
			querystr.append("select Mobile_Number,Password from Banking where Mobile_Number=('").append(Mobile_Number)
					.append("') and Password = ('").append(Password).append("')");
			query = querystr.toString();
			ResultSet rs = Dao.displaycall(query);
			while (rs.next()) {
				result++; 
			}
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			return result;
		}
	}

	public static int statusValidator(String num) throws Exception {
		String query = "select status from Banking where status='inactive' and Mobile_Number='" + num + "' ";
		ResultSet y = Dao.displaycall(query);
		int rows = 0;

		if (y.next()) {
			rows++;
		}
		return rows;
	}

	// UserMenu Display
	public static void userMenu(String mobileNumber) throws Exception {
		// k = w;
		log.info("1.View your account details");
		log.info("2.Withdraw");
		log.info("3.Deposite");
		log.info("4.Amount Transfer");
		log.info("5.Back to Welcome page");
		log.info("6.To Exit from app");
		int g = i.nextInt();
		while (g > 5) {
			log.info("Enter a valid option");
			g = i.nextInt();
		}
		// View Account Details
		if (g == 1) {
			detailsDisplay(mobileNumber);
		}
		if (g == 2) {
			withdraw(mobileNumber);
		}
		if (g == 3) {
			deposit(mobileNumber);

		}
		if (g == 4) {
			amountTransfer(mobileNumber);
		}
		if (g == 5) {
			welcome();
		}
		if (g == 6) {
			log.info("Are you sure!");
			log.info("Press 1 to Exit");
			log.info("Press 2 to Back");
			int h = i.nextInt();
			if (h == 1) {
				System.exit(0);
			}
			if (h == 2) {
				userMenu(mobileNumber);
			}

		}

	}

	// Display Details to user
	public static void detailsDisplay(String logid) throws Exception {
		try {
			String query = "select * from Banking where Mobile_Number='" + logid + "'";
			ResultSet details = Dao.displaycall(query);
			while (details.next()) {
				String Name = details.getString("Name");
				int Accnumber = details.getInt("ACCOUNT_NUMBER");
				int age = details.getInt("age");
				String Gender = details.getString("Gender");
				String Mobile_Number = details.getString("Mobile_Number");
				int Amount = details.getInt("Amount");
				log.info("Your name : " + Name);
				log.info("Your Account Number :" + Accnumber);
				log.info("Your age : " + age);
				log.info("Your Gender : " + Gender);
				log.info("Your Mobile_Number : " + Mobile_Number);
				log.info("Available Balance : " + Amount);
				log.info("Enter 1 to go back to user menu");
				int u = i.nextInt();
				while (u >= 2) {
					log.info("Enter a valid option");
					u = i.nextInt();
				}

				if (u == 1) {
					userMenu(Mobile_Number);
				}

			}
		} catch (Exception e) {
			e.getMessage();
		}

	}

	// withdraw Details
	//@SuppressWarnings("unused")
	private static void withdraw(String mobileNumber) throws Exception {
		try {
			int Amount = 0;
			String query = "select Amount from Banking where Mobile_Number='" + mobileNumber + "'";
			ResultSet details = Dao.displaycall(query);
			if (details.next()) {
				Amount = details.getInt("Amount");
			}
			log.info("Your available amount = " + Amount);
			log.info("Enter the amount that you want to withdraw");
			int withdrawAmount = i.nextInt();
			if (Amount < withdrawAmount) {
				System.out.println("Insufficient Account Balance");
				System.exit(1);
			} else {
				log.info(withdrawAmount + " = Amount withdraw successfull ");
				int totalamount = Amount - withdrawAmount;
				updateAmount(totalamount, mobileNumber);
			}

		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}

	}

	// UpdateAmount in Sql
	private static void updateAmount(int totalamount, String mobilenumber) {
		try {
			String query = "update Banking set Amount='" + totalamount + "'  where Mobile_Number='" + mobilenumber
					+ "'";
			Dao.updateSql1(query);
			log.info("Your current balance = " + totalamount);

			log.info("Enter 1 to go back to user menu");
			int option = i.nextInt();
			while (option >= 2) {
				log.info("Enter a valid number");
				option = i.nextInt();
			}
			if (option == 1) {
				userMenu(mobilenumber);
			}

		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}

	}

	public static void deposit(String mobilenumber) throws Exception {
		try {
			log.info("Enter the amount that you want to deposite in your account");
			int DepositAmount = i.nextInt();
			String query = "select Amount from Banking where Mobile_Number='" + mobilenumber + "'";
			ResultSet details = Dao.displaycall(query);
			if (details.next()) {
				int Amount = details.getInt("Amount");
				int totalamount = Amount + DepositAmount;
				log.info(DepositAmount + " = Amount Deposited successfull ");
				updateDepositedAmount(mobilenumber, totalamount);
			}
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
	}

	public static void updateDepositedAmount(String mobilenumber, int totalamount) {

		try {
			String queryamount = "update Banking set Amount=" + totalamount + " where Mobile_Number='" + mobilenumber
					+ "'";
			int a = Dao.updateSql1(queryamount);
			if (a > 0) {
				log.info("Your current balance = " + totalamount);
			} else {
				System.exit(1);
			}
			log.info("Enter 1 to go back to user menu");
			int option = i.nextInt();
			while (option >= 2) {
				log.info("Enter a valid number");
				option = i.nextInt();
			}
			if (option == 1) {
				userMenu(mobilenumber);
			}

		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
	}

	public static void amountTransfer(String mobileNumber) throws Exception {

		int totalamount = 0;
		int amountTransfer = 0;
		int amounts = 0;
		System.out.println("Enter the account number");
		int transferAccountno = i.nextInt();
		String queryreceiveramo = "select Amount from Banking where ACCOUNT_NUMBER='" + transferAccountno + "'";
		ResultSet details = Dao.displaycall(queryreceiveramo);
		if (details.next()) {
			amounts = details.getInt("Amount");
			System.out.println("Enter the amount to transfer");
			amountTransfer = i.nextInt();
			totalamount = amounts + amountTransfer;

		}
		int senderamounts = 0;
		String queryreceiveramount = "select Amount from Banking where Mobile_Number='" + mobileNumber + "'";
		ResultSet get = Dao.displaycall(queryreceiveramount);
		if (get.next()) {
			senderamounts = get.getInt("Amount");
		}
		if (senderamounts < amountTransfer) {
			System.out.println("Insufficient Balance");
			System.out.println("Try Again Later  :(  ");
			System.exit(1);
		}

		String querytransfer = "update Banking set Amount='" + totalamount + "' where ACCOUNT_NUMBER= '"
				+ transferAccountno + "' ";
		int a = Dao.updateSql1(querytransfer);
		if (a == 1) {
			updateSenderAccount(mobileNumber, amountTransfer);
		} else {
			System.out.println("Transfer failed");
		}

	}

	public static void updateSenderAccount(String mblNumber, int amountTransfer) throws Exception {
		int amounts = 0;
		String query = "select amount from Banking where Mobile_Number='" + mblNumber + "' ";
		ResultSet details = Dao.displaycall(query);
		if (details.next()) {
			amounts = details.getInt("Amount");
		}
		int updatedAmt = amounts - amountTransfer;
		String querytransfer = "update Banking set Amount='" + updatedAmt + "' where Mobile_Number= '" + mblNumber
				+ "' ";
		int a = Dao.updateSql1(querytransfer);
		if (a == 1) {
			System.out.println("success");
		}
	}

	public static void adminlogin() throws Exception {
		log.info("Admin page");
		log.info("Enter Mobile Number");
		String mobilenumber = i.next();
		log.info("Enter password");
		String password = i.next();
		int row = loginValidation(mobilenumber, password);
		if (row == 1) {
			adminMenu();
		} else {
			System.out.println("Enter a valid id / pass");
			System.out.println("Try Again :(");
		}

	}

	public static void adminMenu() throws Exception {
		log.info("Welcome Admin");
		System.err.println("!__DON'T SHARE ANY INFORMATION ABOUT USERS TO UNAUTHOURISED PERSON__!");
		log.info("   ");
		log.info("Press-1 to Search user");
		log.info("Press-2 to Acitvate User Account");
		int options = i.nextInt();
		while (options >= 3) {
			log.info("Enter valid key");
			options = i.nextInt();
		}
		if (options == 1) {
			log.info("Enter user accountNumber");
			int accountNumber = i.nextInt();
			userDetailsToadmin(accountNumber);
		}
		if (options == 2) {
			showInactiveUsers();
			activatUser();
		}

	}

	public static void userDetailsToadmin(int accountNumber) throws Exception {
		String Name = null;
		try {
			String query = "select * from Banking where account_number='" + accountNumber + "'";
			ResultSet details = Dao.displaycall(query);
			while (details.next()) {
				Name = details.getString("Name");
				int age = details.getInt("age");
				String Gender = details.getString("Gender");
				String Mobile_Number = details.getString("Mobile_Number");
				// String Password = details.getString("Password");
				int Amount = details.getInt("Amount");
				log.info("User name : " + Name);
				log.info("User age : " + age);
				log.info("User Gender : " + Gender);
				log.info("User Mobile_Number : " + Mobile_Number);

				log.info("Password is Confidential");

				log.info("Available Balance : " + Amount);

			}

			log.info("Enter 1 to go back");
			log.info("Enter 2 to go Welcome Page");
			int options = i.nextInt();
			if (options == 1) {
				adminMenu();
			}
			if (options == 2) {
				welcome();
			} else {
				throw new Exception("Enter a valid number");
			}
		} catch (Exception e) {
			e.getMessage();
		}

	}

	public static void showInactiveUsers() throws Exception {
		String name = null;
		int accountNumber = 0;
		String query = "select name,account_number from Banking where status='inactive'";
		ResultSet obj = Dao.displaycall(query);
		while (obj.next()) {
			name = obj.getString("name");
			accountNumber = obj.getInt("account_number");
			System.out.printf(name);
			System.out.printf("\t" + accountNumber);
			log.info(" ");
		}

	}

	public static void activatUser() throws Exception {
		log.info("Enter 1 to activate all the users");
		int options = i.nextInt();
		if (options == 1) {
			String query = "update Banking set status='active' where status='inactive'";
			Dao.updateSql1(query);
			log.info("Activated Succesfully");
		} else {

			System.out.println("Not activated");
			System.exit(0);
		}

	}
}