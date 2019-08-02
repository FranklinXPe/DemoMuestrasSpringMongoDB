package com.firebasespring.pe.FirebaseSpring.api.controllers;


import com.firebasespring.pe.FirebaseSpring.api.service.TokenService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")

public class AuthTokenController {

    @Autowired
    TokenService service;


    @RequestMapping(method = RequestMethod.GET, value = "/validate")
    public ResponseEntity<Map> verificarToken(@RequestHeader(value="ID-TOKEN") String idToken) throws Exception {

        System.out.println("Esto es el Header de ID-TOKEN:"+idToken);
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        String uid = decodedToken.getUid();

        String uId=service.getUserIdFromIdToken(idToken);
        Map<String,Object> results=new HashMap<>();
        results.put("uID",uId);

        System.out.println("Esto es la validacion de Firebase:"+uId);

        return ResponseEntity.ok(results);
    }
}
