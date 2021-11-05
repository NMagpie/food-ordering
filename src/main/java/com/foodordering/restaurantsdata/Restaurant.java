package com.foodordering.restaurantsdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.foodordering.restaurantsdata.foods.Foods;
import lombok.Getter;

import java.util.ArrayList;

public class Restaurant {

    @JsonProperty("restaurant_id")
    @Getter
    private final int id;

    @JsonProperty("name")
    @Getter
    private final String name;

    @JsonProperty(value = "address" , access = JsonProperty.Access.WRITE_ONLY)
    @Getter
    private final String address;

    @JsonProperty("menu_items")
    private final int menuItems;

    @JsonProperty("menu")
    private final ArrayList<Foods> menu;

    @JsonProperty("rating")
    private float rating;

    @JsonIgnore
    private int ratings = 0;

    @JsonIgnore
    private int rates = 0;

    @JsonIgnore
    @Getter
    private int rejectNumber = 0;

    @JsonCreator
    public Restaurant(@JsonProperty("restaurant_id") int id,
                      @JsonProperty("name") String name,
                      @JsonProperty("address") String address,
                      @JsonProperty("menu_items") int menuItems,
                      @JsonProperty("menu") ArrayList<Foods> menu,
                      @JsonProperty("rating") float rating) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.menuItems = menuItems;
        this.menu = menu;
        this.rating = rating;
    }

    public void addRejectNumber() {
        rejectNumber++;
    }

    public void resetRejectNumber() {
        rejectNumber = 0;
    }

    public void addRating(int rating) {
        rates++;

        ratings += rating;

        this.rating = (float) ratings / rates;
    }

}