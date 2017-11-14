package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }
    public List<MealWithExceed> getAll() {
        log.info("getAll");
        return MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay());
    }

    public List<MealWithExceed> getAllFiltered(String startTime, String endTime,
                                               String startDate, String endDate) {
        log.info("getAllFiltered: time: {} - {} date: {} - {}", startTime,endTime,startDate,endDate);
        return service.getAllFiltered(AuthorizedUser.id(),
                LocalDateTime.of(
                        !startDate.isEmpty() ? LocalDate.parse(startDate) : LocalDate.MIN,
                        !startTime.isEmpty() ? LocalTime.parse(startTime) : LocalTime.MIN),
                LocalDateTime.of(
                        !endDate.isEmpty() ? LocalDate.parse(endDate) : LocalDate.MAX,
                        !endTime.isEmpty() ? LocalTime.parse(endTime) : LocalTime.MAX
                ),
                AuthorizedUser.getCaloriesPerDay());
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(AuthorizedUser.id(),id);
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(AuthorizedUser.id(),meal);
    }

    public void delete(int id) {
        log.info("delete meal with id = {}", id);
        service.delete(AuthorizedUser.id(),id);
    }

    public void update(Meal meal) {
        log.info("update {} with id={}", meal, meal.getId());
        assureIdConsistent(meal, meal.getId());
        service.update(AuthorizedUser.id(),meal);
    }
}