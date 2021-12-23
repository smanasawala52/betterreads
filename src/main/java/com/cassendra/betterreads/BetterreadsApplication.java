package com.cassendra.betterreads;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;

@SpringBootApplication
@EnableConfigurationProperties(DataStaxAstraProperties.class)
public class BetterreadsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BetterreadsApplication.class, args);
	}

	public void testConnect() {
		try (CqlSession session = CqlSession.builder().withKeyspace("main")
				.withCloudSecureConnectBundle(
						Paths.get("secure-connect-betterreads.zip"))
				.withAuthCredentials("nicBhqGuHPkdilBfMAFFvsmg",
						"m4jEAXtvi,4Alu248yy,-BMd869dxn4qDtP_.sb-jm6.P0w7AxIYvAE7cq7c.Tbytxc,q21+-+YZ5iGkdQHE2-OF58S.hyRepvn9g0Y10r5KZTy8vQxw8bnwPZZeSkOE")
				.build()) {
			// Select the release_version from the system.local table:
			ResultSet rs = session
					.execute("SELECT * FROM main.chipotle_stores LIMIT 100000");
			List<Row> row = rs.all();
			// Print the results of the CQL query to the console:
			if (row != null) {

				row.parallelStream()
						.filter(p -> p.getString("state").startsWith("A"))
						.sorted((p1, p2) -> p1.getString("location")
								.compareTo(p2.getString("location")))
						.sorted((p1, p2) -> p1.getString("state")
								.compareTo(p2.getString("state")))
						.forEachOrdered(
								p -> System.out.println(p.getString("state")
										+ " | " + p.getString("location")
										+ " | " + p.getBigDecimal("latitude")
										+ " | " + p.getBigDecimal("longitude")
										+ " | " + p.getString("address")));
			} else {
				System.out.println("An error occurred.");
			}
		}
	}

	@Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(
			DataStaxAstraProperties astraProperties) {
		Path bundle = astraProperties.getSecureConnectBundle().toPath();
		return builder -> builder.withCloudSecureConnectBundle(bundle)
				.withCodecRegistry(null)
				.withAuthCredentials("nicBhqGuHPkdilBfMAFFvsmg",
						"m4jEAXtvi,4Alu248yy,-BMd869dxn4qDtP_.sb-jm6.P0w7AxIYvAE7cq7c.Tbytxc,q21+-+YZ5iGkdQHE2-OF58S.hyRepvn9g0Y10r5KZTy8vQxw8bnwPZZeSkOE");
	}

}
