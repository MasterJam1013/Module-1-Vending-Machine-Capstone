package com.techelevator;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Map;

public class VendingMachineTest {

    @Test
    public void feedMoneyTest(){
        //Arrange
        VendingMachine sut = new VendingMachine();

        //Act
        sut.feedMoney(4);
        sut.feedMoney(5);
        BigDecimal result = sut.getCurrentBalance();
        BigDecimal expected = new BigDecimal(9);
        //Assert
        Assert.assertEquals(expected, result);
    }

    @Test
    public void makeInventoryTest(){
        //Arrange
        VendingMachine sut = new VendingMachine();
        VendingItem sut2 = new VendingItem();
        //Act
        sut.makeOpeningInventory();
        Map<String, VendingItem> testInventory = sut.getInventory();
        VendingItem testItem = testInventory.get("B1");
        String name =testItem.getItemName();
        //Assert
        Assert.assertEquals("Moonpie", name);
    }

    @Test
    public void testItemSoldNewInventory(){
        //Arrange
        VendingMachine sut = new VendingMachine();
        VendingItem sut2 = new VendingItem();
        //Act
        sut.makeOpeningInventory();
        sut.feedMoney(4);
        sut.purchaseItemFromVendingMachine();
//        VendingItem itemSold = sut.getInventory().get("B1");

        //Assert

    }

}
