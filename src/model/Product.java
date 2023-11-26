package model;
public class Product {
    private int productId;
    private String productName;
    private String description;
    private double purchasePrice;
    private double sellingPrice;
    private int quantity;
    //Getters-----------------------------------------------------------------------------------------------------------
    public int getProductId() {return productId;}
    public String getProductName() {return productName;}
    public String getDescription() {return description;}
    public double getPurchasePrice() {return purchasePrice;}
    public double getSellingPrice() {return sellingPrice;}
    public int getQuantity() {return quantity;}

    //setters-----------------------------------------------------------------------------------------------------------
    public void setProductId(int productId) {this.productId = productId;}
    public void setProductName(String productName) {this.productName = productName;}
    public void setDescription(String description) {this.description = description;}
    public void setPurchasePrice(double purchasePrice) {this.purchasePrice = purchasePrice;}
    public void setSellingPrice(double sellingPrice) {this.sellingPrice = sellingPrice;}
    public void setQuantity(int quantity) {this.quantity = quantity;}




}
