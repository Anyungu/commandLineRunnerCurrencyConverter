package com.anyungu.ictlife.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.anyungu.ictlife.models.Currency;
import com.anyungu.ictlife.models.Language;
import com.anyungu.ictlife.responses.CurrencyResponse;
import com.anyungu.ictlife.responses.GeneralDataResponse;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//class to perform business and functional logic
@Service
public class IctlifeApplicationService {

	private Scanner scanner;

	// function to fetch the currency csv file from server
	public CurrencyResponse getCurrencyFileFromServer(String currency) {

		try {
			OkHttpClient client = new OkHttpClient();

			Request requestHttp = new Request.Builder().get().url(
					"https://focusmobile-interview-materials.s3.eu-west-3.amazonaws.com/Cheap.Stocks.Internationalization.Currencies.csv")
					.build();

			Call call = client.newCall(requestHttp);

			// http call to get file
			Response response = call.execute();

			String stringContentType = response.header("content-type").toString();

			// check if response is csv or not
			if (stringContentType.contentEquals("text/csv")) {

				return checkIfCurrecyIsApplicable(currency, response);

			} else {

				// response is not csv, most likely file was not found
				CurrencyResponse curResponse = new CurrencyResponse();

				curResponse.setCode(400);
				curResponse.setMessage("Oops, File containing currencies may be unavailable. Contact Admin");

				return curResponse;

			}

		} catch (Exception e) {

			// we dont know what might have gone wrong
			CurrencyResponse curResponse = new CurrencyResponse();

			curResponse.setCode(400);
			curResponse.setMessage(e.getMessage());

			return curResponse;

		}

	}

	// check if currency is applicable
	private CurrencyResponse checkIfCurrecyIsApplicable(String currency, Response response) {

		try {
			String currLine = "";
			StringTokenizer tokenizer;

			BufferedReader br = new BufferedReader(new InputStreamReader(response.body().byteStream()));

			while ((currLine = br.readLine()) != null) {
				tokenizer = new StringTokenizer(currLine, "\n");
				while (tokenizer.hasMoreElements()) {
//		                System.out.println(tokenizer.nextToken());

					List<String> stringList = Collections.list(new StringTokenizer(tokenizer.nextToken(), ",")).stream()
							.map(token -> (String) token).collect(Collectors.toList());

					// if currency exists, it's usable, inform the user
					if (currency.contentEquals(stringList.get(2))) {

						Currency cur = new Currency();
						cur.setCountry(stringList.get(0));
						cur.setCurrency(stringList.get(1));
						cur.setCurrencyISO(stringList.get(2));

						CurrencyResponse curResponse = new CurrencyResponse();

						curResponse.setCode(200);
						curResponse.setMessage("Currency is Applicable");
						curResponse.setCurrency(cur);

						return curResponse;

					}

				}
			}

			// currency does not exist
			CurrencyResponse curResponse = new CurrencyResponse();

			curResponse.setCode(400);
			curResponse.setMessage("Oops, Currency is invalid");

			return curResponse;
		} catch (Exception e) {

			// we dont know what might have gone wrong
			CurrencyResponse curResponse = new CurrencyResponse();

			curResponse.setCode(400);
			curResponse.setMessage(e.getMessage());

			return curResponse;
		}

	}


		// function to fetch the language csv file from server
		public GeneralDataResponse<?> getlanguageFileFromServer(String language) {

			try {
				OkHttpClient client = new OkHttpClient();
	
				Request requestHttp = new Request.Builder().get().url(
						"https://focusmobile-interview-materials.s3.eu-west-3.amazonaws.com/Cheap.Stocks.Internationalization.Languages.csv")
						.build();
	
				Call call = client.newCall(requestHttp);
	
				// http call to get file
				Response response = call.execute();
	
				String stringContentType = response.header("content-type").toString();
	
				// check if response is csv or not
				if (stringContentType.contentEquals("text/csv")) {
	
					return checkIfLanguageIsApplicable(language, response);
	
				} else {
	
					// response is not csv, most likely file was not found
					GeneralDataResponse<?> curResponse = new GeneralDataResponse<>();
					curResponse.setCode(400);
					curResponse.setMessage("Oops, File containing languages may be unavailable. Contact Admin");
	
					return curResponse;
	
				}
	
			} catch (Exception e) {
	
				// we dont know what might have gone wrong
				GeneralDataResponse<?> curResponse = new GeneralDataResponse<>();
	
				curResponse.setCode(400);
				curResponse.setMessage(e.getMessage());
	
				return curResponse;
	
			}
	
		}


