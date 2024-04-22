package lab10.processing_transactions;

import java.util.*;
import java.util.stream.Collectors;

class Transaction {
    int value;
    String currency;
    int year;

    Transaction(String currency, int value, int year) {
        this.value = value;
        this.currency = currency;
        this.year = year;
    }

    int getValue() {
        return value;
    }
    String getCurrency() {
        return currency;
    }
    int getYear() {
        return year;
    }
    public String toString() {
        return currency + ":" + value + ":" + year;
    }

    public static void main(String[] args) {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("USD", 1000, 2019));
        transactions.add(new Transaction("EUR", 1200, 2018));
        transactions.add(new Transaction("EUR", 140, 2018));
        transactions.add(new Transaction("EUR", 1440, 2018));
        transactions.add(new Transaction("USD", 1500, 2017));
        transactions.add(new Transaction("EUR", 1300, 2016));
        transactions.add(new Transaction("USD", 1100, 2015));
        transactions.add(new Transaction("EUR", 1400, 2014));
        transactions.add(new Transaction("USD", 1600, 2013));
        transactions.add(new Transaction("EUR", 23700, 2012));
        transactions.add(new Transaction("USD", 1800, 2011));
        transactions.add(new Transaction("EUR", 1900, 2010));
        transactions.add(new Transaction("USD", 2000, 2009));

        System.out.println("Highest Value: " + transactions.stream()
                .map(Transaction::getValue)
                .max(Integer::compare).get());

        System.out.println("Year of Highest Value Transaction: " + transactions.stream()
                .max(Comparator.comparingInt(Transaction::getValue)).
                map(Transaction::getYear).get());

        System.out.println("Year of Most Total Transactions: " + transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getYear, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).get());

        System.out.println("Number of Distinct Years: " + transactions.stream()
                .map(Transaction::getYear)
                .distinct()
                .count());

        System.out.println("Mapping of Year to Largest Transaction Currency: " + transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getYear,
                        Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingInt(Transaction::getValue)),
                                transaction -> transaction.get().getCurrency()))));
    }
}


