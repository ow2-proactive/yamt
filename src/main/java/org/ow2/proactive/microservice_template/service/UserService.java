package org.ow2.proactive.microservice_template.service;


import org.ow2.proactive.microservice_template.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service("userService")
@Transactional
public class UserService {

    private Map<String, User> users = new HashMap<>();

//    static {
//        users = new HashMap<>();
//    }

//    public UserService(Set<User> usersSet){
//        for(User user : usersSet){
//            users.put(user.getName(), user);
//        }
//    }

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
