package com.ail.revolut;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MoneyTransferApp {
	private static final Logger logger = LoggerFactory.getLogger(MoneyTransferApp.class);

	public static void main(String[] args) {
		startDatabase();
//		startJersey();
	}

	private static void startDatabase() {
		try {
			Class.forName("org.h2.Driver");
//			Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
			Connection con = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
			Statement stmt = con.createStatement();
//			stmt.executeUpdate("DROP TABLE table1");
			stmt.executeUpdate("CREATE TABLE table1 (user VARCHAR(50))");
			stmt.executeUpdate("INSERT INTO table1 (user) VALUES ('User1')");
			stmt.executeUpdate("INSERT INTO table1 (user) VALUES ('User2')");

			ResultSet rs = stmt.executeQuery("SELECT * FROM table1");
			while (rs.next()) {
				String name = rs.getString("user");
				logger.debug(name);
			}
			stmt.close();
			con.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
