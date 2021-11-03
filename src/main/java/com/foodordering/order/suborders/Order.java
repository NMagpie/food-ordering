package com.foodordering.order.suborders;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;

public class Order {

    @JsonProperty(value = "restaurant_id", access = JsonProperty.Access.WRITE_ONLY)
    @Getter
    private int restaurantId;

    @JsonProperty("items")
    private ArrayList<Integer> items;

    @JsonProperty("priority")
    private int priority;

    @JsonProperty("max_wait")
    private float maxWait;

    @JsonProperty("created_time")
    private long createdTime;

    @JsonCreator
    public Order(@JsonProperty("restaurant_id") int restaurantId,
                 @JsonProperty("items") ArrayList<Integer> items,
                 @JsonProperty("priority") int priority,
                 @JsonProperty("max_wait") float maxWait,
                 @JsonProperty("created_time") long createdTime) {
        this.restaurantId = restaurantId;
        this.items = items;
        this.priority = priority;
        this.maxWait = maxWait;
        this.createdTime = createdTime;
    }
}
