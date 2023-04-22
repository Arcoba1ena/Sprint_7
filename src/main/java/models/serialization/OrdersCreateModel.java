package models.serialization;

import java.util.ArrayList;

public class OrdersCreateModel {
    private String phone;
    private String address;
    private String comment;
    private String lastName;
    private Integer rentTime;
    private String firstName;
    private String deliveryDate;
    private Integer metroStation;
    private ArrayList<String> color;


    public OrdersCreateModel(String phone, String address, String comment, String lastName,
                             Integer rentTime, String firstName, String deliveryDate,
                             Integer metroStation, ArrayList<String> color) {
        this.phone = phone;
        this.address = address;
        this.comment = comment;
        this.lastName = lastName;
        this.rentTime = rentTime;
        this.firstName = firstName;
        this.deliveryDate = deliveryDate;
        this.metroStation = metroStation;
        this.color = color;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getRentTime() {
        return rentTime;
    }

    public void setRentTime(Integer rentTime) {
        this.rentTime = rentTime;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Integer getMetroStation() {
        return metroStation;
    }

    public void setMetroStation(Integer metroStation) {
        this.metroStation = metroStation;
    }

    public ArrayList<String> getColor() {
        return color;
    }

    public void setColor(ArrayList<String> color) {
        this.color = color;
    }
}