package com.plazoleta.msrestaurant.domain.model;

public class Dish {

    private Long id;
    private String name;
    private Integer price;
    private String description;
    private String imageUrl;
    private Long categoryId;
    private Long restaurantId;
    private Boolean active;
    private Long ownerId; // ⚠️ Necesario para validar que es el propietario

    public Dish(Long id, String name, Integer price, String description,
                String imageUrl, Long categoryId, Long restaurantId,
                Boolean active, Long ownerId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
        this.restaurantId = restaurantId;
        this.active = active;
        this.ownerId = ownerId;
    }

    // Constructor sin ID (útil para creación)
    public Dish(String name, Integer price, String description,
                String imageUrl, Long categoryId, Long restaurantId,
                Boolean active, Long ownerId) {
        this(null, name, price, description, imageUrl, categoryId, restaurantId, active, ownerId);
    }

    // Constructor sin ownerID
    public Dish(Long id, String name, Integer price, String description,
                String imageUrl, Long categoryId, Long restaurantId,
                Boolean active) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
        this.restaurantId = restaurantId;
        this.active = active;
    }

    // Getters y Setters

    public Long getId() { return id; }

    public String getName() { return name; }

    public Integer getPrice() { return price; }

    public String getDescription() { return description; }

    public String getImageUrl() { return imageUrl; }

    public Long getCategoryId() { return categoryId; }

    public Long getRestaurantId() { return restaurantId; }

    public Boolean getActive() { return active; }

    public Long getOwnerId() { return ownerId; }

    public void setId(Long id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setPrice(Integer price) { this.price = price; }

    public void setDescription(String description) { this.description = description; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public void setRestaurantId(Long restaurantId) { this.restaurantId = restaurantId; }

    public void setActive(Boolean active) { this.active = active; }

    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", categoryId=" + categoryId +
                ", restaurantId=" + restaurantId +
                ", active=" + active +
                ", ownerId=" + ownerId +
                '}';
    }
}
