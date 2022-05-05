package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Assign.RegisterDetails;

public class Dao {

	public static void input(RegisterDetails obj) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://hariharan-db.cdbky3odmgvg.ap-south-1.rds.amazonaws.com/tamil_db","hariharan", "Hari1008");
		String query = "insert INTO Banking (Name,age,Mobile_Number,Gender,Amount,Password) VALUES ('" + obj.getName()
				+ "','" + obj.getAge() + "','" + obj.getMobile_Number() + "','" + obj.getGender() + "','"
				+ obj.getAmount() + "','" + obj.getPassword() + "')";
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		statement.close();
		connection.close();
	}

	public static ResultSet displaycall(String query) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://hariharan-db.cdbky3odmgvg.ap-south-1.rds.amazonaws.com/tamil_db","hariharan", "Hari1008");
		Statement run = connection.createStatement();
		ResultSet get = run.executeQuery(query);
		// connection.close();
		return get;
	}

	public static int updateSql1(String query) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://hariharan-db.cdbky3odmgvg.ap-south-1.rds.amazonaws.com/tamil_db","hariharan", "Hari1008");
		Statement statement = connection.createStatement();
		int row = statement.executeUpdate(query);
		 
		return row;
		//connection.close();
		
	}

}
