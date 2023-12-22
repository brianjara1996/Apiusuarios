package com.consulta.usuarios.Controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.consulta.usuarios.Dao.UserService;
import com.consulta.usuarios.Models.User;
import com.consulta.usuarios.Models.UserDTO;
import com.consulta.usuarios.Utils.ErrorDetail;
import com.consulta.usuarios.Utils.ErrorInfo;

@RestController
public class UserController {
	
	
	@Autowired
	private UserService userService;
	
	ErrorInfo errorInfo = new ErrorInfo();
    ErrorDetail errorDetail = new ErrorDetail();
	
	
	 @PostMapping("/sign-up")
	    public ResponseEntity<Object> signUp(@RequestBody User us) { 
	        try {
	        	// Validaciones de correo electrónico y contraseña
		        if (!isValidEmail(us.getEmail())) {
		        	
		        	errorDetail.setTimestamp(new Timestamp(System.currentTimeMillis()));
			        errorDetail.setCodigo(400); // Código de error personalizado
			        errorDetail.setDetail("Error al Validar Email"); // Mensaje de error obtenido de la excepción

			        List<ErrorDetail> errorList = new ArrayList<>();
			        errorList.add(errorDetail);
			        errorInfo.setError(errorList);
		        	
		            return ResponseEntity.status(400).body(errorInfo);
		        }

		        if (!isValidPassword(us.getPassword())) {
		        		        	
		        	errorDetail.setTimestamp(new Timestamp(System.currentTimeMillis()));
			        errorDetail.setCodigo(400); // Código de error personalizado
			        errorDetail.setDetail("Error al validar contraseña"); // Mensaje de error obtenido de la excepción

			        List<ErrorDetail> errorList = new ArrayList<>();
			        errorList.add(errorDetail);
			        errorInfo.setError(errorList);
		        	
		            return ResponseEntity.status(400).body(errorInfo);
		        }
	        
		        us = userService.InsertUser(us);
		        
		        if(us != null) {
		        	
		        	UserDTO usDTO = new UserDTO();
		        	
		        	usDTO.setCreated(us.getCreated());
		        	usDTO.setId(us.getUsers_id());
		        	usDTO.setIsActive(us.getIsActive());
		        	usDTO.setLastLogin(us.getLastLogin());
		        	usDTO.setToken(us.getToken());
		        	
		        	return ResponseEntity.ok(usDTO);
		        }
		        else {
		        	
		        	errorDetail.setTimestamp(new Timestamp(System.currentTimeMillis()));
			        errorDetail.setCodigo(500); // Código de error personalizado
			        errorDetail.setDetail("No se pudo insertar nuevo usuario en la base de datos"); // Mensaje de error obtenido de la excepción

			        List<ErrorDetail> errorList = new ArrayList<>();
			        errorList.add(errorDetail);
			        errorInfo.setError(errorList);
		        	
		            return ResponseEntity.status(500).body(errorInfo);
		        } 
	        }
	        catch(Exception e) {

	        	errorDetail.setTimestamp(new Timestamp(System.currentTimeMillis()));
		        errorDetail.setCodigo(500); // Código de error personalizado
		        errorDetail.setDetail("Error interno en el servidor: " + e.getMessage()); // Mensaje de error obtenido de la excepción

		        List<ErrorDetail> errorList = new ArrayList<>();
		        errorList.add(errorDetail);
		        errorInfo.setError(errorList);
	        	
	            return ResponseEntity.status(500).body(errorInfo);
	        }
	    }
	 
	 
	 
	   @PostMapping("/login")
	   public ResponseEntity<Object> loginUser(@RequestBody User us){	   
		   try {
			   us = userService.BuscarUser(us);
			   if( us != null) {
					return ResponseEntity.ok(us);
			   }
			   
			   errorDetail.setTimestamp(new Timestamp(System.currentTimeMillis()));
		        errorDetail.setCodigo(401); // Código de error personalizado
		        errorDetail.setDetail("No se encontro usuario en la Base de datos"); // Mensaje de error obtenido de la excepción

		        List<ErrorDetail> errorList = new ArrayList<>();
		        errorList.add(errorDetail);
		        errorInfo.setError(errorList);
	        	
	            return ResponseEntity.status(401).body(errorInfo);
			   
		   }
		   catch(Exception e) {
			   
			   
			    errorDetail.setTimestamp(new Timestamp(System.currentTimeMillis()));
		        errorDetail.setCodigo(500); // Código de error personalizado
		        errorDetail.setDetail("Error interno en el servior:" + e.getMessage()); // Mensaje de error obtenido de la excepción

		        List<ErrorDetail> errorList = new ArrayList<>();
		        errorList.add(errorDetail);
		        errorInfo.setError(errorList);
	        	
	            return ResponseEntity.status(500).body(errorInfo);
			   
		   }
	   }
	   
	 
	 
	    private boolean isValidEmail(String email) {
	    	
	    	try {
	    		String EMAIL_VALIDACION = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		    	
		        Pattern pattern = Pattern.compile(EMAIL_VALIDACION);
		        Matcher matcher = pattern.matcher(email);
		        return matcher.matches();
	    	}
	    	catch(Exception e) {
	    	return false;
	    	}
	    }

	    private boolean isValidPassword(String password) {
	    	
	    	try {
	    		String PASSWORD_VALIDACION = "^(?=.*[A-Z])(?=(.*\\d){2})[a-zA-Z\\d]{8,12}$";
	    	   	
		        Pattern pattern = Pattern.compile(PASSWORD_VALIDACION);
		        Matcher matcher = pattern.matcher(password);
		        return matcher.matches();
	    	}
	    	catch(Exception e) {
	    		return false;
	    	}
	    }

}
