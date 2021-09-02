package ru.albabich.grad.util;

import ru.albabich.grad.model.Restaurant;
import ru.albabich.grad.to.RestaurantTo;

import java.util.Collection;
import java.util.List;

public class RestaurantUtil {
    public static List<RestaurantTo> getTos(Collection<Restaurant> restaurants) {
        return restaurants.stream()
                .map(restaurant -> createTo(restaurant, restaurant.getVotes().size()))
                .toList();
    }

    public static RestaurantTo createTo(Restaurant restaurant, int votes) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), votes);
    }
}
