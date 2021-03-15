package com.techelevator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;

public class VendingMachine {

    //set instance variables
    private Map<String, VendingItem> machineInventory = new TreeMap<>(); //we used TreeMap so that the inventory is sorted by Key
    //keeps track of amount of VendingItems sold; used to generate Sales Report
    private Map<VendingItem, BigDecimal> productsSold = new HashMap<>();
    private BigDecimal currentBalance = new BigDecimal("0.00");

    //Getter

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public Map getInventory() {
        return machineInventory;
    }

    //Setter

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    //Method to make opening inventory
    public void makeOpeningInventory() {
        //Open/read inventory file
        Path myPath = Path.of("inventory.txt");
        try (Scanner fileScanner = new Scanner(myPath)) {
            while (fileScanner.hasNextLine()) {
                String inventoryFileLine = fileScanner.nextLine();
                //parse inventory file line, get pieces of info for VendingItem constructor in right format.
                String[] fileLineArray = inventoryFileLine.split("\\|");
                String vendingKey = fileLineArray[0];
                String itemName = fileLineArray[1];
                String itemPriceString = fileLineArray[2];
                BigDecimal itemPrice = new BigDecimal(itemPriceString);
                String itemType = fileLineArray[3];
                ItemType test = ItemType.valueOf(itemType.toUpperCase());
                // Run VendingItem constructor with pieces of info from inventory file line.
                VendingItem inventoryItem = new VendingItem(vendingKey, itemName, itemPrice, itemType);
                //Set item amount at 5
                inventoryItem.setItemAmount(5);
                //put item into inventory map
                machineInventory.put(vendingKey, inventoryItem);
            }
        } catch (IOException e) {
            System.out.println("Can't read from that file!");
        }
        //Makes fresh product sold map for new day
        productsSold.clear();
    }

    //Method to list all items.
    public void listAllItems() {
        //for-each loop to go through each key-value pair in the machineInventory map
        for (Map.Entry<String, VendingItem> entry : machineInventory.entrySet()) {
            //if the VendingItem (the value of the entry) has an Item Amount greater than 0, print out
            //item name, the item's vending code, and the item's price
            if (entry.getValue().getItemAmount() > 0) {
                System.out.println(entry.getValue().getItemName() + " " + entry.getKey() + " " + entry.getValue().getItemPrice());
                //if the Vending Item has an inventory of zero, print out its vending code and "SOLD OUT"
            } else {
                System.out.println(entry.getKey() + " SOLD OUT");
            }
        }
    }

    public void printSalesReport() {
        //move through each entry in product sold map to grab item and number sold.
        BigDecimal salesTotal = BigDecimal.ZERO;
        for (Map.Entry<VendingItem, BigDecimal> entry : productsSold.entrySet()) {
            BigDecimal numberSold = entry.getValue();
            VendingItem itemSold = entry.getKey();
            String itemName = itemSold.getItemName();
            BigDecimal itemPrice = itemSold.getItemPrice();
            BigDecimal itemTotalSales = itemPrice.multiply(numberSold);
            salesTotal = salesTotal.add(itemTotalSales);
            System.out.println(itemName + " " + itemTotalSales);
        }
        System.out.println("** TOTAL SALES ** $" + salesTotal);
    }

    //Method to feed money
    public void feedMoney(int dollarsToFeed) {
        BigDecimal moneyToAddToBalance = new BigDecimal(dollarsToFeed);
        currentBalance = currentBalance.add(moneyToAddToBalance);
        //code to write to audit file with FEED MONEY goes below here.
        LocalDateTime timeStamp = LocalDateTime.now();

        try (FileOutputStream stream = new FileOutputStream("audit.txt", true);
             PrintWriter auditWriter = new PrintWriter(stream)){
            auditWriter.println(timeStamp + " FEED MONEY " + dollarsToFeed + " " + currentBalance);
        } catch (IOException e) {
            System.out.println("Couldn't append to file.");
        }


    }

