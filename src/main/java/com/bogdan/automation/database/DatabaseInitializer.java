package com.bogdan.automation.database;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;

public final class DatabaseInitializer {

	private DatabaseInitializer() {
	}

	public static void initialize() {

		try (Connection connection = DatabaseManager.getConnection();
				Statement statement = connection.createStatement();
				InputStream inputStream = DatabaseInitializer.class.getClassLoader()
						.getResourceAsStream("schema.sql")) {

			if (inputStream == null) {
				throw new RuntimeException("schema.sql file was not found");
			}

			String schema = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines()
					.collect(Collectors.joining("\n"));

			statement.execute(schema);

		} catch (Exception exception) {
			throw new RuntimeException("Failed to initialize database", exception);
		}
	}
}