package ru.javaops.topjava2.web.restaurant;

import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.to.RestaurantTo;
import ru.javaops.topjava2.web.MatcherFactory;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static ru.javaops.topjava2.web.menuitem.MenuItemTestData.*;
import static ru.javaops.topjava2.web.vote.VoteTestData.*;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> REST_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menuItems", "votes", "$$_hibernate_interceptor");
    public static final MatcherFactory.Matcher<RestaurantTo> REST_TO_WITH_VOTES_MATCHER = MatcherFactory.usingEqualsComparator(RestaurantTo.class);
    public static final MatcherFactory.Matcher<Restaurant> REST_WITH_MENU_ITEMS_MATCHER = MatcherFactory.usingAssertions(Restaurant.class,
//     No need use ignoringAllOverriddenEquals, see https://assertj.github.io/doc/#breaking-changes
            (a, e) -> {
                throw new UnsupportedOperationException();
            },
            (a, e) -> assertThat(a).usingRecursiveComparison()
                    .ignoringFields("menuItems.restaurant", "votes").isEqualTo(e));

    public static final int NOT_FOUND = 100;
    public static final int REST1_ID = 1;
    public static final int REST2_ID = 2;
    public static final int REST3_ID = 3;

    public static final Restaurant rest1 = new Restaurant(REST1_ID, "Khachapuri and Wine");
    public static final Restaurant rest2 = new Restaurant(REST2_ID, "Munhell");
    public static final Restaurant rest3 = new Restaurant(REST3_ID, "Kwakinn");
    public static final List<Restaurant> restaurants = List.of(rest1, rest3, rest2);

    static {
        rest1.setMenuItems(menuItemsRest1Today);
        rest2.setMenuItems(menuItemsRest2Today);
        rest3.setMenuItems(menuItemsRest3Today);
        rest1.setVotes(voteRest1Today);
        rest2.setVotes(voteRest2Today);
        rest3.setVotes(voteRest3Today);
    }

    public static final List<Restaurant> restaurantsWithMenuToday = List.of(rest1, rest3, rest2);

    public static Restaurant getNew() {
        return new Restaurant(null, "New Restaurant");
    }

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(rest3);
        updated.setName("UpdatedName");
        return updated;
    }
}