		// check if language is applicable
		private GeneralDataResponse<?> checkIfLanguageIsApplicable(String language, Response response) {

			try {
				String currLine = "";
				StringTokenizer tokenizer;
	
				BufferedReader br = new BufferedReader(new InputStreamReader(response.body().byteStream()));
	
				while ((currLine = br.readLine()) != null) {
					tokenizer = new StringTokenizer(currLine, "\n");
					while (tokenizer.hasMoreElements()) {	         
	
						List<String> stringList = Collections.list(new StringTokenizer(tokenizer.nextToken(), ",")).stream()
								.map(token -> (String) token).collect(Collectors.toList());
	
						// if currency exists, it's usable, inform the user
						if (language.contentEquals(stringList.get(1))) {
	
							Language lan = new Language();
							lan.setLanguage(stringList.get(0));
							lan.setLanguageCode(stringList.get(1));
						
							GeneralDataResponse<?> curResponse = new GeneralDataResponse<>();
	
							curResponse.setCode(200);
							curResponse.setMessage("Currency is Applicable");
						
	
							return curResponse;
	
						}
	
					}
				}
	
				// language does not exist
				GeneralDataResponse<?> curResponse = new GeneralDataResponse<>();
	
				curResponse.setCode(400);
				curResponse.setMessage("Oops, Currency is invalid");
	
				return curResponse;
			} catch (Exception e) {
	
				// we dont know what might have gone wrong
				GeneralDataResponse<?> curResponse = new GeneralDataResponse<>();
	
				curResponse.setCode(400);
				curResponse.setMessage(e.getMessage());
	
				return curResponse;
			}
	
		}

//	First Display
//	Show welcome message
//	get user input
//	convert to upper case
	public Integer fetchUserInputMenuOne() {

		scanner = new Scanner(System.in);

		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("***Welcome to Cheap Stocks Inc ***");
		System.out.println();
		System.out.println("**********************************");
		System.out.println();
		System.out.println("Please Select What you want to do");
		System.out.println();
		System.out.println("1: List All Applicable Currencies");
		System.out.println("2: List All Applicable Languages");
		System.out.println("3: Check if Currency is applicable");
		System.out.println("4: Check if language is applicable");
		System.out.println("5: View available stock prices");
		System.out.println("6: Exit");
		

		Integer choiceOne = scanner.nextInt();

		return choiceOne;
	}


	//checkCurrecyStartMenu
	public String fetchUserInputMenuCurrency() {

		scanner = new Scanner(System.in);

		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("**********************************");
		System.out.println();
		System.out.println("Please type the code of the currency you want to confirm");
		System.out.println();
		System.out.println();
		

		String choiceOne = scanner.nextLine();

		return choiceOne;
	}


	//checkLanguageStartMenu
	public String fetchUserInputMenuLanguage() {

		scanner = new Scanner(System.in);

		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("**********************************");
		System.out.println();
		System.out.println("Please type the code of the languange you want to confirm");
		System.out.println();
		System.out.println();
		

		String choiceOne = scanner.nextLine();

		return choiceOne;
	}

	//ask user to try again
	public Boolean doYouWantToTryAgain() {

		scanner = new Scanner(System.in);

		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("**********************************");
		System.out.println();
		System.out.println("Do you want to try again?");
		System.out.println();
		System.out.println("press y for yes, press any other key to cancel");
		System.out.println();

		String name = scanner.next();

		if (name.contentEquals("y")) {
			return true;
		} else {
			return false;
		}
	}



