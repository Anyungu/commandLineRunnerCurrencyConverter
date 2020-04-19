package com.anyungu.ictlife.models;

import java.util.HashMap;

public class StockPrice {

    private HashMap<String, Double> companyStock = new HashMap<>();

    public HashMap<String, Double> getCompanyStock() {
        return companyStock;
    }

    public void setCompanyStock(HashMap<String, Double> companyStock) {
        this.companyStock = companyStock;
    }


}