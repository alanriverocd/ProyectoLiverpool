package com.liverpool.exam.model;

/**
 * DTO summarizing an order and its items for embedding into user responses.
 */
public class OrderSummary {
    private String orderRef;
    private String orderStatus;
    private String storeName;
    private String estimatedDelivery;
    private java.util.List<ItemSummary> items = new java.util.ArrayList<>();

    public String getOrderRef() { return orderRef; }
    public void setOrderRef(String orderRef) { this.orderRef = orderRef; }
    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }
    public String getStoreName() { return storeName; }
    public void setStoreName(String storeName) { this.storeName = storeName; }
    public String getEstimatedDelivery() { return estimatedDelivery; }
    public void setEstimatedDelivery(String estimatedDelivery) { this.estimatedDelivery = estimatedDelivery; }
    public java.util.List<ItemSummary> getItems() { return items; }
    public void setItems(java.util.List<ItemSummary> items) { this.items = items; }
}
