package ru.javaops.topjava2.util;

import ru.javaops.topjava2.model.MenuItem;
import ru.javaops.topjava2.to.MenuItemTo;

public class MenuItemUtil {
    public static MenuItem createNewFromTo(MenuItemTo menuItemTo) {
        return new MenuItem(null, menuItemTo.getName(), menuItemTo.getPrice());
    }

    public static MenuItem updateFromTo(MenuItem menuItem, MenuItemTo menuItemTo) {
        menuItem.setName(menuItemTo.getName());
        menuItem.setPrice(menuItemTo.getPrice());
        return menuItem;
    }
}
