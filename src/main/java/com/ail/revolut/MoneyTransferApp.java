package com.ail.revolut;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class MoneyTransferApp {
	public static final String BASE_URI = "http://localhost:8080/revolut/api/";

	private static final String APP_PACKAGE = "com.ail";
	private static final Logger logger = LoggerFactory.getLogger(MoneyTransferApp.class);

	public static void main(String[] args) {
		try {
			final HttpServer server = startServer();
			logger.info(String.format(
					"Jersey app started with WADL available at %sapplication.wadl Hit enter to stop it...",
					BASE_URI)
			);
			System.in.read();
			server.shutdown();
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static HttpServer startServer() {
		final ResourceConfig rc = new ResourceConfig().packages(APP_PACKAGE);
		return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
	}

}
