package com.foodordering;

import com.foodordering.restaurantsdata.RestaurantsData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class FoodOrderingApplication {

	private static final SpringApplication app = new SpringApplication(FoodOrderingApplication.class);

	private static String port;

	private static final RestaurantsData restaurantsData = new RestaurantsData();

	public static void main(String[] args) {
		app.setDefaultProperties(Collections.singletonMap("server.port", port));

		app.run();
	}

	public static RestaurantsData getRestaurantsData() {
		return restaurantsData;
	}

	static {

			File config = new File("configFO.txt");

			try {

				Scanner scanner = new Scanner(config);

				port = scanner.nextLine();

				if (!port.matches("^\\d{4,5}$")) throw new NoSuchElementException();

			} catch (FileNotFoundException e) {
				parsingError(1);
			} catch (NoSuchElementException e) {
				parsingError(2);
			}

	}

	private static void parsingError(int intCase) {

		switch (intCase) {

			case 1:
				System.out.println("\"configFO.txt\" file have to be in the same directory as jar file or projects");
				break;

			case 2:
				System.out.println("The config file has to contain only free port to be reserved for this server");
				break;

		}

		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.exit(1);

	}

}
