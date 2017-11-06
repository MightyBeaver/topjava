package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.DAO.MealMemoryStorage;
import ru.javawebinar.topjava.DAO.Meals;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private static final int CALORIES_PER_DAY = 2000;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meals");
        Meals meals = new MealMemoryStorage();
        List<MealWithExceed> mealsWithExceed =
                MealsUtil.getFilteredWithExceeded(meals.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
        request.setAttribute("mealsWithExceed",mealsWithExceed);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}