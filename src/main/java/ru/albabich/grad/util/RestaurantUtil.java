package ru.albabich.grad.util;

import lombok.experimental.UtilityClass;
import ru.albabich.grad.model.Restaurant;
import ru.albabich.grad.to.RestaurantWithMenuTo;

import java.util.List;

@UtilityClass
public class RestaurantUtil {

    public static List<RestaurantWithMenuTo> getTosWithMenu(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(RestaurantUtil::getToWithMenu)
                .toList();
    }

    public static RestaurantWithMenuTo getToWithMenu(Restaurant restaurant) {
        return new RestaurantWithMenuTo(restaurant.getId(), restaurant.getName(),
                MenuItemUtil.getTos(restaurant.getMenuItems()));
    }
}
