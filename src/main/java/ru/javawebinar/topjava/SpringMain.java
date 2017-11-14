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
            MealRestController mealController = appCtx.getBean(MealRestController.class);
            Meal mealToUpdate = new Meal(LocalDateTime.of(2016, Month.MAY, 30, 10,
                    0), "Завтрак", 510);
            mealToUpdate.setId(1);
            mealController.update(mealToUpdate);
            mealController.getAll().forEach(System.out::println);
            mealToUpdate.setId(7);                                  //чужая еда
            mealController.update(mealToUpdate);
        }
    }

}
