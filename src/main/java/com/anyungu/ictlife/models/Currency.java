package com.anyungu.ictlife.models;

// class to represent the currency data model
public class Currency {

	// country field
	private String country;

	// currency field
	private String currency;

	// currency symbol field
	private String currencyISO;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrencyISO() {
		return currencyISO;
	}

	public void setCurrencyISO(String currencyISO) {
		this.currencyISO = currencyISO;
	}

}
