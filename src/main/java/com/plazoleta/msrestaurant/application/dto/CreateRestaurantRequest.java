package com.plazoleta.msrestaurant.application.dto;

import jakarta.validation.constraints.*;

public class CreateRestaurantRequest {

    @NotBlank(message = "Restaurant name is required")
    @Pattern(regexp = ".*\\D.*", message = "Restaurant name cannot contain only numbers")
    private String name;

    @NotBlank(message = "NIT is required")
    @Pattern(regexp = "\\d+", message = "NIT must be numeric")
    private String nit;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^\\+?\\d{1,13}$",
            message = "Phone number must be numeric with a maximum of 13 characters and may include +"
    )
    private String phone;

    @NotBlank(message = "Logo URL is required")
    private String logoUrl;

    @NotNull(message = "Owner ID is required")
    private Long ownerId;

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
