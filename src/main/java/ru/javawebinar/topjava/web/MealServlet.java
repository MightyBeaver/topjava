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
    private Logger log = LoggerFactory.getLogger(MealServlet.class);
    private int CALORIES_PER_DAY = 2000;
    private Meals mealsDAO = new MealMemoryStorage();
    private String INSERT_OR_EDIT = "/mealForm.jsp";
    private String SHOW_MEALS = "/meals.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meals");
        String action = request.getParameter("action");
        if(action == null) {
            action = "listMeals";
        }
        log.debug(action);
        switch (action) {
            case "delete": deleteMeal(request,response); break;
            case "edit" : editMeal(request,response); break;
            case "listMeals" : listMeals(request,response); break;
            case "insert": insertMeal(request,response); break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        log.debug("meal post request");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal = new Meal(dateTime, description, calories);
        String idParam = request.getParameter("id");
        log.debug("idParam: "+idParam);
        if (idParam.equals("")) {
            log.debug("add");
            mealsDAO.add(meal);
        } else {
            log.debug("update");
            int id = Integer.parseInt(idParam);
            meal.setId(id);
            mealsDAO.update(meal);
        }
        response.sendRedirect("meals");
    }

    private void deleteMeal(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        mealsDAO.delete(id);
        response.sendRedirect("meals");
    }

    private void editMeal(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Meal meal = mealsDAO.getById(id);
        request.setAttribute("meal", meal);
        request.getRequestDispatcher(INSERT_OR_EDIT).forward(request, response);
    }

    private void listMeals(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        List<MealWithExceed> mealsWithExceed =
                MealsUtil.getFilteredWithExceeded(mealsDAO.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
        request.setAttribute("mealsWithExceed",mealsWithExceed);
        request.getRequestDispatcher(SHOW_MEALS).forward(request, response);
    }

    private void insertMeal(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        request.getRequestDispatcher(INSERT_OR_EDIT).forward(request, response);
    }
}