package ru.albabich.grad.web.menuitem;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.albabich.grad.web.restaurant.RestaurantTestData;
import ru.albabich.grad.model.MenuItem;
import ru.albabich.grad.repository.MenuItemRepository;
import ru.albabich.grad.to.MenuItemTo;
import ru.albabich.grad.util.JsonUtil;
import ru.albabich.grad.util.MenuItemUtil;
import ru.albabich.grad.web.AbstractControllerTest;
import ru.albabich.grad.web.GlobalExceptionHandler;

import java.time.LocalDate;
import java.time.Month;

import static java.time.LocalDate.now;
import static java.time.LocalDate.of;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.albabich.grad.util.MenuItemUtil.createTo;
import static ru.albabich.grad.web.user.UserTestData.ADMIN_MAIL;

class AdminMenuItemControllerTest extends AbstractControllerTest {

    private static final String REST_URL = "/api/admin/restaurants/";

    @Autowired
    MenuItemRepository menuItemRepository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RestaurantTestData.REST1_ID + "/menu-items/" + MenuItemTestData.MENU_ITEM1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MenuItemTestData.MENU_ITEM_TO_MATCHER.contentJson(createTo(MenuItemTestData.menuItem1)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RestaurantTestData.REST1_ID + "/menu-items/" + MenuItemTestData.NOT_FOUND))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        MenuItemTo newMenuItemTo = new MenuItemTo(LocalDate.now(), "idaho potatoes", 28099);
        MenuItem newMenuItem = MenuItemUtil.createNewFromTo(newMenuItemTo);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + RestaurantTestData.REST1_ID + "/menu-items/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenuItemTo)))
                .andDo(print());

        MenuItemTo createdTo = MenuItemTestData.MENU_ITEM_TO_MATCHER.readFromJson(action);
        int newId = createdTo.id();
        newMenuItem.setId(newId);
        MenuItemTestData.MENU_ITEM_MATCHER.assertMatch(MenuItemUtil.createFromTo(createdTo), newMenuItem);

        MenuItem actualMenuItem = menuItemRepository.findById(newId).orElse(null);
        MenuItemTestData.MENU_ITEM_MATCHER.assertMatch(actualMenuItem, newMenuItem);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        MenuItemTo updatedTo = new MenuItemTo(of(2021, Month.SEPTEMBER, 24), "salad updated", 28000);
        perform(MockMvcRequestBuilders.put(REST_URL + RestaurantTestData.REST1_ID + "/menu-items/" + MenuItemTestData.MENU_ITEM1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andExpect(status().isNoContent())
                .andDo(print());

        MenuItem actualMenuItem = menuItemRepository.findById(MenuItemTestData.MENU_ITEM1_ID).orElse(null);
        MenuItemTestData.MENU_ITEM_MATCHER.assertMatch(actualMenuItem, MenuItemUtil.updateFromTo(new MenuItem(MenuItemTestData.menuItem1), updatedTo));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        MenuItemTo invalid = new MenuItemTo(null, "", 0);
        perform(MockMvcRequestBuilders.post(REST_URL + RestaurantTestData.REST1_ID + "/menu-items/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalid() throws Exception {
        MenuItemTo invalid = new MenuItemTo(null, "", 28000);
        perform(MockMvcRequestBuilders.put(REST_URL + RestaurantTestData.REST1_ID + "/menu-items/" + MenuItemTestData.MENU_ITEM1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicate() throws Exception {
        MenuItemTo duplicate = new MenuItemTo(now(), "salad", 15000);
        perform(MockMvcRequestBuilders.post(REST_URL + RestaurantTestData.REST1_ID + "/menu-items/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(duplicate)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(GlobalExceptionHandler.EXCEPTION_DUPLICATE_MENU_ITEM)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        MenuItemTo duplicate = new MenuItemTo(now(), "lobio", 28000);
        perform(MockMvcRequestBuilders.put(REST_URL + RestaurantTestData.REST1_ID + "/menu-items/" + MenuItemTestData.MENU_ITEM1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(duplicate)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(GlobalExceptionHandler.EXCEPTION_DUPLICATE_MENU_ITEM)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + RestaurantTestData.REST1_ID + "/menu-items/" + MenuItemTestData.MENU_ITEM1_ID))
                .andExpect(status().isNoContent());
        assertFalse(menuItemRepository.findById(MenuItemTestData.MENU_ITEM1_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + RestaurantTestData.REST1_ID + "/menu-items/" + MenuItemTestData.NOT_FOUND))
                .andExpect(status().isUnprocessableEntity());
    }
}