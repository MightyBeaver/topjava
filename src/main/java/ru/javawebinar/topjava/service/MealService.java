package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface MealService {

    Meal create (int userId, Meal meal);

    void delete(int userId, int id) throws NotFoundException;

    Meal get(int userId, int id) throws NotFoundException;

    List<MealWithExceed> getAllFiltered(int userId, LocalDateTime start, LocalDateTime end, int calories)
            throws NotFoundException;

    void update(int userId, Meal meal) throws NotFoundException;

    List<Meal> getAll(int userId);
}