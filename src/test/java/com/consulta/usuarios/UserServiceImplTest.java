package com.consulta.usuarios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.consulta.usuarios.Dao.UserServiceImpl;
import com.consulta.usuarios.Models.User;

public class UserServiceImplTest {
	
    private UserServiceImpl userService;
    private EntityManager entityManager;

    @BeforeEach
    public void setup() {
        entityManager = mock(EntityManager.class);
        userService = new UserServiceImpl();
    }

    @Test
    public void testInsertUser() {
        // Mock User
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        // Mock simula el comportamiento de entityManager
        String email = user.getEmail();
        when(entityManager.createQuery(toString())).thenReturn(mock(Query.class));
        when(entityManager.createQuery("From User WHERE email ='" + email + "'")).thenReturn(mock(Query.class));
        when(entityManager.createQuery("Update User set token ='" + toString() + "' where email ='" + email + "'")).thenReturn(mock(Query.class));
        when(entityManager.createQuery("From User WHERE email ='" + email + "'")).thenReturn(mock(Query.class));
        when(entityManager.createQuery("From User WHERE email ='" + email + "'")).thenReturn(mock(Query.class));
        when(entityManager.merge(user)).thenReturn(user);

        // llamo al metodo insertedUser
        User insertedUser = userService.InsertUser(user);

        // Verifico que el usuario se inserto correctamente
        assertNotNull(insertedUser);
        assertEquals("test@example.com", insertedUser.getEmail());
        
    }
    
    
    @Test
    public void testBuscarUser() {
        // Mock User
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setToken("valid_token");

        // Mock simula el comportamiento de entityManager
        String email = user.getEmail();
        when(entityManager.createQuery(toString())).thenReturn(mock(Query.class));
        when(entityManager.createQuery("From User Where email ='" + email + "' and token='" + user.getToken() + "'")).thenReturn(mock(Query.class));
        when(entityManager.createQuery("Update User set token ='" + toString() + "' where email ='" + email + "'")).thenReturn(mock(Query.class));
        when(entityManager.createQuery("From User WHERE email ='" + email + "'")).thenReturn(mock(Query.class));
        when(entityManager.createQuery("From User WHERE email ='" + email + "'")).thenReturn(mock(Query.class));
        when(entityManager.createQuery("From User Where email ='" + email + "' and token='" + user.getToken() + "'")).thenReturn(mock(Query.class));
        when(entityManager.createQuery("From User Where email ='" + email + "' and token='" + user.getToken() + "'")).thenReturn(mock(Query.class));
        when(entityManager.createQuery("From User Where email ='" + email + "' and token='" + user.getToken() + "'")).thenReturn(mock(Query.class));
        when(entityManager.createQuery("Update User set token ='" + toString() + "' where email ='" + email + "'")).thenReturn(mock(Query.class));
        when(entityManager.createQuery("Update User set token ='" + toString() + "' where email ='" + email + "'")).thenReturn(mock(Query.class));

        // llamo al metodo BuscarUser
        User finddUser = userService.BuscarUser(user);

        // Verifico que el usuario se inserto correctamente
        assertNotNull(finddUser);
        assertEquals("test@example.com", finddUser.getEmail());
        assertEquals("valid_token", finddUser.getToken());
    }



}
