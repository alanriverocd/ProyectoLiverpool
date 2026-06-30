package com.liverpool.exam.model;

/**
 * DTO for a single item within an order summary.
 */
public class ItemSummary {
    private String itemId;
    private String displayName;
    private int quantity;

    public String getItemId() { return itemId; }
    public void setItemId(String itemId) { this.itemId = itemId; }
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
