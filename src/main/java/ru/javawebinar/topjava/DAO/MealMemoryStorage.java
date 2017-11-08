package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MealMemoryStorage implements Meals {

    private ConcurrentHashMap<Integer,Meal> mealStorage = new ConcurrentHashMap<>();
    private AtomicInteger idGenerator  = new AtomicInteger(0);

     {
        mealStorage.put(idGenerator.incrementAndGet(),
            new Meal(idGenerator.get(),LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        mealStorage.put(idGenerator.incrementAndGet(),
                new Meal(idGenerator.get(),LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        mealStorage.put(idGenerator.incrementAndGet(),
                new Meal(idGenerator.get(),LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        mealStorage.put(idGenerator.incrementAndGet(),
                new Meal(idGenerator.get(),LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        mealStorage.put(idGenerator.incrementAndGet(),
                new Meal(idGenerator.get(),LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        mealStorage.put(idGenerator.incrementAndGet(),
                new Meal(idGenerator.get(),LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }


    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealStorage.values());
    }

    @Override
    public void add(Meal meal) {
        int id = idGenerator.incrementAndGet();
        meal.setId(id);
        mealStorage.put(id,meal);
    }

    @Override
    public void update(Meal meal) {
        mealStorage.put(meal.getId(),meal);
    }

    @Override
    public void delete(int id) {
        mealStorage.remove(id);
    }

    @Override
    public Meal getById(int id) {
        return mealStorage.get(id);
    }
}
