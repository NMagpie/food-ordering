package com.foodordering.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodordering.order.suborders.Order;
import com.foodordering.order.suborders.OrderResponse;
import com.foodordering.restaurantsdata.Restaurant;
import com.foodordering.restaurantsdata.RestaurantsData;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static com.foodordering.FoodOrderingApplication.getRestaurantsData;

public class ClientOrder {

    @JsonIgnore
    private static final RestTemplate restTemplate;

    @JsonIgnore
    private static final HttpHeaders headers;

    @JsonIgnore
    private static final ObjectMapper mapper;

    @JsonIgnore
    private static final RestaurantsData restaurantsData = getRestaurantsData();

    @JsonProperty("order_id")
    private final int id;

    @JsonProperty(value = "orders", access = JsonProperty.Access.WRITE_ONLY)
    private final ArrayList<Order> orders;

    @JsonProperty(value = "orders", access = JsonProperty.Access.READ_ONLY)
    private final ArrayList<OrderResponse> orderResponses = new ArrayList<>();

    @JsonCreator
    public ClientOrder(@JsonProperty("client_id") int id,
                       @JsonProperty("orders")ArrayList<Order> orders) {

        this.id = id;
        this.orders = orders;

    }

    public void ordersToResponse() {

        for (Order order : orders) {

            Restaurant restaurant = restaurantsData.getRestById(order.getRestaurantId());

            if (restaurant == null) continue;

            String url = restaurant.getAddress();

            url = "http://" + url + "/v2/order";

            try {
                HttpEntity<Order> request = new HttpEntity<>(order, headers);

                String strResponse = restTemplate.postForObject(url, request, String.class);

                OrderResponse response = mapper.readValue(strResponse, OrderResponse.class);

                if (response.getAddress() == null) continue;

                orderResponses.add(response);

                restaurant.resetRejectNumber();

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (ResourceAccessException | HttpClientErrorException | HttpServerErrorException e) {
                restaurant.addRejectNumber();
                if (restaurant.getRejectNumber() > 1) { restaurantsData.remove(restaurant);
                    System.out.println("Restaurant" + restaurant.getName() + "was not responding for two times, it's time to exclude it!");
                }
            }

        }

    }

    static {
        restTemplate = new RestTemplateBuilder().build();

        headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        mapper = new ObjectMapper();
    }

}
