package com.consulta.usuarios.Dao;

import com.consulta.usuarios.Models.User;

public interface UserService {
	
	User InsertUser(User us);
	
	User BuscarUser(User us);
}
