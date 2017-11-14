package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
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

    private boolean belongsToUser(int userId, Meal meal) {
        return  meal != null && meal.getUserId() == userId;
    }

    @Override
    public Meal save(int userId, Meal meal) {
        log.info("save {}", meal);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        } else {
            Meal mealToUpdate = mealRepository.get(meal.getId());
            if(!belongsToUser(userId, mealToUpdate)){
                return null;
            }
        }
        meal.setUserId(userId);
        mealRepository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int userId, int id) {
        log.info("delete {}", id);
        Meal meal = mealRepository.get(id);
        return belongsToUser(userId,meal) && mealRepository.remove(id) != null;
    }

    @Override
    public Meal get(int userId, int id) {
        log.info("get {}", id);
        Meal meal = mealRepository.get(id);
        if(belongsToUser(userId,meal)) {
            return meal;
        }
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll");
        return mealRepository.values().stream()
                .filter(meal -> belongsToUser(userId,meal))
                .sorted(MEAL_DATETIME_COMPARATOR)
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getAllFiltered(int userId, LocalDateTime start, LocalDateTime end) {
        log.info("getAllFiltered");
        return getAll(userId).stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDate(), start.toLocalDate(),end.toLocalDate()))
                .collect(Collectors.toList());
    }

}

