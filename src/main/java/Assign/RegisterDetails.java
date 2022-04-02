package Assign;

public class RegisterDetails {
	private String Name;
	private int age;
	private String Mobile_Number;
	private String Gender;
	private int Amount;
	private String Password;

	public RegisterDetails(String Name, int age, String Mobile_Number, String Gender, int Amount, String Password) {
		this.setName(Name);
		this.setAge(age);
		this.setMobile_Number(Mobile_Number);
		this.setGender(Gender);
		this.setAmount(Amount);
		this.setPassword(Password);
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getMobile_Number() {
		return Mobile_Number;
	}

	public void setMobile_Number(String mobile_Number) {
		Mobile_Number = mobile_Number;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public int getAmount() {
		return Amount;
	}

	public void setAmount(int amount) {
		Amount = amount;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

}
