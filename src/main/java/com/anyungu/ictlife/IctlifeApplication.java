package com.anyungu.ictlife;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;

import com.anyungu.ictlife.responses.CurrencyResponse;
import com.anyungu.ictlife.service.IctlifeApplicationService;
import com.anyungu.ictlife.service.IctlifeApplicationStockService;

@SpringBootApplication
public class IctlifeApplication implements CommandLineRunner {

	@Autowired
	private IctlifeApplicationService ictlifeApplicationService;

	@Autowired
	private IctlifeApplicationStockService ictlifeApplicationStockService;

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
			Integer fetchUserInputMenuOne = ictlifeApplicationService.fetchUserInputMenuOne();


			if (fetchUserInputMenuOne == 1) {
				ictlifeApplicationService.getAllSupportedCurrencies();
			}


			if (fetchUserInputMenuOne == 2) {
				ictlifeApplicationService.getAllSupportedLanguages();
			}

			if (fetchUserInputMenuOne == 3) {

				String currency = ictlifeApplicationService.fetchUserInputMenuCurrency();
				ictlifeApplicationService.getCurrencyFileFromServer(currency);
			}

			if (fetchUserInputMenuOne == 4) {

				String language = ictlifeApplicationService.fetchUserInputMenuLanguage();
				ictlifeApplicationService.getlanguageFileFromServer(language);
			}

			if (fetchUserInputMenuOne == 5) {

				HashMap<String,String> fetchUserInputMenuStock = ictlifeApplicationStockService.fetchUserInputMenuStock();
				ictlifeApplicationStockService.getStockPrice(fetchUserInputMenuStock.get("choiceOne"), fetchUserInputMenuStock.get("choiceTwo"));
			}

			if (fetchUserInputMenuOne == 6) {

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
