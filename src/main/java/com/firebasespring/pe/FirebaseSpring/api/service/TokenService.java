package com.firebasespring.pe.FirebaseSpring.api.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import org.springframework.util.StringUtils;

@Service
public class TokenService {

    public String getUserIdFromIdToken(String firebaseToken) throws Exception {

        if (StringUtils.isEmpty(firebaseToken)) {
            throw new IllegalArgumentException("FirebaseTokenBlank");
        }


        String uid = null;
        try {
            //uid = FirebaseAuth.getInstance().verifyIdTokenAsync(idToken).get().getUid();
            FirebaseToken token = FirebaseAuth.getInstance().verifyIdToken(firebaseToken);
             uid = token.getUid();

            System.out.println(token.getUid()+ "  --   "+token.getEmail());


        } catch (FirebaseAuthException  e) {
            throw new Exception("User Not Authenticated");
        }
        return uid;
    }

}
