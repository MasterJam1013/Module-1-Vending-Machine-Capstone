package com.techelevator;

import java.math.BigDecimal;

public class VendingItem {
    //set instance variables
    private String vendingKey;
    private String itemName;
    private BigDecimal itemPrice;
    private int itemAmount;
    private String itemType;


    //Vending item constructor
    public VendingItem() {
    }


    public VendingItem(String vendingKey, String itemName, BigDecimal itemPrice, String itemType){

        this.vendingKey = vendingKey;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemType = itemType;
    }

    //Getters


    public String getVendingKey() {
        return vendingKey;
    }

    public String getItemName() {
        return itemName;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public int getItemAmount() {
        return itemAmount;
    }

    public String getItemType() {
        return itemType;
    }

    //Setters


    public void setItemAmount(int itemAmount) {
        this.itemAmount = itemAmount;
    }



}
