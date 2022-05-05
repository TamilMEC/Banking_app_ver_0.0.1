package Logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
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

	public static int statusValidator(String num) throws Exception {
		String query = "select status from Banking where status='inactive' and Mobile_Number='" + num + "' ";
		ResultSet y = Dao.displaycall(query);
		int rows = 0;
	
		if (y.next()) {
			rows++;
		}
		return rows;
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
	public static int loginValidation(String Mobile_Number, String Password) throws ClassNotFoundException, SQLException {
	//	String query = null;
//		StringBuilder querystr = new StringBuilder();
//			querystr.append("select Mobile_Number,Password from Banking where Mobile_Number=('").append(Mobile_Number)
//					.append("') and Password = ('").append(Password).append("')").append("and ").append("User='user'").append("').append("status=active");
			String query = "select * from Banking where Mobile_Number='"+Mobile_Number+"' and PASSWORD = '"+Password+"' and status = 'active'";
			//
					//query = querystr.toString();
			ResultSet rs = Dao.displaycall(query);
			if(rs.next()) {
				return 1; 
			}
			else 
			{
				return 0;
	}
			
	}
	
	
	
	public static int adminLoginValidation(String Mobile_Number, String Password) throws ClassNotFoundException, SQLException {
		String query = null;
		StringBuilder querystr = new StringBuilder();
			querystr.append("select Mobile_Number,Password from Banking where Mobile_Number=('").append(Mobile_Number)
					.append("') and Password = ('").append(Password).append("')").append("and ").append("User='Admin'");
			query = querystr.toString();
			ResultSet rs = Dao.displaycall(query);
			if(rs.next()) {
				return 1; 
			}
			else 
			{
				return 0;
	}
			
	}
	
	

	// UserMenu Display
	public static void userMenu(String mobileNumber) throws Exception {
		// k = w;
		log.info("1.View your account details");
		log.info("2.Withdraw");
		log.info("3.Deposite");
		log.info("4.Amount Transfer");
		log.info("5.Transaction details");
		log.info("6.Back to Welcome page");
		log.info("7.To Exit from app");
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
		if(g==5) {
			transactiondetails(mobileNumber);
		}
		if (g == 6) {
			welcome();
		}
		if (g == 7) {
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

	private static void transactiondetails(String mobileNumber) throws Exception, SQLException {
		String query = "select * from transactioncore where MobileNumber='"+mobileNumber+"'";
		ResultSet rs = Dao.displaycall(query);
		while(rs.next()) {
		String UserAccountNumber = rs.getString("UserAccountNumber");
		int Amount = rs.getInt("Amount");
		String TYPE = rs.getString("TYPE");
		String AccountNumber = rs.getString("AccountNumber");
		String currentBalance = rs.getString("currentBalance");
		String Datetime = rs.getString("Datetime"); 
		System.out.println("Your account Number = "+UserAccountNumber+"");
		System.out.println("Amount = "+Amount+"");
		System.out.println("Transaction type = "+TYPE+"");
		System.out.println("Account number = " +AccountNumber+"");
		System.out.println("Your current balnce = " +currentBalance+"");
		System.out.println("Transaction date and time = "+Datetime+"");
		System.out.println();
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
			int accountnumber=0;
			String query = "select * from Banking where Mobile_Number='" + mobileNumber + "'";
			ResultSet details = Dao.displaycall(query);
			while (details.next()) {
				Amount = details.getInt("Amount");
				accountnumber=details.getInt("ACCOUNT_NUMBER");
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
				updateAmount(totalamount, mobileNumber,accountnumber,withdrawAmount);
			}

		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}

	}

	// UpdateAmount in Sql
	private static void updateAmount(int totalamount, String mobilenumber,int accountnumber,int withdrawamount) {
		LocalDateTime timestamp = LocalDateTime.now();
		String time = timestamp.toString();
		try {
			String query = "update Banking set Amount='" + totalamount + "'  where Mobile_Number='" + mobilenumber
					+ "'";
			int row=Dao.updateSql1(query);
			System.out.println("transcation running");
			if(row==1) {
			
				String query1="insert into transactioncore(MobileNumber,UserAccountNumber,Amount,TYPE,CurrentBalance,DATETIME) VALUES('"+mobilenumber+"','"+accountnumber+"','"+withdrawamount+"','Debited',"+totalamount+",'"+time+"')";
				Dao.updateSql1(query1);
			}
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
			String query = "select * from Banking where Mobile_Number='" + mobilenumber + "'";
			ResultSet details = Dao.displaycall(query);
			if (details.next()) {
				int Amount = details.getInt("Amount");
				int totalamount = Amount + DepositAmount;
				int accountNumber = details.getInt("ACCOUNT_NUMBER");
				log.info(DepositAmount + " = Amount Deposited successfull ");
				updateDepositedAmount(mobilenumber, totalamount,accountNumber,DepositAmount);
			}
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
	}

	public static void updateDepositedAmount(String mobilenumber, int totalamount,int accountNumber,int depositAmount) {
		LocalDateTime timestamp = LocalDateTime.now();
		String time = timestamp.toString();
		try {
			String queryamount = "update Banking set Amount=" + totalamount + " where Mobile_Number='" + mobilenumber
					+ "'";
			int a = Dao.updateSql1(queryamount);
			if(a==1) {
				
				String query1="insert into transactioncore(MobileNumber,UserAccountNumber,Amount,TYPE,CurrentBalance,DATETIME) VALUES('"+mobilenumber+"','"+accountNumber+"','"+depositAmount+"','Credited',"+totalamount+",'"+time+"')";
				Dao.updateSql1(query1);
			}
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
		LocalDateTime timestamp = LocalDateTime.now();
		String time = timestamp.toString();
		int totalamount = 0;
		int amountTransfer = 0;
		int amounts = 0;
		String mobileNumber2=null;
		int accountnumbers=0;
		System.out.println("Enter the account number");
		int transferAccountno = i.nextInt();
		String queryreceiveramo = "select * from Banking where ACCOUNT_NUMBER='" + transferAccountno + "'";
		ResultSet details = Dao.displaycall(queryreceiveramo);
		if (details.next()) {
			amounts = details.getInt("Amount");
			mobileNumber2 = details.getString("Mobile_Number");
			accountnumbers= details.getInt("ACCOUNT_NUMBER");
			System.out.println("Enter the amount to transfer");
			amountTransfer = i.nextInt();
			totalamount = amounts + amountTransfer;

		}
		int senderamounts = 0;
		int accountNumber=0;
		String queryreceiveramount = "select * from Banking where Mobile_Number='" + mobileNumber + "'";
		ResultSet get = Dao.displaycall(queryreceiveramount);
		if (get.next()) {
			senderamounts = get.getInt("Amount");
			accountNumber = get.getInt("ACCOUNT_NUMBER");
		}
		if (senderamounts < amountTransfer) {
			System.out.println("Insufficient Balance");
			System.out.println("Try Again Later  :(  ");
			System.exit(1);
		}

		String querytransfer = "update Banking set Amount='" + totalamount + "' where ACCOUNT_NUMBER= '"
				+ transferAccountno + "' ";
		int a = Dao.updateSql1(querytransfer);
		String query1="insert into transactioncore(MobileNumber,UserAccountNumber,Amount,TYPE,AccountNumber,CurrentBalance,DATETIME) VALUES('"+mobileNumber2+"','"+accountnumbers+"','"+amountTransfer+"','Received','"+accountNumber+"',"+totalamount+",'"+time+"')";
		Dao.updateSql1(query1);
		if (a == 1) {
			updateSenderAccount(mobileNumber, amountTransfer,transferAccountno,amounts,accountNumber);
				
		} else {
			System.out.println("Transfer failed");
		}

	}

	public static void updateSenderAccount(String mblNumber, int amountTransfer ,int transferAccountno,int amounttoanotheuser ,int accountNumber) throws Exception {
		LocalDateTime timestamp = LocalDateTime.now();
		String time = timestamp.toString();
		int amounts = 0;
		String query = "select * from Banking where Mobile_Number='" + mblNumber + "' ";
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

			String query1="insert into transactioncore(MobileNumber,UserAccountNumber,Amount,TYPE,AccountNumber,CurrentBalance,DATETIME) VALUES('"+mblNumber+"','"+accountNumber+"','"+amountTransfer+"','Transfered','"+transferAccountno+"',"+updatedAmt+",'"+time+"')";
			Dao.updateSql1(query1);
		}
	}

	public static void adminlogin() throws Exception {
		log.info("Admin page");
		log.info("Enter Mobile Number");
		String mobilenumber = i.next();
		log.info("Enter password");
		String password = i.next();
		int row = adminLoginValidation(mobilenumber, password);
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