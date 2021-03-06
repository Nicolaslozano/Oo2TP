package com.unla.grupo_2_oo2_2020.services;

import com.unla.grupo_2_oo2_2020.entities.User;

public interface IUserService {

    User findByUsername(String username);

    User findByDni(int dni);

    void save(User user);

    boolean hasRole(String username, String role);
}