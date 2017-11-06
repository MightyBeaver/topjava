package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.DAO.MealMemoryStorage;
import ru.javawebinar.topjava.DAO.Meals;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private static final int CALORIES_PER_DAY = 2000;
    private static final Meals mealsDAO = new MealMemoryStorage();
    private static String INSERT_OR_EDIT = "/mealForm.jsp";
    private static String SHOW_MEALS = "/meals.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meals");
        String forwardLocation;
        String action = request.getParameter("action");
        if(action == null) {
            action = "listMeals";
        }
        log.debug(action);
        if (action.equalsIgnoreCase("delete")){
            int id = Integer.parseInt(request.getParameter("id"));
            mealsDAO.delete(id);
            response.sendRedirect("meals");
        } else if (action.equalsIgnoreCase("edit")){
            forwardLocation = INSERT_OR_EDIT;
            int id = Integer.parseInt(request.getParameter("id"));
            Meal meal = mealsDAO.getById(id);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher(forwardLocation).forward(request, response);
        } else if (action.equalsIgnoreCase("listMeals")){
            forwardLocation = SHOW_MEALS;
            List<MealWithExceed> mealsWithExceed =
                    MealsUtil.getFilteredWithExceeded(mealsDAO.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
            request.setAttribute("mealsWithExceed",mealsWithExceed);
            request.getRequestDispatcher(forwardLocation).forward(request, response);
        } else if (action.equalsIgnoreCase("insert")){
            forwardLocation = INSERT_OR_EDIT;
            request.getRequestDispatcher(forwardLocation).forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        log.debug("meal post requsest");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal = new Meal(dateTime, description, calories);
        try {
            log.debug("update");
            int id = Integer.parseInt(request.getParameter("id"));
            meal.setId(id);
            mealsDAO.update(meal);
        } catch (NumberFormatException e) {
            log.debug("add");
            mealsDAO.add(meal);
        }
        doGet(request, response);
    }
}