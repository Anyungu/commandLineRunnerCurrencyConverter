package com.anyungu.ictlife.responses;

import com.anyungu.ictlife.models.Currency;

// Currency Response Model
public class CurrencyResponse {

	private Integer code;

	private String message;

	private Currency currency;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

}
