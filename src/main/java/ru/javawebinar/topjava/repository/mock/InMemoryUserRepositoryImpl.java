package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private static final Comparator<User> USER_COMPARATOR =
            Comparator.comparing(User::getName).thenComparing(User::getEmail);
    private static final List<User> userList = Arrays.asList(
            new User(null,"Antidotsrd","doshiq@wanwan.ua","qwerty", Role.ROLE_USER),
            new User(null,"Dakurobotto","jackx@tekken.jp","12345", Role.ROLE_USER),
            new User(null,"MambaTV","kermit@enough.com","mambapls", Role.ROLE_USER),
            new User(null,"Fi_nochka","moder@dreag.gg","OpieOP", Role.ROLE_USER),
            new User(null,"Whitelight_TV","mne@len.com","shanson", Role.ROLE_USER),
            new User(null,"Marakaratma","chernoe@serdce.wanwan","KeepopieOP", Role.ROLE_ADMIN, Role.ROLE_USER),
            new User(null,"Cheeezets","cheezets@cinema.ua","kek", Role.ROLE_USER),
            new User(null,"TheVK","salt@cod.kek","MeiRules", Role.ROLE_USER),
            new User(null,"TheVK","pepper@mw2.kek","MeiRules", Role.ROLE_USER)
    );
    private Map<Integer,User> userRepository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);
    {
        userList.forEach(this::save);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return userRepository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
        }
        userRepository.put(user.getId(), user);
        return user;
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return userRepository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return userRepository.values().stream()
                .sorted(USER_COMPARATOR)
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return userRepository.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst().orElse(null);
    }
}
