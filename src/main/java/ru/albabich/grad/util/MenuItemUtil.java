package ru.albabich.grad.util;

import lombok.experimental.UtilityClass;
import ru.albabich.grad.model.MenuItem;
import ru.albabich.grad.to.MenuItemTo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class MenuItemUtil {
    public static MenuItem createNewFromTo(MenuItemTo menuItemTo) {
        return new MenuItem(null, menuItemTo.getAvailable(), menuItemTo.getName(), menuItemTo.getPrice());
    }

    public static MenuItem createFromTo(MenuItemTo menuItemTo) {
        return new MenuItem(menuItemTo.getId(), menuItemTo.getAvailable(), menuItemTo.getName(), menuItemTo.getPrice());
    }

    public static MenuItem updateFromTo(MenuItem menuItem, MenuItemTo menuItemTo) {
        menuItem.setName(menuItemTo.getName());
        menuItem.setAvailable(menuItemTo.getAvailable());
        menuItem.setPrice(menuItemTo.getPrice());
        return menuItem;
    }

    public static MenuItemTo createTo(MenuItem menuItem) {
        return new MenuItemTo(menuItem.getId(), menuItem.getAvailable(), menuItem.getName(), (menuItem.getPrice()));
    }

    public static List<MenuItemTo> getTos(Collection<MenuItem> menuItems) {
        return menuItems.stream()
                .map(MenuItemUtil::createTo)
                .collect(Collectors.toList());
    }
}
