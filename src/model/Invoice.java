package model;
public class Invoice {
    private int invoiceNumber;
    private String invoiceDate;
    private int customerId;
    private int productId;
    private double unitPrice;
    private int units;
    private double totalPrice;
    private double discount;
    private double discountedTotalPrice;

    //Getters-----------------------------------------------------------------------------------------------------------
    public double getDiscount() {
        return discount;
    }
    public int getInvoiceNumber() {
        return invoiceNumber;
    }
    public String getInvoiceDate() {
        return invoiceDate;
    }
    public int getCustomerId() {
        return customerId;
    }
    public int getProductId() {
        return productId;
    }
    public int getUnits() {
        return units;
    }
    public double getUnitPrice() {
        return unitPrice;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public double getDiscountedTotalPrice() {
        return discountedTotalPrice;
    }

    //setters-----------------------------------------------------------------------------------------------------------
    public void setDiscount(double discount) {this.discount = discount;}
    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public void setUnits(int units) {
        this.units = units;
    }
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public void setDiscountedTotalPrice(double discountedTotalPrice) {this.discountedTotalPrice = discountedTotalPrice;}
}
