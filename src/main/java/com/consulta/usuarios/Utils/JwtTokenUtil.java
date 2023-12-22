package com.consulta.usuarios.Utils;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtTokenUtil {
	

    private static final String SECRET_KEY = "ghk45jgherogho834go3h4g";

    public static String generarTokenJWT(String mail) {
    	
    	SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;
    	
    	SecretKey key = Keys.secretKeyFor(algorithm);
        //Key key = Keys.hmacShaKeyFor(algorithm);

        // Configuracion de  las claims del token
        return Jwts.builder()
                .setSubject(mail) // email como sujeto del token
                .setIssuedAt(new Date()) //Decha de emisión del token
                .setExpiration(getExpirationDate()) //Fecha de caducidad del token
                .signWith(key) //Firma del token con la clave secreta
                .compact();
    }

    private static Date getExpirationDate() {
        // se establece la fecha de caducidad del token
        long tiempoDeExpiracionEnMilisegundos = 3600000; // 1 hora en milisegundos
        return new Date(System.currentTimeMillis() + tiempoDeExpiracionEnMilisegundos);
    }

}
