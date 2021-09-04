package ru.albabich.grad.web.menuitem;

import ru.albabich.grad.model.MenuItem;
import ru.albabich.grad.to.MenuItemTo;
import ru.albabich.grad.web.MatcherFactory;

import java.time.Month;
import java.util.List;

import static java.time.LocalDate.now;
import static java.time.LocalDate.of;

public class MenuItemTestData {
    public static final MatcherFactory.Matcher<MenuItem> MENU_ITEM_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(MenuItem.class, "restaurant");
    public static final MatcherFactory.Matcher<MenuItemTo> MENU_ITEM_TO_MATCHER = MatcherFactory.usingEqualsComparator(MenuItemTo.class);

    public static final int NOT_FOUND = 100;
    public static final int MENU_ITEM1_ID = 1;
    public static final MenuItem menuItem1 = new MenuItem(MENU_ITEM1_ID, now(), "salad", 25000);
    public static final MenuItem menuItem2 = new MenuItem(MENU_ITEM1_ID + 1, now(), "khachapuri", 28000);
    public static final MenuItem menuItem3 = new MenuItem(MENU_ITEM1_ID + 2, now(), "lobio", 20000);
    public static final MenuItem menuItem4 = new MenuItem(MENU_ITEM1_ID + 3, of(2021, Month.MARCH, 24), "phali", 5000);
    public static final MenuItem menuItem5 = new MenuItem(MENU_ITEM1_ID + 4, of(2021, Month.MARCH, 24), "wine", 26000);
    public static final MenuItem menuItem6 = new MenuItem(MENU_ITEM1_ID + 5, of(2021, Month.MARCH, 24), "salad", 23050);
    public static final MenuItem menuItem7 = new MenuItem(MENU_ITEM1_ID + 6, of(2021, Month.MARCH, 24), "ribs BBQ", 55050);
    public static final MenuItem menuItem8 = new MenuItem(MENU_ITEM1_ID + 7, now(), "steak", 75050);
    public static final MenuItem menuItem9 = new MenuItem(MENU_ITEM1_ID + 8, of(2021, Month.MARCH, 24), "shashlik", 45000);
    public static final MenuItem menuItem10 = new MenuItem(MENU_ITEM1_ID + 9, of(2021, Month.MARCH, 24), "beer", 29000);
    public static final MenuItem menuItem11 = new MenuItem(MENU_ITEM1_ID + 10, of(2021, Month.MARCH, 24), "salad", 20000);
    public static final MenuItem menuItem12 = new MenuItem(MENU_ITEM1_ID + 11, of(2021, Month.MARCH, 24), "pork knuckle", 65000);
    public static final MenuItem menuItem13 = new MenuItem(MENU_ITEM1_ID + 12, now(), "chicken", 45000);
    public static final MenuItem menuItem14 = new MenuItem(MENU_ITEM1_ID + 13, of(2021, Month.MARCH, 24), "rib eye steak", 95000);
    public static final MenuItem menuItem15 = new MenuItem(MENU_ITEM1_ID + 14, of(2021, Month.MARCH, 24), "beer", 35000);

    public static final List<MenuItem> menuItemsRest1Today = List.of(menuItem1, menuItem2, menuItem3);
    public static final List<MenuItem> menuItemsRest2Today = List.of(menuItem8);
    public static final List<MenuItem> menuItemsRest3Today = List.of(menuItem13);

}
