package com.anyungu.ictlife.service;

import java.util.HashMap;
import java.util.Scanner;

import com.anyungu.ictlife.models.LanguageTranslation;
import com.anyungu.ictlife.models.StockPrice;
import com.anyungu.ictlife.responses.CurrencyResponse;
import com.anyungu.ictlife.responses.GeneralDataResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


@Service
public class IctlifeApplicationStockService {


    private Scanner scanner;


    @Autowired
    private IctlifeApplicationService ictlifeApplicationService;

    
    private HashMap <String, Double> companyStock = new HashMap<>();


    //Statically initialize Stock prices in usd for 3 companies
    //this data can alternatively be sourced from a datasource
    public IctlifeApplicationStockService () {
        this.companyStock.put("Cheap Stocks", 4.0);
        this.companyStock.put("Safaricom", 2.0);
        this.companyStock.put("Africa's Talking", 3.0);
    }
 

    //funtion to get stock price
    public GeneralDataResponse<?> getStockPrice(String languageCode, String currencyCode) {

      // if no language and no currency is selected use the defaults  
      if (StringUtils.isEmpty(languageCode) && StringUtils.isEmpty(currencyCode)) {

            companyStock.forEach((k, v) -> {
                System.out.println("The current price for "+ k +" is " + v);
            });

            GeneralDataResponse<String> generalDataResponse = new GeneralDataResponse<>();
            generalDataResponse.setCode(200);
            generalDataResponse.setData("Stock Prices Updated");

            return generalDataResponse;

      }

      //if user selected both language and currency
      if (!StringUtils.isEmpty(languageCode) && !StringUtils.isEmpty(currencyCode)) {

        CurrencyResponse currencyS = ictlifeApplicationService.getCurrencyFileFromServer(currencyCode);
        GeneralDataResponse<?> languageS = ictlifeApplicationService.getlanguageFileFromServer(languageCode);

        //unsupported language and currency
        if (currencyS.getCode() != 200 && languageS.getCode() != 200) {

            System.out.println("The currency & language you typed is not supported");


            GeneralDataResponse<String> generalDataResponse = new GeneralDataResponse<>();
            generalDataResponse.setCode(400);
            generalDataResponse.setData("The currency & language you typed is not supported");

            return generalDataResponse;
        }

        //unsuported language
        if (currencyS.getCode() == 200 && languageS.getCode() != 200) {

            System.out.println("The language you typed is not supported");


            GeneralDataResponse<String> generalDataResponse = new GeneralDataResponse<>();
            generalDataResponse.setCode(400);
            generalDataResponse.setData("The language you typed is not supported");

            return generalDataResponse;
        }
        

        //unsuported currency
        if (currencyS.getCode() != 200 && languageS.getCode() == 200) {

         String translated = translateText("The currency you typed is not supported", languageCode);
         System.out.println(translated);

            GeneralDataResponse<String> generalDataResponse = new GeneralDataResponse<>();
            generalDataResponse.setCode(400);
            generalDataResponse.setMessage(translated);

            return generalDataResponse;
        }
    }


        String translatedOne = translateText("The current price for", languageCode);

        String translatedTwo = translateText("is", languageCode);



        companyStock.forEach((k, v) -> {
            System.out.println(translatedOne + " "+ k + " "+ translatedTwo + " " + v);
        });

        GeneralDataResponse<String> generalDataResponse = new GeneralDataResponse<>();
        generalDataResponse.setCode(200);
        generalDataResponse.setData("Stock Prices Updated");

        return generalDataResponse;
      
      }

      



      private String translateText(String rawText, String languageCode) {


        try{
            OkHttpClient client = new OkHttpClient();


            HttpUrl url = HttpUrl.parse("https://translate.yandex.net/api/v1.5/tr.json/translate").newBuilder()
                .addQueryParameter("key", "trnsl.1.1.20200419T120021Z.4e958decb17d7f91.1c0cad562aa8c93a6129b39fe98073c0afd0366c")
                .addQueryParameter("text", rawText)       
                .addQueryParameter("lang", "en-"+languageCode)        
                .build();

            Request requestHttp = new Request.Builder().get().url(url) 
                .build();


				Call call = client.newCall(requestHttp);
	
				// http call to get file
                Response response = call.execute();
                

                ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                LanguageTranslation languageTranslation = objectMapper.readValue(response.body().string(), LanguageTranslation.class);  

                if (languageTranslation.getCode() == 200) {
                    return languageTranslation.getText();
                }

                return rawText;

                
        } catch (Exception e) {
            GeneralDataResponse<String> generalDataResponse = new GeneralDataResponse<>();
            generalDataResponse.setCode(400);
            generalDataResponse.setMessage(e.getMessage());

            return rawText;
        }
      
      }



    //getStockStartMenu
	public HashMap<String, String> fetchUserInputMenuStock() {


        HashMap<String, String> choices = new HashMap<>();

		scanner = new Scanner(System.in);

		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("**********************************");
		System.out.println();
		System.out.println("Please type the code of the languange you want to use. Press enter to use default");
		System.out.println();
		

        String choiceOne = scanner.nextLine();
        

        System.out.println("Please type the code of the currency you want to use. Press enter to use default");
		System.out.println();
      
        

        String choiceTwo = scanner.nextLine();

        choices.put("choiceOne", choiceOne);
        choices.put("choiceTwo", choiceTwo);

		return choices;
	}

    

}