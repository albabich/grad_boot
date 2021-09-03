package ru.albabich.grad.util;

import ru.albabich.grad.model.Restaurant;
import ru.albabich.grad.to.RestaurantWithVotesTo;
import ru.albabich.grad.to.RestaurantWithMenuTo;

import java.util.Collection;
import java.util.List;

public class RestaurantUtil {
    public static List<RestaurantWithVotesTo> getTos(Collection<Restaurant> restaurants) {
        return restaurants.stream()
                .map(restaurant -> createTo(restaurant, restaurant.getVotes().size()))
                .toList();
    }

    public static RestaurantWithVotesTo createTo(Restaurant restaurant, int votes) {
        return new RestaurantWithVotesTo(restaurant.getId(), restaurant.getName(), votes);
    }

    public static List<RestaurantWithMenuTo> getTosWithMenu(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(restaurant -> new RestaurantWithMenuTo(restaurant.getId(),
                        restaurant.getName(),
                        MenuItemUtil.getTos(restaurant.getMenuItems())))
                .toList();
    }
}
