package com.foodordering.restaurantsdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class RestaurantsData {

    @JsonProperty("restaurants")
    private int restaurants = 0;

    @JsonProperty("restaurants_data")
    private final ArrayList<Restaurant> restaurantsData = new ArrayList<>();

    public void add(Restaurant restaurant) {
        restaurants++;
        restaurantsData.add(restaurant);
    }

    public void remove(Restaurant restaurant) {
        restaurants--;
        restaurantsData.remove(restaurant);
    }

    public Restaurant getRestById(int id) {

        for (Restaurant restaurant : restaurantsData)
            if (restaurant.getId() == id) return restaurant;

        return null;
    }

    public void addRating(Rating rating) {
        getRestById(rating.getRestaurantId()).addRating(rating.getRating());
    }

}