	//listAllCurrencies
	public GeneralDataResponse<?> getAllSupportedCurrencies() {


		try {
			OkHttpClient client = new OkHttpClient();

			Request requestHttp = new Request.Builder().get().url(
					"https://focusmobile-interview-materials.s3.eu-west-3.amazonaws.com/Cheap.Stocks.Internationalization.Currencies.csv")
					.build();

			Call call = client.newCall(requestHttp);

			// http call to get file
			Response response = call.execute();

			String stringContentType = response.header("content-type").toString();

			List<Currency> curList = new ArrayList<>();


				// check if response is csv or not
				if (stringContentType.contentEquals("text/csv")) {

					System.out.println(response.body().toString());
				
					// String currLine = "";
					// StringTokenizer tokenizer;
		
					// BufferedReader br = new BufferedReader(new InputStreamReader(response.body().byteStream()));
		
					// while ((currLine = br.readLine()) != null) {
					// 	tokenizer = new StringTokenizer(currLine, "\n");
					// 	while (tokenizer.hasMoreElements()) {
		
					// 		List<String> stringList = Collections.list(new StringTokenizer(tokenizer.nextToken(), ",")).stream()
					// 				.map(token -> (String) token).collect(Collectors.toList());
		
					
					// 			Currency cur = new Currency();
					// 			cur.setCountry(stringList.get(0));
					// 			cur.setCurrency(stringList.get(1));
					// 			cur.setCurrencyISO(stringList.get(2));	

					// 			curList.add(cur);
							
		
					// 	}
					// }

					GeneralDataResponse<?> curResponse = new GeneralDataResponse<>();
	
					curResponse.setCode(200);
					curResponse.setMessage("Found and listed");
	
					return curResponse;
	
				} else {
	
					// response is not csv, most likely file was not found
					GeneralDataResponse<String> curResponse = new GeneralDataResponse<>();
	
					curResponse.setCode(400);
					curResponse.setMessage("Oops, File containing currencies may be unavailable. Contact Admin");
	
					return curResponse;
	
				}
		}
		catch(Exception e) {


			// we dont know what might have gone wrong
			GeneralDataResponse<String> curResponse = new GeneralDataResponse<>();

			curResponse.setCode(400);
			curResponse.setData(e.getMessage());

			return curResponse;

			

		}

	}



		//getAllLanguages
		public GeneralDataResponse<?> getAllSupportedLanguages() {


			try {
				OkHttpClient client = new OkHttpClient();
	
				Request requestHttp = new Request.Builder().get().url(
						"https://focusmobile-interview-materials.s3.eu-west-3.amazonaws.com/Cheap.Stocks.Internationalization.Languages.csv")
						.build();
	
				Call call = client.newCall(requestHttp);
	
				// http call to get file
				Response response = call.execute();
	
				String stringContentType = response.header("content-type").toString();

	
					// check if response is csv or not
					if (stringContentType.contentEquals("text/csv")) {
	
				
						System.out.println(response.body().toString());

						GeneralDataResponse<?> curResponse = new GeneralDataResponse<>();
	
						curResponse.setCode(200);
						curResponse.setMessage("Found and listed");
		
						return curResponse;
		
					} else {
		
						// response is not csv, most likely file was not found
						GeneralDataResponse<String> lanResponse = new GeneralDataResponse<>();
		
						lanResponse.setCode(400);
						lanResponse.setData("Oops, File containing currencies may be unavailable. Contact Admin");
		
						return lanResponse;
		
					}
			}
			catch(Exception e) {
	
	
				// we dont know what might have gone wrong
				GeneralDataResponse<String> lanResponse = new GeneralDataResponse<>();
	
				lanResponse.setCode(400);
				lanResponse.setData(e.getMessage());
	
				return lanResponse;
	
				
	
			}
	
		}

}
