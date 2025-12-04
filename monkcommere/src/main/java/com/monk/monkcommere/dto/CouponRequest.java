package com.monk.monkcommere.dto;

//this DTO is used when creating a new coupon (POST /coupons)

public class CouponRequest {

    // ef: cart30
    private String code;

    // eg:cartwise
    private String type;

    // Rules that will come as a json
    private String details;

    private boolean active;

    // getters and setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}

