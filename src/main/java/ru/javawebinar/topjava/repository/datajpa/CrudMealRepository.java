package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Override
    @Transactional
    Meal save(Meal meal);

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId")
     int delete(@Param("id") int id, @Param("userId") int userId);

    Optional<Meal> findById(Integer id);

    /*List<Meal> findAllByUserId(int userId, Sort sort);

    List<Meal> findAllByDateTimeBetweenAndUserId(LocalDateTime startDateTime, LocalDateTime endDateTime,
                                                 int userID, Sort sort);*/

    List<Meal> findAllByUserIdOrderByDateTimeDesc(int userId);

    List<Meal> findAllByDateTimeBetweenAndUserIdOrderByDateTimeDesc(LocalDateTime startDateTime,
                                                                    LocalDateTime endDateTime, int userID);
}
