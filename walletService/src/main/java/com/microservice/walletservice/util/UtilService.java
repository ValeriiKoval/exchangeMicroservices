package com.microservice.walletservice.util;

public class UtilService {

    private UtilService() {
    }

    public static long dollarsToCents(double dollars) {
        return (long) (dollars * 100);
    }

    public static double centsToDollars(long cents) {
        return (double) cents / 100;
    }
}
