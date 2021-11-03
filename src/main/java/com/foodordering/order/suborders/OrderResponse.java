package com.foodordering.order.suborders;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.foodordering.FoodOrderingApplication;
import com.foodordering.restaurantsdata.Restaurant;
import lombok.Getter;

public class OrderResponse {

    @JsonProperty("restaurant_id")
    @Getter
    private int restaurantId;

    @JsonProperty(value = "restaurant_address", access = JsonProperty.Access.READ_ONLY)
    @Getter
    private String address;

    @JsonProperty("order_id")
    private int orderId;

    @JsonProperty("estimated_waiting_time")
    private int estimatedWaitingTime;

    @JsonProperty("created_time")
    private long createdTime;

    @JsonProperty("registered_time")
    private long registeredTime;

    @JsonCreator
    public OrderResponse(@JsonProperty("restaurant_id") int restId,
                         @JsonProperty("order_id") int orderId,
                         @JsonProperty("estimated_waiting_time") int estimatedWaitingTime,
                         @JsonProperty("created_time") long createdTime,
                         @JsonProperty("registered_time") long registeredTime) {
        this.restaurantId = restId;
        this.orderId = orderId;
        this.estimatedWaitingTime = estimatedWaitingTime;
        this.createdTime = createdTime;
        this.registeredTime = registeredTime;

        Restaurant restaurant = FoodOrderingApplication.getRestaurantsData().getRestById(restId);

        if (restaurant == null) address = null;
        else address = FoodOrderingApplication.getRestaurantsData().getRestById(restId).getAddress();

    }
}