    //Method to purchase item from vending machine
    public void purchaseItemFromVendingMachine() {

        //Take in user input
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter item number for purchase: ");
        String vendingKey = input.nextLine();
        VendingItem itemToBeSold = machineInventory.get(vendingKey);
        String itemName = itemToBeSold.getItemName();
        BigDecimal itemToBeSoldPrice = itemToBeSold.getItemPrice();
        int itemToBeSoldInventory = itemToBeSold.getItemAmount();

        //If statement if balance too low or SOLD OUT inform user. If balance high enough complete method
        if(itemToBeSoldInventory == 0){
            System.out.println("Sold Out Item!");
        } else if (currentBalance.compareTo(itemToBeSoldPrice) == -1){
            System.out.println("Your Balance is " + currentBalance + "and the item price is: " + itemToBeSoldPrice
                    + ". YOU NEED TO ADD MORE $$");
        } else {
            //Update current balance
            currentBalance = currentBalance.subtract(itemToBeSoldPrice);
            //Update inventory list
            itemToBeSold.setItemAmount(itemToBeSoldInventory - 1);
            //Dispense item printing item type message
            switch (itemToBeSold.getItemType()){
                case "Chip":
                    System.out.println("Crunch Crunch, Yum!");
                    break;
                case "Candy":
                    System.out.println("Munch Munch, Yum!");
                    break;
                case "Drink":
                    System.out.println("Glug Glug, Yum!");
                    break;
                case "Gum":
                    System.out.println("Chew Chew, Yum!");
                    break;
                default:
                    System.out.println("Enjoy your purchase");
                    break;
            }
            //Add to product sold map
            if(productsSold.containsKey(itemToBeSold)){
                productsSold.put(itemToBeSold, productsSold.get(itemToBeSold).add(BigDecimal.ONE));
            } else {
                productsSold.put(itemToBeSold, BigDecimal.ONE);
            }

            //Write to audit file
            LocalDateTime timeStamp = LocalDateTime.now();
            try (FileOutputStream stream = new FileOutputStream("audit.txt", true);
                 PrintWriter auditWriter = new PrintWriter(stream)){
                auditWriter.println(timeStamp + " PURCHASE: " + itemName + " " + vendingKey + " " + itemToBeSoldPrice
                        + " " + itemToBeSold.getItemAmount() + " items left");
            } catch (IOException e) {
                System.out.println("Couldn't append to file.");
            }
        }
        //Return user to purchase menu

    }

    //print audit file method
    public void printAuditFile() {
        Path source = Path.of("audit.txt");
        try (Scanner sourceScanner = new Scanner(source)) {
            while (sourceScanner.hasNextLine()) {
                String line = sourceScanner.nextLine(); //read a line
                System.out.println(line);
            }
        } catch(IOException e){
                System.out.println("Couldn't read from input file.");
            }
        }

        //make change method
        public void makeChange(){
        BigDecimal currentUserBalance = getCurrentBalance();
        BigDecimal quarterValue = new BigDecimal("0.25");
        BigDecimal dimeValue = new BigDecimal("0.10");
        BigDecimal nickelValue = new BigDecimal("0.05");
        BigDecimal divideByQuarter = currentUserBalance.divideToIntegralValue(quarterValue);
       currentUserBalance = currentUserBalance.subtract(divideByQuarter.multiply(quarterValue));
       BigDecimal divideByDime = currentUserBalance.divideToIntegralValue(dimeValue);
       currentUserBalance = currentUserBalance.subtract(divideByDime.multiply(dimeValue));
       BigDecimal divideByNickel = currentUserBalance.divideToIntegralValue(nickelValue);
       currentUserBalance = currentUserBalance.subtract(divideByNickel.multiply(nickelValue));
       currentBalance = currentUserBalance;

       System.out.println("You received " + divideByQuarter + " quarters and " + divideByDime + " dimes and " +
               divideByNickel + " nickels in change.");
        //Write to audit file
            LocalDateTime timeStamp = LocalDateTime.now();
            try (FileOutputStream stream = new FileOutputStream("audit.txt", true);
                 PrintWriter auditWriter = new PrintWriter(stream)){
                auditWriter.println(timeStamp + " GIVE CHANGE " + divideByQuarter + " quarters and " + divideByDime + " dimes and " +
                        divideByNickel + " nickels given in change. REMAINING BALANCE: " + getCurrentBalance());
            } catch (IOException e) {
                System.out.println("Couldn't append to file.");
            }

        }





    }


