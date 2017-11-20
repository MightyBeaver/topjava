package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;


import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() throws Exception {
        Meal meal = service.get(USER_MEAL_4.getId(), USER_ID);
        assertMatch(meal, USER_MEAL_4);
    }

    @Test(expected = NotFoundException.class)
    public void getNonExistentMeal() throws Exception {
        service.get(100100, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getOtherUserMeal() throws Exception {
        service.get(USER_MEAL_1.getId(), ADMIN_ID);
    }

    @Test
    public void delete() throws Exception {
        service.delete(ADMIN_MEAL_0.getId(), ADMIN_ID);
        assertMatch(service.getAll(ADMIN_ID), ADMIN_MEAL_1);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNonExistentMeal() throws Exception {
        service.delete(100100, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteOtherUserMeal() throws Exception {
        service.delete(ADMIN_MEAL_0.getId(), USER_ID);
    }

    @Test
    public void getBetweenDates() throws Exception {
        Collection<Meal> meals = service.getBetweenDates(
                LocalDate.of(2015, Month.MAY, 31),
                LocalDate.of(2015, Month.MAY, 31),
                USER_ID);
       assertMatch(meals, Arrays.asList(USER_MEAL_5, USER_MEAL_4,USER_MEAL_3));
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        Collection<Meal> meals = service.getBetweenDateTimes(
                LocalDateTime.of(2015, Month.MAY, 30, 0, 0),
                LocalDateTime.of(2015, Month.MAY, 30, 13, 0),
                USER_ID);
        assertMatch(meals, Arrays.asList(USER_MEAL_1, USER_MEAL_0));
    }

    @Test
    public void getAll() throws Exception {
        Collection<Meal> allRetrieved = service.getAll(USER_ID);
        Collection<Meal> allUserMeals = Arrays.asList(USER_MEAL_5, USER_MEAL_4, USER_MEAL_3,
                USER_MEAL_2, USER_MEAL_1, USER_MEAL_0);
        assertMatch(allRetrieved, allUserMeals);
    }

    @Test
    public void update() throws Exception {
        Meal updatedMeal = new Meal(ADMIN_MEAL_0);
        updatedMeal.setDescription("Админ плотный завтрак");
        updatedMeal.setCalories(700);
        service.update(updatedMeal, ADMIN_ID);
        assertMatch(service.get(updatedMeal.getId(), ADMIN_ID),updatedMeal);
    }

    @Test(expected = NotFoundException.class)
    public void updateOtherUserMeal() throws Exception {
        service.update(USER_MEAL_0, ADMIN_ID);
    }

    @Test
    public void save() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.now(), "Админ новая еда", 600);
        Meal created = service.create(newMeal, ADMIN_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(ADMIN_ID), Arrays.asList(newMeal, ADMIN_MEAL_1, ADMIN_MEAL_0));
    }

    @Test(expected = NotFoundException.class)
    public void saveWithSameDateTime() throws Exception {
        Meal newMeal = new Meal(ADMIN_MEAL_0);
        newMeal.setId(null);
        service.create(newMeal, ADMIN_ID);
    }

}