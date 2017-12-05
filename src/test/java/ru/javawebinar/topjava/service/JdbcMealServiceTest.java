package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"jdbc","postgres"})
public class JdbcMealServiceTest extends AbstractMealServiceTest {
}
