package ru.albabich.grad.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.albabich.grad.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.albabich.grad.util.RestaurantUtil.getTosWithVotes;
import static ru.albabich.grad.util.RestaurantUtil.getTosWithMenu;
import static ru.albabich.grad.web.user.UserTestData.USER2_MAIL;

class RestaurantControllerTest extends AbstractControllerTest {
    private static final String REST_URL = RestaurantController.REST_URL + '/';

    @Test
    @WithUserDetails(value = USER2_MAIL)
    void getAllWithMenuItemsToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "with-menu/today"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.REST_WITH_MENU_ITEMS_MATCHER.contentJson(getTosWithMenu(RestaurantTestData.restaurants)));
    }

    @Test
    @WithUserDetails(value = USER2_MAIL)
    void getAllWithVotesToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "with-votes/today"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.REST_TO_WITH_VOTES_MATCHER.contentJson(getTosWithVotes(RestaurantTestData.restaurants)));
    }
}