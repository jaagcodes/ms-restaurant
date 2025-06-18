package com.plazoleta.msrestaurant.domain.model;

public class Restaurant {

    private Long id;
    private String name;
    private String address;
    private String phone;
    private String logoUrl;
    private String nit;
    private Long ownerId;

    public Restaurant(Long id, String name, String address, String phone, String logoUrl, String nit, Long ownerId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.logoUrl = logoUrl;
        this.nit = nit;
        this.ownerId = ownerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
