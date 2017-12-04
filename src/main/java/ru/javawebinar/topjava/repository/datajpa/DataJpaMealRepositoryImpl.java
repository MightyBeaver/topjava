package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {

    @Autowired
    private CrudMealRepository crudMealRepository;

    @Autowired
    private CrudUserRepository crudUserRepository;

    //private static final Sort SORT_DATETIME_DESC = new Sort(Sort.Direction.DESC, "date_time");

    @Override
    public Meal save(Meal meal, int userId) {
        if (!meal.isNew() && get(meal.getId(), userId) == null) {
            return null;
        }
        meal.setUser(crudUserRepository.findById(userId).orElse(null));
        return meal.getUser() != null ? crudMealRepository.save(meal) : null;
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudMealRepository.delete(id,userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = crudMealRepository.findById(id).orElse(null);
        return meal != null && meal.getUser().getId() == userId ? meal : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        //return crudMealRepository.findAllByUserId(userId,SORT_DATETIME_DESC);
        return crudMealRepository.findAllByUserIdOrderByDateTimeDesc(userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        //return crudMealRepository.findAllByDateTimeBetweenAndUserId(startDate,endDate,userId,SORT_DATETIME_DESC);
        return crudMealRepository.findAllByDateTimeBetweenAndUserIdOrderByDateTimeDesc(startDate,endDate,userId);
    }
}
