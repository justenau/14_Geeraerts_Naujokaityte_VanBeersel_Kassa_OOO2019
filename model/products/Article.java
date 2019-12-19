package model.products;

/**
 * @author Justė Naujokaitytė, Quinten Geeraerts
 */
public class Article {
    private int code;
    private String description;
    private String group;
    private double price;
    private int stock;

    public Article(int code, String description, String group, double price, int stock) {
        this.code = code;
        this.description = description;
        this.group = group;
        this.price = price;
        this.stock = stock;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "[" +
                "" + code +
                ", " + description +
                ", " + group +
                ", " + price +
                ", " + stock +
                ']';
    }
}
