package com.techelevator;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class VendingItemTest {

    @Test
    public void vendingItemConstructor(){

        //Arrange
        BigDecimal price = new BigDecimal(1.01);
        VendingItem sut = new VendingItem("A1", "test", price, "CHIP");

        //Act
        String name = sut.getItemName();

        //Assert
        Assert.assertEquals("test", name);
    }

}
