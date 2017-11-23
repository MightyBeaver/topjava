package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

   // public static final BeanMatcher<Meal> MATCHER = new BeanMatcher<>();

    //  User meals
    public static final Meal USER_MEAL_0 = new Meal(START_SEQ + 2,
            LocalDateTime.of(2015, Month.MAY, 30, 10, 0),
            "Завтрак", 500);
    public static final Meal USER_MEAL_1 = new Meal(START_SEQ + 3,
            LocalDateTime.of(2015, Month.MAY, 30, 13, 0),
            "Обед", 1000);
    public static final Meal USER_MEAL_2 = new Meal(START_SEQ + 4,
            LocalDateTime.of(2015, Month.MAY, 30, 20, 0),
            "Ужин", 500);
    public static final Meal USER_MEAL_3 = new Meal(START_SEQ + 5,
            LocalDateTime.of(2015, Month.MAY, 31, 10, 0),
            "Завтрак", 1000);
    public static final Meal USER_MEAL_4 = new Meal(START_SEQ + 6,
            LocalDateTime.of(2015, Month.MAY, 31, 13, 0),
            "Обед", 500);
    public static final Meal USER_MEAL_5 = new Meal(START_SEQ + 7,
            LocalDateTime.of(2015, Month.MAY, 31, 20, 0),
            "Ужин", 510);

    //  Admin meals
    public static final Meal ADMIN_MEAL_0 = new Meal(START_SEQ+8,
            LocalDateTime.of(2015, Month.MAY, 30, 9, 0),
            "Админ завтрак", 500);
    public static final Meal ADMIN_MEAL_1 = new Meal(START_SEQ+9,
            LocalDateTime.of(2015, Month.MAY, 30, 14, 0),
            "Админ обед", 1000);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}