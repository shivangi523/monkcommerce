package com.monk.monkcommere.model;

import jakarta.persistence.*;

//Coupon entity, details field stores flexible rules as JSON text.

@Entity
@Table(name = "coupon")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //eg:"cart80"
    @Column(nullable = false, unique = true)
    private String code;

    //eg:artwise,productwise,bxgy"
    @Column(nullable = false)
    private String type;

    // store rules as JSON string, eg: {"minAmount":500,"discount":50}
    @Column(columnDefinition = "TEXT")
    private String details;

    private boolean active = true;


    //getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

}

