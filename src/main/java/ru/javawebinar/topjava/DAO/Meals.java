package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Meals {
    List<Meal> getAll();

    void add(Meal meal);

    void update(Meal meal);

    void delete(int id);

    Meal getById(int id);
}
