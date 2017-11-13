package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDateTime;
import java.util.List;

public interface MealRepository {
    Meal save(int userId, Meal meal);

    // false if not found
    boolean delete(int userId, int id);

    // null if not found
    Meal get(int userId, int id);

    List<Meal> getAll(int userId);

    List<MealWithExceed> getAllFiltered(int userId, LocalDateTime start, LocalDateTime end, int calories);
}
