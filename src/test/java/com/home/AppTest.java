package com.home;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import dao.MergePrices;
import org.junit.Test;
import model.Price;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest {


    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    private Date parseDateToDateFormat(String date){

        Date convertDate = null;

        try {

            convertDate = dateFormat.parse(date);

        }
        catch (ParseException ex){

        }

        return convertDate;

    }

    @Test
    public void testMergePriceFromTask(){

        List<Price> currentPrices = new ArrayList<>();
        List<Price> newPrices = new ArrayList<>();
        List<Price> expectedPrices = new ArrayList<>();


        // old Prices
        Price price1 = new Price(1, "122856", 1, 1, parseDateToDateFormat("01.01.2013 00:00:00"), parseDateToDateFormat("31.01.2013 00:00:00"), 11000);
        currentPrices.add(price1);

        Price price2 = new Price(1, "122856", 2, 1, parseDateToDateFormat("10.01.2013 00:00:00"), parseDateToDateFormat("20.01.2013 00:00:00"), 99000);
        currentPrices.add(price2);

        Price price3 = new Price(1, "6654", 1, 2, parseDateToDateFormat("01.01.2013 00:00:00"), parseDateToDateFormat("31.01.2013 00:00:00"), 5000);
        currentPrices.add(price3);

        // new Prices

        Price price4 = new Price(1, "122856", 1, 1, parseDateToDateFormat("20.01.2013 00:00:00"), parseDateToDateFormat("20.02.2013 00:00:00"), 11000);
        newPrices.add(price4);

        Price price5 = new Price(1, "122856", 2, 1, parseDateToDateFormat("15.01.2013 00:00:00"), parseDateToDateFormat("25.01.2013 00:00:00"), 92000);
        newPrices.add(price5);

        Price price6 = new Price(1, "6654", 1, 2, parseDateToDateFormat("12.01.2013 00:00:00"), parseDateToDateFormat("13.01.2013 00:00:00"), 4000);
        newPrices.add(price6);


        //expectedPrices

        Price price7 = new Price(1, "122856", 1, 1, parseDateToDateFormat("01.01.2013 00:00:00"), parseDateToDateFormat("20.02.2013 00:00:00"), 11000);
        expectedPrices.add(price7);

        Price price8 = new Price(1, "122856", 2, 1, parseDateToDateFormat("10.01.2013 00:00:00"), parseDateToDateFormat("15.01.2013 00:00:00"), 99000);
        expectedPrices.add(price8);

        Price price9 = new Price(1, "122856", 2, 1, parseDateToDateFormat("15.01.2013 00:00:00"), parseDateToDateFormat("25.01.2013 00:00:00"), 92000);
        expectedPrices.add(price9);

        Price price10 = new Price(1, "6654", 1, 2, parseDateToDateFormat("01.01.2013 00:00:00"), parseDateToDateFormat("12.01.2013 00:00:00"), 5000);
        expectedPrices.add(price10);

        Price price11 = new Price(1, "6654", 1, 2, parseDateToDateFormat("12.01.2013 00:00:00"), parseDateToDateFormat("13.01.2013 00:00:00"), 4000);
        expectedPrices.add(price11);

        Price price12 = new Price(1, "6654", 1, 2, parseDateToDateFormat("13.01.2013 00:00:00"), parseDateToDateFormat("31.01.2013 00:00:00"), 5000);
        expectedPrices.add(price12);



        MergePrices mergePrices = new MergePrices();
        List<Price> updatePrices = mergePrices.updatePrices(currentPrices, newPrices);
        assertEquals(expectedPrices.size(), updatePrices.size());

        for (Price price : expectedPrices) {
            assertTrue(updatePrices.contains(price));
        }

    }


    //oldPrice 01.01_11000_20.01
    //newPrice 01.01_13000_25.01
    //expected 01.01_13000_25.01

    @Test
    public void newPriceOverlapOldPrice() {

        List<Price> currentPrices = new ArrayList<>();
        List<Price> newPrices = new ArrayList<>();
        List<Price> expectedPrices = new ArrayList<>();


        // old Prices
        Price price1 = new Price(1, "122856", 1, 1, parseDateToDateFormat("01.01.2013 00:00:00"), parseDateToDateFormat("20.01.2013 00:00:00"), 11000);
        currentPrices.add(price1);

        // new Prices
        Price price2 = new Price(1, "122856", 1, 1, parseDateToDateFormat("01.01.2013 00:00:00"), parseDateToDateFormat("25.01.2013 00:00:00"), 13000);
        newPrices.add(price2);

        //expectedPrices

        Price price3 = new Price(1, "122856", 1, 1, parseDateToDateFormat("01.01.2013 00:00:00"), parseDateToDateFormat("25.01.2013 00:00:00"), 13000);
        expectedPrices.add(price3);

        MergePrices mergePrices = new MergePrices();
        List<Price> updatePrices = mergePrices.updatePrices(currentPrices, newPrices);
        assertEquals(expectedPrices.size(), updatePrices.size());

        for (Price price : expectedPrices) {
            assertTrue(updatePrices.contains(price));
        }

    }

    //oldPrices 01.01_____11000______31.01
    //newPrices     05.01_15000_20.01
    //expected 01.05_11000_05.01 && 05.01_15000_20.01 && 20.01_11000_31.01

    @Test
    public void innerPriceMerge(){

        List<Price> currentPrices = new ArrayList<>();
        List<Price> newPrices = new ArrayList<>();
        List<Price> expectedPrices = new ArrayList<>();

        // old Prices
        Price price1 = new Price(1, "122856", 1,1,parseDateToDateFormat("01.01.2013 00:00:00"), parseDateToDateFormat("31.01.2013 00:00:00"), 11000);
        currentPrices.add(price1);

        // new Prices
        Price price2 = new Price(1, "122856", 1,1,parseDateToDateFormat("05.01.2013 00:00:00"), parseDateToDateFormat("20.01.2013 00:00:00"), 15000);
        newPrices.add(price2);

        //expectedPrices
        Price price3 = new Price(1, "122856", 1,1,parseDateToDateFormat("01.01.2013 00:00:00"), parseDateToDateFormat("05.01.2013 00:00:00"), 11000);
        expectedPrices.add(price3);

        Price price4 = new Price(1, "122856", 1,1,parseDateToDateFormat("05.01.2013 00:00:00"), parseDateToDateFormat("20.01.2013 00:00:00"), 15000);
        expectedPrices.add(price4);

        Price price5 = new Price(1, "122856", 1,1,parseDateToDateFormat("20.01.2013 00:00:00"), parseDateToDateFormat("31.01.2013 00:00:00"), 15000);
        expectedPrices.add(price5);

        MergePrices mergePrices = new MergePrices();
        List<Price> updatePrices = mergePrices.updatePrices(currentPrices, newPrices);
        assertEquals(expectedPrices.size(), updatePrices.size());

        for(Price price : expectedPrices){
            assertTrue(updatePrices.contains(price));
        }

    }

    //oldPrices 01.01_11000_20.01
    //newPrices         18.01_15000_25.01
    //expected 01.01_11000_18.01 && 18.01_15000_25.01

    @Test
    public void newPriceAfter(){


        List<Price> currentPrices = new ArrayList<>();
        List<Price> newPrices = new ArrayList<>();
        List<Price> expectedPrices = new ArrayList<>();


        // old Prices
        Price price1 = new Price(1, "122856", 1,1,parseDateToDateFormat("01.01.2013 00:00:00"), parseDateToDateFormat("20.01.2013 00:00:00"), 11000);
        currentPrices.add(price1);

        // new Prices
        Price price2 = new Price(1, "122856", 1,1,parseDateToDateFormat( "18.01.2013 00:00:00"), parseDateToDateFormat("25.01.2013 00:00:00"), 15000);
        newPrices.add(price2);

        //expectedPrices
        Price price3 = new Price(1, "122856", 1,1,parseDateToDateFormat("01.01.2013 00:00:00"), parseDateToDateFormat("18.01.2013 00:00:00"), 11000);
        expectedPrices.add(price3);

        Price price4 = new Price(1, "122856", 1,1,parseDateToDateFormat("18.01.2013 00:00:00"), parseDateToDateFormat("25.01.2013 00:00:00"), 15000);
        expectedPrices.add(price4);


        MergePrices mergePrices = new MergePrices();
        List<Price> updatePrices = mergePrices.updatePrices(currentPrices, newPrices);
        assertEquals(expectedPrices.size(), updatePrices.size());

        for(Price price : expectedPrices){
            assertTrue(updatePrices.contains(price));
        }




    }

    //oldPrices 01.01_11000_20.01 && 20.01_13000_30.01
    //newPrices           18.01__15000_25.01

    //expectedPrices    01.01_11000_18.01 && 18.01_15000_25.01 && 25.01_13000_30.01

    @Test
    public void newPricesBefore(){

        List<Price> currentPrices = new ArrayList<>();
        List<Price> newPrices = new ArrayList<>();
        List<Price> expectedPrices = new ArrayList<>();

        // old Prices
        Price price1 = new Price(1, "122856", 1,1,parseDateToDateFormat("01.01.2013 00:00:00"), parseDateToDateFormat("20.01.2013 00:00:00"), 11000);
        currentPrices.add(price1);

        Price price2 = new Price(1, "122856", 1,1,parseDateToDateFormat("20.01.2013 00:00:00"), parseDateToDateFormat("30.01.2013 00:00:00"), 13000);
        currentPrices.add(price2);

        // new Prices
        Price price3 = new Price(1, "122856", 1,1,parseDateToDateFormat("18.01.2013 00:00:00"), parseDateToDateFormat("25.01.2013 00:00:00"), 15000);
        newPrices.add(price3);


        //expected
        Price price4 = new Price(1, "122856", 1,1,parseDateToDateFormat("01.01.2013 00:00:00"), parseDateToDateFormat("18.01.2013 00:00:00"), 11000);
        expectedPrices.add(price4);

        Price price5 = new Price(1, "122856", 1,1,parseDateToDateFormat("18.01.2013 00:00:00"), parseDateToDateFormat("25.01.2013 00:00:00"), 15000);
        expectedPrices.add(price5);

        Price price6 = new Price(1, "122856", 1,1,parseDateToDateFormat("25.01.2013 00:00:00"), parseDateToDateFormat("30.01.2013 00:00:00"), 13000);
        expectedPrices.add(price6);

        MergePrices mergePrices = new MergePrices();
        List<Price> updatePrices = mergePrices.updatePrices(currentPrices, newPrices);
        assertEquals(expectedPrices.size(), updatePrices.size());

        for(Price price : expectedPrices){
            assertTrue(updatePrices.contains(price));
        }

    }


    //oldPrice  01.01__11000__20.01 && 20.01__13000__30.01
    // newPrice         10.01____12000______25.01 && 29.01__20000_05.02

    //expectedPrices    01.01__11000_10.01 && 10.01_12000_25.01 && 25.01_13000_29.01 && 29.01_20000_05.02

    @Test
    public void newTwoIntervalPrice(){

        List<Price> currentPrices = new ArrayList<>();
        List<Price> newPrices = new ArrayList<>();
        List<Price> expectedPrices = new ArrayList<>();


        // old Prices
        Price price1 = new Price(1, "122856", 1,1,parseDateToDateFormat("01.01.2013 00:00:00"), parseDateToDateFormat("20.01.2013 00:00:00"), 11000);
        currentPrices.add(price1);

        Price price2 = new Price(1, "122856", 1,1,parseDateToDateFormat("20.01.2013 00:00:00"), parseDateToDateFormat("30.01.2013 00:00:00"), 13000);
        currentPrices.add(price2);

        // new Prices
        Price price3 = new Price(1, "122856", 1,1,parseDateToDateFormat("10.01.2013 00:00:00"), parseDateToDateFormat("25.01.2013 00:00:00"), 12000);
        newPrices.add(price3);

        Price price4 = new Price(1, "122856", 1,1,parseDateToDateFormat("29.01.2013 00:00:00"), parseDateToDateFormat("05.02.2013 00:00:00"), 20000);
        newPrices.add(price4);

        //expected

        Price price5 = new Price(1, "122856", 1,1,parseDateToDateFormat("01.01.2013 00:00:00"), parseDateToDateFormat("10.01.2013 00:00:00"), 11000);
        expectedPrices.add(price5);

        Price price6 = new Price(1, "122856", 1,1,parseDateToDateFormat("10.01.2013 00:00:00"), parseDateToDateFormat("25.01.2013 00:00:00"), 12000);
        expectedPrices.add(price6);

        Price price7 = new Price(1, "122856", 1,1,parseDateToDateFormat("25.01.2013 00:00:00"), parseDateToDateFormat("29.01.2013 00:00:00"), 13000);
        expectedPrices.add(price7);

        Price price8 = new Price(1, "122856", 1,1,parseDateToDateFormat("29.01.2013 00:00:00"), parseDateToDateFormat("05.02.2013 00:00:00"), 20000);
        expectedPrices.add(price8);


        MergePrices mergePrices = new MergePrices();
        List<Price> updatePrices = mergePrices.updatePrices(currentPrices, newPrices);
        assertEquals(expectedPrices.size(), updatePrices.size());

        for(Price price : expectedPrices){
            assertTrue(updatePrices.contains(price));
        }


    }

    //oldPrices 01.01_8000_15_01 && 15.01_8700_25.01 && 25.01_9000_30.01
    // newPrices    05.01____8000____17.01 && 17.01___8500___27.01

    //expected 01.01_8000_17.01 && 17.01_8500_27.01 && 27.01_9000_30.01

    @Test
    public void intoSamePrices(){

        List<Price> currentPrices = new ArrayList<>();
        List<Price> newPrices = new ArrayList<>();
        List<Price> expectedPrices = new ArrayList<>();

        // old Prices
        Price price1 = new Price(1, "122856", 1,1,parseDateToDateFormat("01.01.2013 00:00:00"), parseDateToDateFormat("15.01.2013 00:00:00"), 8000);
        currentPrices.add(price1);

        Price price2 = new Price(1, "122856", 1,1,parseDateToDateFormat("15.01.2013 00:00:00"), parseDateToDateFormat("25.01.2013 00:00:00"), 8700);
        currentPrices.add(price2);

        Price price3 = new Price(1, "122856", 1,1,parseDateToDateFormat("25.01.2013 00:00:00"), parseDateToDateFormat("30.01.2013 00:00:00"), 9000);
        currentPrices.add(price3);

        // new Prices
        Price price4 = new Price(1, "122856", 1,1,parseDateToDateFormat("05.01.2013 00:00:00"), parseDateToDateFormat( "17.01.2013 00:00:00"), 8000);
        newPrices.add(price4);

        Price price5 = new Price(1, "122856", 1,1,parseDateToDateFormat("17.01.2013 00:00:00"), parseDateToDateFormat( "27.01.2013 00:00:00"), 8500);
        newPrices.add(price5);

        //expected
        Price price6 = new Price(1, "122856", 1,1,parseDateToDateFormat("01.01.2013 00:00:00"), parseDateToDateFormat("17.01.2013 00:00:00"), 8000);
        expectedPrices.add(price6);

        Price price7 = new Price(1, "122856", 1,1,parseDateToDateFormat("17.01.2013 00:00:00"), parseDateToDateFormat("27.01.2013 00:00:00"), 8500);
        expectedPrices.add(price7);


        Price price8 = new Price(1, "122856", 1,1,parseDateToDateFormat("27.01.2013 00:00:00"), parseDateToDateFormat("30.01.2013 00:00:00"), 9000);
        expectedPrices.add(price8);


        MergePrices mergePrices = new MergePrices();
        List<Price> updatePrices = mergePrices.updatePrices(currentPrices, newPrices);
        assertEquals(expectedPrices.size(), updatePrices.size());

        for(Price price : expectedPrices){
            assertTrue(updatePrices.contains(price));
        }

    }


}
