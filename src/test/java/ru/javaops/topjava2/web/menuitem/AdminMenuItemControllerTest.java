package ru.javaops.topjava2.web.menuitem;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.MenuItem;
import ru.javaops.topjava2.repository.MenuItemRepository;
import ru.javaops.topjava2.to.MenuItemTo;
import ru.javaops.topjava2.util.JsonUtil;
import ru.javaops.topjava2.util.MenuItemUtil;
import ru.javaops.topjava2.web.AbstractControllerTest;
import ru.javaops.topjava2.web.GlobalExceptionHandler;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.topjava2.web.menuitem.MenuItemTestData.*;
import static ru.javaops.topjava2.web.restaurant.RestaurantTestData.REST1_ID;
import static ru.javaops.topjava2.web.user.UserTestData.ADMIN_MAIL;

class AdminMenuItemControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminMenuItemController.REST_URL + '/';

    @Autowired
    MenuItemRepository menuItemRepository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + REST1_ID + "/menu-items/" + MENU_ITEM1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_ITEM_MATCHER.contentJson(menuItem1));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + REST1_ID + "/menu-items/" + NOT_FOUND))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        MenuItemTo newMenuItemTo = new MenuItemTo("idaho potatoes", 28000);
        MenuItem newMenuItem = MenuItemUtil.createNewFromTo(newMenuItemTo);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + REST1_ID + "/menu-items/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenuItemTo)))
                .andDo(print());

        MenuItem created = MENU_ITEM_MATCHER.readFromJson(action);
        int newId = created.id();
        newMenuItem.setId(newId);
        MENU_ITEM_MATCHER.assertMatch(created, newMenuItem);

        MenuItem actualMenuItem = menuItemRepository.findById(newId).orElse(null);
        MENU_ITEM_MATCHER.assertMatch(actualMenuItem, newMenuItem);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        MenuItemTo updatedTo = new MenuItemTo("salad updated", 280);
        perform(MockMvcRequestBuilders.put(REST_URL + REST1_ID + "/menu-items/" + MENU_ITEM1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andExpect(status().isNoContent())
                .andDo(print());

        MenuItem actualMenuItem = menuItemRepository.findById(MENU_ITEM1_ID).orElse(null);
        MENU_ITEM_MATCHER.assertMatch(actualMenuItem, MenuItemUtil.updateFromTo(new MenuItem(menuItem1), updatedTo));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        MenuItemTo invalid = new MenuItemTo("", 0);
        perform(MockMvcRequestBuilders.post(REST_URL + REST1_ID + "/menu-items/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalid() throws Exception {
        MenuItemTo invalid = new MenuItemTo("", 280);
        perform(MockMvcRequestBuilders.put(REST_URL + REST1_ID + "/menu-items/" + MENU_ITEM1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicate() throws Exception {
        MenuItemTo duplicate = new MenuItemTo("salad", 150);
        perform(MockMvcRequestBuilders.post(REST_URL + REST1_ID + "/menu-items/")
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
        MenuItemTo duplicate = new MenuItemTo("lobio", 280);
        perform(MockMvcRequestBuilders.put(REST_URL + REST1_ID + "/menu-items/" + MENU_ITEM1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(duplicate)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(GlobalExceptionHandler.EXCEPTION_DUPLICATE_MENU_ITEM)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + REST1_ID + "/menu-items/" + MENU_ITEM1_ID))
                .andExpect(status().isNoContent());
        assertFalse(menuItemRepository.findById(MENU_ITEM1_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + REST1_ID + "/menu-items/" + NOT_FOUND))
                .andExpect(status().isUnprocessableEntity());
    }
}