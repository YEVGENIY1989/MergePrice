package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Price {

    private long id;
    private String productCode;
    private int number;
    private int depart;
    private Date begin;
    private Date end;
    private long value;
    private boolean isNewPrice;

    private SimpleDateFormat dateFormat;

    public Price(long id, String productCode, int number, int depart, Date begin, Date end, long value) {
        this.id = id;
        this.productCode = productCode;
        this.number = number;
        this.depart = depart;
        this.begin = begin;
        this.end = end;
        this.value = value;

        dateFormat =  new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    }


    public boolean isNewPrice() {
        return isNewPrice;
    }

    public void setIsNewPrice(boolean newPrice) {
        isNewPrice = newPrice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getDepart() {
        return depart;
    }

    public void setDepart(int depart) {
        this.depart = depart;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }


    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Price price = (Price) o;

        if(id != price.id)
            return false;

        if(productCode != null ? !productCode.equals(price.productCode) : price.productCode != null){
            return false;
        }

        if(number != price.number)
            return false;

        if(depart != price.depart)
            return false;

        if(begin != null ? !begin.equals(price.begin) : price.begin != null){
            return false;
        }


        if(end != null ? !end.equals(price.end) : price.end != null){
            return false;
        }

        if(value != value)
            return false;

        return true;

    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + (int)(id ^ (id >> 32));
        result = prime * result + ((productCode == null) ? 0 : productCode.hashCode());
        result = prime * result + (number ^ (number >> 32));
        result = prime * result + (depart ^ (depart >> 32));
        result = prime * result + ((begin == null) ? 0 : begin.hashCode());
        result = prime * result + ((end == null) ? 0 : end.hashCode());
        result = prime * result + (int)(value ^ (value >> 32));

        return result;

    }

    @Override
    public String toString() {


        return "Product code " + productCode + "\n" +
                "Begin date " + dateFormat.format(begin) + "\n" +
                "End date " + dateFormat.format(end) + "\n" +
                Long.toString(value);
    }

    public boolean isSameProduct(Price otherProduct){

        if(productCode.equals(otherProduct.getProductCode()) && number == otherProduct.getNumber() && depart == otherProduct.depart)
            return true;

        return false;

    }

}
