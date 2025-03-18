package com.example.ActionService.Dto;


import lombok.*;
@Getter
@Setter
public class OrderEvent {
    private Long orderId;
    private String userEmail;
    private String restaurantEmail;
    private String message;
    private double totalAmount;
    private String userAddress;


    public OrderEvent(Long orderId, String userEmail, String restaurantEmail, String message, double totalAmount, String userAddress) {
        this.orderId = orderId;
        this.userEmail = userEmail;
        this.restaurantEmail = restaurantEmail;
        this.message = message;
        this.totalAmount = totalAmount;
        this.userAddress = userAddress;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getRestaurantEmail() {
        return restaurantEmail;
    }

    public void setRestaurantEmail(String restaurantEmail) {
        this.restaurantEmail = restaurantEmail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

