package com.foodordering.restaurantsdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public class Rating {

    @Getter
    private final int restaurantId;

    @Getter
    private final int rating;

    @JsonCreator
    public Rating(@JsonProperty("restaurant_id") int restaurantId,
                  @JsonProperty("rating") int rating) {
        this.restaurantId = restaurantId;
        this.rating = rating;
    }

}
