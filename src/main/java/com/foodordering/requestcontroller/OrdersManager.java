package com.foodordering.requestcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodordering.order.ClientOrder;
import com.foodordering.restaurantsdata.Rating;
import com.foodordering.restaurantsdata.Restaurant;
import com.foodordering.restaurantsdata.RestaurantsData;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static com.foodordering.FoodOrderingApplication.getRestaurantsData;

@RestController
public class OrdersManager {

    private static final RestaurantsData restaurantsData;

    private static final ObjectMapper mapper;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void registerRestaurant(@RequestBody Restaurant restaurant) {
        restaurantsData.add(restaurant);
    }

    @GetMapping(value = "/menu", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String giveMenu() throws JsonProcessingException {
        return mapper.writeValueAsString(restaurantsData);
    }

    @PostMapping(value = "/order", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String processOrder(@RequestBody ClientOrder clientOrder) throws JsonProcessingException {
        clientOrder.ordersToResponse();
        return mapper.writeValueAsString(clientOrder);
    }

    @PostMapping(value = "/rating", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String newRating(@RequestBody ArrayList<Rating> ratings) {

        for (Rating rating : ratings)
            restaurantsData.addRating(rating);

        return "Success!";
    }

    static {
        mapper = new ObjectMapper();

        restaurantsData = getRestaurantsData();
    }
}
