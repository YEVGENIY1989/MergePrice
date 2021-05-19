package dao;

import model.Price;

import java.util.*;

public class MergePrices {


    public List<Price> updatePrices(List<Price> currentPrice, List<Price> newPrice){

        List<Price> newUpdatesPrices = new ArrayList<>();

        while (currentPrice.size() > 0 || newPrice.size() > 0){


            List<Price> currentPriceSame = getSamePrices(currentPrice);
            List<Price> newPriceSame = getSamePrices(newPrice);

            for(int i = 0 ; i < newPriceSame.size(); i++){
                currentPriceSame = getUpdatePrice(newPriceSame.get(i), currentPriceSame);

            }

            newUpdatesPrices.addAll(currentPriceSame);

        }

        return newUpdatesPrices;
    }


    private List<Price> getUpdatePrice(Price newPrice, List<Price> oldPrice){

        List<Price> updatePricesDate = new ArrayList<>();

        for(int i = 0; i < oldPrice.size(); i++){

            Price currentPrice = oldPrice.get(i);

            if(isDateOverlap(currentPrice, newPrice)){

                Price price = new Price(currentPrice.getId(), currentPrice.getProductCode(), currentPrice.getNumber(),
                        currentPrice.getDepart(), newPrice.getEnd(), currentPrice.getEnd(),currentPrice.getValue());

                currentPrice.setEnd(newPrice.getBegin());
                updatePricesDate.add(currentPrice);
                updatePricesDate.add(newPrice);
                updatePricesDate.add(price);

            }


            else if(isNewDatePriceOverlapOldPrice(currentPrice, newPrice)){
                updatePricesDate.add(newPrice);
            }

            else if(isNewDatePriceBeforeOldPrice(currentPrice, newPrice) ){

                if(currentPrice.getValue() == newPrice.getValue()){
                    currentPrice.setEnd(newPrice.getEnd());
                    updatePricesDate.add(currentPrice);
                }
                else{
                    currentPrice.setEnd(newPrice.getBegin());
                    updatePricesDate.add(currentPrice);
                    updatePricesDate.add(newPrice);
                }

            }

            else if(isNewDataEndAfterOldBegin(currentPrice, newPrice) && newPrice.getEnd().before(currentPrice.getEnd())){

                currentPrice.setBegin(newPrice.getEnd());
                updatePricesDate.add(currentPrice);

            }

            else {
                updatePricesDate.add(currentPrice);
            }

        }

        return updatePricesDate;

    }


    // Новая цена полностью попадает в отрезок старой цены
    private boolean isDateOverlap(Price prevPrice, Price nextPrice){
        return prevPrice.getBegin().before(nextPrice.getBegin()) && prevPrice.getEnd().after(nextPrice.getEnd());
    }

    //Новая цена захватывает конец старой цены
    private boolean isNewDatePriceBeforeOldPrice(Price prevPrice, Price nextPrice){
        return nextPrice.getBegin().before(prevPrice.getEnd()) && nextPrice.getEnd().after(prevPrice.getEnd());
    }

    // Новая цена полностью перекрывает отрезок времени старой цены
    private boolean isNewDatePriceOverlapOldPrice(Price prevPrice, Price nextPrice){

        return nextPrice.getBegin().equals(prevPrice.getBegin());

    }

    //Конец новой цены перекрывает конец старой цены
    private boolean isNewDataEndAfterOldBegin(Price prevPrice, Price nextPrice){
        return nextPrice.getEnd().after(prevPrice.getBegin());
    }


    private List<Price> getSamePrices(List<Price> allPrices){

        List<Price> samePrices = new ArrayList<>();
        Iterator<Price> iteratorPrice = allPrices.iterator();

        samePrices.add(iteratorPrice.next());
        iteratorPrice.remove();

        while (iteratorPrice.hasNext()){

            Price nextPrice = iteratorPrice.next();

            if(nextPrice.isSameProduct(samePrices.get(0))){
                samePrices.add(nextPrice);
                iteratorPrice.remove();
            }

        }

        Collections.sort(samePrices, new Comparator<Price>() {
            @Override
            public int compare(Price price1, Price price2) {
                return price1.getBegin().before(price2.getBegin())?-1:0;
            }
        });

        return samePrices;
    }







}
