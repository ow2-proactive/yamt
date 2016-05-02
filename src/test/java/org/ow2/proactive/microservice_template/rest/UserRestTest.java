package org.ow2.proactive.microservice_template.rest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ow2.proactive.microservice_template.fixtures.UserFixture;
import org.ow2.proactive.microservice_template.model.User;
import org.ow2.proactive.microservice_template.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by Iaroslav on 4/28/2016.
 */
public class UserRestTest {

    @InjectMocks
    private UserRest userRest;

    @Mock
    private UserService userService;

    private User user;
    private Optional<User> optUser;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        user = UserFixture.simpleUser();
        optUser = Optional.of(user);
    }

    @Test
    public void testListAllUsers(){
        Collection<User> users = new LinkedList<>();
        users.add(user);
        when(userService.findAllUsers()).thenReturn(users);

        ResponseEntity<Collection<User>> actualUsers = userRest.listAllUsers();

        assertThat(actualUsers.getStatusCode(), is(HttpStatus.OK));
        assertThat(actualUsers.getBody().size(), is(1));
        assertThat(actualUsers.getBody().iterator().next(), is(user));
        verify(userService, times(1)).findAllUsers();
    }

    @Test
    public void testGetUser(){
        String userName = user.getName();
        when(userService.findByName(userName)).thenReturn(optUser);
        ResponseEntity<User> actualUser = userRest.getUser(userName);
        assertThat(actualUser.getStatusCode(), is(HttpStatus.OK));
        assertThat(actualUser.getBody(), is(user));
        verify(userService, times(1)).findByName(user.getName());
    }

    @Test
    public void testConflictCreateUser(){
        when(userService.findByName(user.getName())).thenReturn(optUser);
        assertThat(userRest.createUser(user).getStatusCode(), is(HttpStatus.CONFLICT));
        verify(userService, times(0)).saveUser(user);
    }

    @Test
    public void testCreateUser(){
        when(userService.findByName(user.getName())).thenReturn(Optional.empty());
        assertThat(userRest.createUser(user).getStatusCode(), is(HttpStatus.CREATED));
        verify(userService, times(1)).saveUser(user);
    }

    @Test
    public void testUpdateUser(){
        when(userService.findByName(user.getName())).thenReturn(optUser);
        assertThat(userRest.updateUser(user.getName(), user).getStatusCode(), is(HttpStatus.OK));
        verify(userService, times(1)).updateUser(user);
    }

    @Test
    public void testDeleteUser(){
        when(userService.findByName(user.getName())).thenReturn(optUser);
        assertThat(userRest.deleteUser(user.getName()).getStatusCode(), is(HttpStatus.OK));
        verify(userService, times(1)).deleteUserByName(user.getName());
    }

    @Test
    public void testDeleteUserNotFound(){
        when(userService.findByName(user.getName())).thenReturn(Optional.empty());
        ResponseEntity<User> response = userRest.deleteUser(user.getName());
        assertThat(response.getBody(), is(nullValue()));
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
        verify(userService, times(0)).deleteUserByName(user.getName());
    }

    @Test
    public void deleteAllUsers(){
        ResponseEntity<User> response = userRest.deleteAllUsers();
        assertThat(response.getBody(), is(nullValue()));
        assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
        verify(userService, times(1)).deleteAllUsers();
    }
}
