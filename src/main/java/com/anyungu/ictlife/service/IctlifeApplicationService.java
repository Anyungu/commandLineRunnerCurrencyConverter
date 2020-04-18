package com.anyungu.ictlife.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.anyungu.ictlife.models.Currency;
import com.anyungu.ictlife.responses.CurrencyResponse;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//class to perform business and functional logic
@Service
public class IctlifeApplicationService {

	private Scanner scanner;

	// function to fetch the csv file from server
	public CurrencyResponse<?> getCurrencyFileFromServer(String currency) {

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
				CurrencyResponse<String> curResponse = new CurrencyResponse<>();

				curResponse.setCode(400);
				curResponse.setMessage("Oops, File containing currencies may be unavailable. Contact Admin");

				return curResponse;

			}

		} catch (Exception e) {

			// we dont know what might have gone wrong
			CurrencyResponse<String> curResponse = new CurrencyResponse<>();

			curResponse.setCode(400);
			curResponse.setMessage(e.getMessage());

			return curResponse;

		}

	}

	// check if currency is applicable
	private CurrencyResponse<?> checkIfCurrecyIsApplicable(String currency, Response response) {

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

						CurrencyResponse<Currency> curResponse = new CurrencyResponse<>();

						curResponse.setCode(200);
						curResponse.setMessage("Currency is Applicable");
						curResponse.setCurrency(cur);

						return curResponse;

					}

				}
			}

			// currency does not exist
			CurrencyResponse<String> curResponse = new CurrencyResponse<>();

			curResponse.setCode(400);
			curResponse.setMessage("Oops, Currency is invalid");

			return curResponse;
		} catch (Exception e) {

			// we dont know what might have gone wrong
			CurrencyResponse<String> curResponse = new CurrencyResponse<>();

			curResponse.setCode(400);
			curResponse.setMessage(e.getMessage());

			return curResponse;
		}

	}

//	First Display
//	Show welcome message
//	get user input
//	convert to upper case
	public String fetchUserInput() {

		scanner = new Scanner(System.in);

		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("***Welcome to Cheap Stocks Inc ***");
		System.out.println();
		System.out.println("**********************************");
		System.out.println();
		System.out.println("Please type the currency that you Intend to use then press enter");
		String name = scanner.next();

		return name.toUpperCase();
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

}
