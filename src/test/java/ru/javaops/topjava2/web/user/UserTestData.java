package ru.javaops.topjava2.web.user;

import ru.javaops.topjava2.model.Role;
import ru.javaops.topjava2.model.User;
import ru.javaops.topjava2.util.JsonUtil;
import ru.javaops.topjava2.web.MatcherFactory;

import java.util.Collections;
import java.util.Date;

public class UserTestData {
    public static final MatcherFactory.Matcher<User> MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "registered", "password");
    public static final int ADMIN_ID = 1;
    public static final int USER2_ID = 2;

    public static final int NOT_FOUND = 100;
    public static final String USER2_MAIL = "user2@mail.ru";
    public static final String ADMIN_MAIL = "admin@gmail.com";

    public static final User admin = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN, Role.USER);
    public static final User user2 = new User(USER2_ID, "User2", "user2@mail.ru", "password1", Role.USER);
    public static final User user3 = new User(USER2_ID + 1, "User3", "user3@mail.ru", "password2", Role.USER);
    public static final User user4 = new User(USER2_ID + 2, "User4", "user4@mail.ru", "password3", Role.USER);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", false, new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        return new User(USER2_ID, "UpdatedName", USER2_MAIL, "newPass", false, new Date(), Collections.singleton(Role.ADMIN));
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
