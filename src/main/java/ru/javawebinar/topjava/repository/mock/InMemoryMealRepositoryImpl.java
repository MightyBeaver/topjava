package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> mealRepository = new ConcurrentHashMap<>();
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private static final Comparator<Meal> MEAL_DATETIME_COMPARATOR =
            Comparator.comparing(Meal::getDateTime).reversed();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> this.save(1,meal));
        mealRepository.get(7).setUserId(2); //для теста
    }

    @Override
    public Meal save(int userId, Meal meal) {
        log.info("save {}", meal);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
        } else {
            Meal mealToUpdate = mealRepository.get(meal.getId());
            if( mealToUpdate == null || mealToUpdate.getUserId().intValue() != meal.getUserId().intValue()){
                return null;
            }
        }
        mealRepository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int userId, int id) {
        log.info("delete {}", id);
        Meal meal = mealRepository.get(id);
        return meal != null && meal.getUserId() == userId && mealRepository.remove(id) != null;
    }

    @Override
    public Meal get(int userId, int id) {
        log.info("get {}", id);
        Meal meal = mealRepository.get(id);
        if(meal != null && meal.getUserId() == userId) {
            return meal;
        }
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll");
        return mealRepository.values().stream()
                .filter(meal -> meal.getUserId() == userId)
                .sorted(MEAL_DATETIME_COMPARATOR)
                .collect(Collectors.toList());
    }

    @Override
    public List<MealWithExceed> getAllFiltered(int userId, LocalDateTime start, LocalDateTime end, int calories) {
        log.info("getAllFiltered");
        return MealsUtil.getFilteredWithExceeded(getAll(userId),start,end,calories);
    }
}

