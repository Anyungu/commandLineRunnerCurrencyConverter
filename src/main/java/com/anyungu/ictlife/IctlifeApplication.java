package com.anyungu.ictlife;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.anyungu.ictlife.responses.CurrencyResponse;
import com.anyungu.ictlife.service.IctlifeApplicationService;

@SpringBootApplication
public class IctlifeApplication implements CommandLineRunner {

	@Autowired
	private IctlifeApplicationService ictlifeApplicationService;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(IctlifeApplication.class);

		// disable spring banner
		app.setBannerMode(Mode.OFF);
		app.run(args);
	}

	@Override
	public void run(String... args) {

		while (true) {

			// get user input
			String fetchUserInput = ictlifeApplicationService.fetchUserInput();

			// get a response from the business logic
			CurrencyResponse<?> currencyFileFromServer = ictlifeApplicationService
					.getCurrencyFileFromServer(fetchUserInput);

			// if response code is 200
			if (currencyFileFromServer.getCode() == 200) {

				System.out.println("Currency is Applicable for " + currencyFileFromServer.getCurrency().getCountry());

			} else {
				System.out.println(currencyFileFromServer.getMessage());
			}

			// do you want to start process again?
			Boolean doYouWantToTryAgain = ictlifeApplicationService.doYouWantToTryAgain();

			if (doYouWantToTryAgain == false) {

				System.out.println();
				System.out.println("**********************************");
				System.out.println();
				System.out.println("Adios");
				System.out.println("Thank you for using Cheap Stocks");
				System.out.println();
				System.out.println("**********************************");
				System.out.println();

				return;
			}

		}

	}

}
