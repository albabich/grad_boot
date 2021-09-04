package ru.albabich.grad.util;

import ru.albabich.grad.model.MenuItem;
import ru.albabich.grad.to.MenuItemTo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MenuItemUtil {
    public static MenuItem createNewFromTo(MenuItemTo menuItemTo) {
        return new MenuItem(null, menuItemTo.getName(), (int) (menuItemTo.getPrice() * 100));
    }

    public static MenuItem createFromTo(MenuItemTo menuItemTo) {
        return new MenuItem(menuItemTo.getId(), menuItemTo.getName(), (int) (menuItemTo.getPrice() * 100));
    }

    public static MenuItem updateFromTo(MenuItem menuItem, MenuItemTo menuItemTo) {
        menuItem.setName(menuItemTo.getName());
        menuItem.setPrice((int) (menuItemTo.getPrice() * 100));
        return menuItem;
    }

    public static MenuItemTo createTo(MenuItem menuItem) {
        return new MenuItemTo(menuItem.getId(), menuItem.getDate(), menuItem.getName(), (menuItem.getPrice() / 100.));
    }

    public static List<MenuItemTo> getTos(Collection<MenuItem> menuItems) {
        return menuItems.stream()
                .map(MenuItemUtil::createTo)
                .collect(Collectors.toList());
    }
}
