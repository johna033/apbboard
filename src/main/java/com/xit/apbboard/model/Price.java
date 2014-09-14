package com.xit.apbboard.model;

import java.io.Serializable;

/**
 * Created by
 *
 * @author homer
 * @since 12.09.14.
 */
public class Price implements Serializable {
    public int priceId;
    public int amountOfSymbols;
    public double price;
    public double priceInRUR;
    public String stringOffer;
    public boolean reward;
}
