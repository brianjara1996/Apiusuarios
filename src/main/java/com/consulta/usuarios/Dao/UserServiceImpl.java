package com.consulta.usuarios.Dao;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.consulta.usuarios.Models.User;
import com.consulta.usuarios.Utils.JwtTokenUtil;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@Repository
@Transactional
public class UserServiceImpl implements UserService{
	
	@PersistenceContext
    EntityManager entityManager;
	
	JwtTokenUtil jwt = new JwtTokenUtil();

	@Override
	@Transactional
	public User InsertUser(User us) {
	try {

		//antes de insertar el usuario valido que no exista en la base de datos
		String sql = "From User WHERE email ='" + us.getEmail() + "'";
		
		List<User> listaUS = entityManager.createQuery(sql).getResultList();
		
		if(listaUS.isEmpty()) {
			//Genero un token para insertar en la base de datos
			us.setToken(jwt.generarTokenJWT(us.getEmail()));
						
			//libreria para encriptar contraseña
			Argon2 argon2 =Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
			
			String Hash = argon2.hash(1, 1024, 1, us.getPassword());
			
			us.setPassword(Hash);
			//Seteo todas las variables restante para insertar el usuario
			us.setIsActive(true);
			us.setCreated(new Timestamp(System.currentTimeMillis()));
			us.setLastLogin(new Timestamp(System.currentTimeMillis()));
			
				entityManager.merge(us);
				return us;	
		}
		else {
			return null;
		 }
		}
		catch(Exception e) {
			return null;
		}
	}

	@Override
	public User BuscarUser(User us){
		try {
			//Busco el usuario por mail y por token para validar que exista en la base de datos y que el token se haya recibido correctamente
			String sql = "From User Where email ='" + us.getEmail() + "' and token='" + us.getToken() + "'";
			
			List<User> lista = entityManager.createQuery(sql).getResultList();
			
			if(!lista.isEmpty()){
				//con la libreria argon2 se verifica que se haya recibido bien la contraseña matcheando con su hash
				Argon2 argon2 =Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
				
				if(argon2.verify(lista.get(0).getPassword(), us.getPassword()))
				{
					String Update_token =  ActualizarToken(us);
					
					if(Update_token != null) {
						//Actualizo el token y lo vuelvo a setear para devolverle el nuevo token actualizado
						lista.get(0).setToken(Update_token);
						
						return lista.get(0);
					}
				}
			}
			return null;
		}
		catch(Exception e) {
			return null;
		}

	}
	
	
	public String ActualizarToken(User us) {
		
		String token = jwt.generarTokenJWT(us.getEmail());
		
		String query = "Update User set token ='" + token + "' where email ='" + us.getEmail() + "'";
		
		int result = entityManager.createQuery(query).executeUpdate();
		
		 if(result > 0) {
      	   return token;
         }
         else {
      	   return null;
         }
		
	}
}
