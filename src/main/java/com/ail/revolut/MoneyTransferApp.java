package com.ail.revolut;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.sql.*;

import static com.example.jersey.Main.BASE_URI;

public class MoneyTransferApp {
	private static final Logger logger = LoggerFactory.getLogger(MoneyTransferApp.class);

	static {
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void main(String[] args) {
		try {
			initDb();
			startJersey();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private static void initDb() throws ClassNotFoundException, SQLException {
//			Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
		Connection con = DriverManager.getConnection("jdbc:h2:./db/test", "sa", "");
		Statement stmt = con.createStatement();
		stmt.executeUpdate("DROP TABLE table1");
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

//		repeatSelect();
	}

	private static void repeatSelect() throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM table1");
		while (rs.next()) {
			String name = rs.getString("user");
			logger.debug(name);
		}
		stmt.close();
		con.close();
	}

	private static void startJersey() throws IOException {
		final HttpServer server = startServer();
		System.out.println(String.format(
				"Jersey app started with WADL available at %sapplication.wadl" +
						"\nHit enter to stop it...",
				BASE_URI)
		);
		System.in.read();
		server.shutdown();
	}

	private static HttpServer startServer() {
		final ResourceConfig rc = new ResourceConfig().packages("ail.revolut");
		return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
	}


}
