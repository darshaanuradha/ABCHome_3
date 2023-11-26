package model;
public class Customer {
    private int customerId;
    private String customerName;
    private String email;
    private String address;
    private String contactNumber;
    private String dateOfBirth;
    private String gender;

    //getters-----------------------------------------------------------------------------------------------------------
    public int getCustomerId() {return customerId;}
    public String getCustomerName() {return customerName;}
    public String getEmail() {return email;}
    public String getAddress() {return address;}
    public String getContactNumber() {return contactNumber;}
    public String getDateOfBirth() {return dateOfBirth;}
    public String getGender() {return gender;}

    //setters-----------------------------------------------------------------------------------------------------------
    public void setCustomerId(int customerId) {this.customerId = customerId;}
    public void setCustomerName(String customerName) {this.customerName = customerName;}
    public void setEmail(String email) {this.email = email;}
    public void setAddress(String address) {this.address = address;}
    public void setContactNumber(String contactNumber) {this.contactNumber = contactNumber;}
    public void setDateOfBirth(String dateOfBirth) {this.dateOfBirth = dateOfBirth;}
    public void setGender(String gender) {this.gender = gender;}

}
