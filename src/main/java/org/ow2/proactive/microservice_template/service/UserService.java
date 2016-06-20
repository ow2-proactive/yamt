package org.ow2.proactive.microservice_template.service;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.ow2.proactive.microservice_template.model.User;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService {

    private Map<String, User> users = new HashMap<>();

    public Collection<User> findAllUsers() {
        return users.values();
    }

    public Optional<User> findByName(String name) {
        return Optional.ofNullable(users.get(name));
    }

    public void saveUser(User user) {
        users.put(user.getName(), user);
    }

    public void updateUser(User user) {
        users.put(user.getName(), user);
    }

    public void deleteUserByName(String name) {
        users.remove(name);
    }

    public void deleteAllUsers() {
        users.clear();
    }

}
