package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            AdminRestController adminController = appCtx.getBean(AdminRestController.class);
            MealRestController mealController = appCtx.getBean(MealRestController.class);
            //mealController.getAll().forEach(System.out::println);
            getAllFilteredTest(mealController);
            //updateTest(mealController);
            //createTest(mealController);
            //getTest(mealController);
            //deleteTest(mealController);
        }
    }
    private static void updateTest(MealRestController mealController) {
            Meal mealToUpdate = new Meal(LocalDateTime.of(2016, Month.MAY, 30, 10,
                    0), "Завтрак", 510);
            mealToUpdate.setId(1);
            mealToUpdate.setUserId(1);
            mealController.update(mealToUpdate);
            mealController.getAll().forEach(System.out::println);
            mealToUpdate.setId(7);                                  //чужая еда
            mealController.update(mealToUpdate);
    }
    private static void createTest(MealRestController mealController) {
        Meal mealToCreate = new Meal(LocalDateTime.of(2016, Month.MAY, 30, 10,
                0), "Новая еда", 510);
        mealController.create(mealToCreate);
        mealController.getAll().forEach(System.out::println);
        mealToCreate.setId(7);
        mealController.create(mealToCreate);
    }
    private static void getTest(MealRestController mealController) {
        System.out.println(mealController.get(1));
        System.out.println(mealController.get(6));
        mealController.get(9);
    }
    private static void deleteTest(MealRestController mealController) {
        mealController.delete(1);
        mealController.getAll().forEach(System.out::println);
        mealController.delete(7);
    }
    private static void getAllFilteredTest(MealRestController mealController) {
        //все записи
        mealController.getAllFiltered(null,null,
                null,null)
                .forEach(System.out::println);
        //С 0 до 13 часов, любой день
        mealController.getAllFiltered(LocalTime.MIN,LocalTime.of(13, 0),
                null,null)
                .forEach(System.out::println);
        //Вся еда c 31 числа включительно
        mealController.getAllFiltered(null,null,
                LocalDate.of(2015, Month.MAY,31), null)
                .forEach(System.out::println);
        //еда до 30 числа включительно с 0 до 13
        mealController.getAllFiltered(LocalTime.MIN,LocalTime.of(13, 0),
                null, LocalDate.of(2015, Month.MAY,30))
                .forEach(System.out::println);
    }
}
