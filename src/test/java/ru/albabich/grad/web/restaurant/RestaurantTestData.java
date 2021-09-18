package ru.albabich.grad.web.restaurant;

import ru.albabich.grad.model.Restaurant;
import ru.albabich.grad.to.RestaurantWithMenuTo;
import ru.albabich.grad.web.MatcherFactory;

import java.util.List;

import static ru.albabich.grad.web.menuitem.MenuItemTestData.*;
import static ru.albabich.grad.web.vote.VoteTestData.*;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> REST_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menuItems", "votes");
    public static final MatcherFactory.Matcher<RestaurantWithMenuTo> REST_WITH_MENU_ITEMS_MATCHER = MatcherFactory.usingEqualsComparator(RestaurantWithMenuTo.class);

    public static final int NOT_FOUND = 100;
    public static final int REST1_ID = 1;
    public static final int REST2_ID = 2;
    public static final int REST3_ID = 3;

    public static final Restaurant rest1 = new Restaurant(REST1_ID, "Khachapuri and Wine");
    public static final Restaurant rest2 = new Restaurant(REST2_ID, "Munhell");
    public static final Restaurant rest3 = new Restaurant(REST3_ID, "Kwakinn");

    static {
        rest1.setMenuItems(menuItemsRest1Today);
        rest2.setMenuItems(menuItemsRest2Today);
        rest3.setMenuItems(menuItemsRest3Today);
    }

    public static final List<Restaurant> restaurants = List.of(rest1, rest3, rest2);

    public static Restaurant getNew() {
        return new Restaurant(null, "New Restaurant");
    }

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(rest3);
        updated.setName("UpdatedName");
        return updated;
    }
}
