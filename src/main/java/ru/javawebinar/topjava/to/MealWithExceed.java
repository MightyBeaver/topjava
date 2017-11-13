package ru.javawebinar.topjava.to;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class MealWithExceed {
    private final Integer id;
    private final Integer userId;
    private final LocalDateTime dateTime;
    private final String description;
    private final int calories;
    private final boolean exceed;
    public MealWithExceed(Integer id,Integer userId, LocalDateTime dateTime, String description, int calories, boolean exceed) {
        this.id = id;
        this.userId = userId;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isExceed() {
        return exceed;
    }

    @Override
    public String toString() {
        return "MealWithExceed{" +
                "id=" + id +
                ", userId=" + userId +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", exceed=" + exceed +
                '}';
    }
}