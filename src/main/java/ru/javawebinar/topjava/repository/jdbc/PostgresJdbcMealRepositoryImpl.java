package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

@Profile("postgres")
@Repository
public class PostgresJdbcMealRepositoryImpl extends AbstractJdbcMealRepository {

    @Autowired
    public PostgresJdbcMealRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(dataSource,jdbcTemplate,namedParameterJdbcTemplate);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query(
                "SELECT * FROM meals WHERE user_id=?  AND date_time BETWEEN  ? AND ? ORDER BY date_time DESC",
                ROW_MAPPER, userId, startDate, endDate);
    }

    protected <T> T getDBCompatibleDateTime(Meal meal){
        return (T)meal.getDateTime();
    }

}
