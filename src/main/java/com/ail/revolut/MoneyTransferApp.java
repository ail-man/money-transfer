package com.ail.revolut;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;

import static com.example.jersey.Main.BASE_URI;

public class MoneyTransferApp {
	private static final Logger logger = LoggerFactory.getLogger(MoneyTransferApp.class);

	public static void main(String[] args) {
		try {
			initJersey();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private static void initJersey() throws IOException {
		final HttpServer server = startServer();
		logger.info(String.format(
				"Jersey app started with WADL available at %sapplication.wadl Hit enter to stop it...",
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
