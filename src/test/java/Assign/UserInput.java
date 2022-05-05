package Assign;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Dao.Dao;
import Logic.Validator;

public class UserInput {

	private static Logger log = LogManager.getLogger(UserInput.class);

	public static void main(String[] args) throws Exception {

		Scanner sc = new Scanner(System.in);

		// Welcome message method called1

		int userinput = Validator.welcome();

		if (userinput == 1) {
			log.info("~~~~Welcome to our Bank~~~~");

			log.info("Please fill the below details correctly");

			// Getting Name-------------------------------
			log.info("Enter your Name"); 
			String Name = sc.next();
			// Name validator
			Name = Validator.checkname(Name);

			// Getting age-------------------------------
			log.info("Enter your age");
			int age = sc.nextInt();
			// Age validator
			age = Validator.checkAge(age);

			// Getting MobileNumbe-------------------------------
			log.info("Enter your 10 Digit Mobile Number");
			String Mobile_Number = sc.next();
			// Mobile_Number validation
			Mobile_Number = Validator.checkMobile(Mobile_Number);

			// Getting Gender-------------------------------
			log.info("Select gender");
			String Gender = null;
			log.info(" Press - 1 for Male ");
			log.info(" Press - 2 for Female ");
			int options = sc.nextInt();
			// Gender validator
			Gender = Validator.checkGender(options);
			log.info("You selected gender as " + Gender);

			// Getting password from user-------------------------------
			log.info("Enter your Password");
			String Password = sc.next();
			// Password Validator
			Password = Validator.checkPassword(Password);

			// Getting Amount deposited bye the user-------------------------------
			log.info("Enter the Amount that you want to Deposit ");
			int Amount = sc.nextInt();
			// Amount validator
			Amount = Validator.checkAmount(Amount);
			log.info(Amount + " is Deposited to your account");

			RegisterDetails obj = new RegisterDetails(Name, age, Mobile_Number, Gender, Amount, Password);
			log.info("  ");

			// Display the details entered by the user
			log.info("Check the details entered by you");
			log.info("  ");
			log.info("Your name = " + obj.getName());
			log.info("Your age = " + obj.getAge());
			log.info("Your MobileNumber = " + obj.getMobile_Number());
			log.info("Your Gender = " + obj.getGender());
			log.info("Deposited Amount = " + obj.getAmount());
			log.info("Your Password = " + obj.getPassword());
			log.info("Thnaks for creating a account in our Bank");
			log.info("Your account will activate in 2 working days");
			Dao.input(obj);
			log.info("");

			// Back to main menu
			log.info("For Main menu press 1");
			int input = sc.nextInt();
			while (input >= 2) {
				log.info("For Main menu press 1");
				input = sc.nextInt();
			}
			if (input == 1) {
				userinput = Validator.welcome();
				while (userinput == 1) {
					log.info("Already you are registered");
					log.info("Press 2 for log in");
					userinput = sc.nextInt();
				}
			}

		}
		if (userinput == 2) {

			log.info("Hello User :)");
			log.info("Enter your Mobile Number");
			String Mobile_Number = sc.next();
			Mobile_Number = Validator.checkMobile(Mobile_Number);
			log.info("Enter your Password");
			String Password = sc.next();
			Password = Validator.checkPassword(Password);
			int loginvalid;
			int useraccountstatus;

			// Login_Vallidation
			loginvalid = Validator.loginValidation(Mobile_Number, Password);
			useraccountstatus = Validator.statusValidator(Mobile_Number);

			if(loginvalid==1) {
				log.info("Logined successfully");
				Validator.userMenu(Mobile_Number);
			}else {
				System.out.println("Invalid MobileNumber and Password");
			}

		}
		sc.close();
	}

}