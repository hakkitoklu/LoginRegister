package com.example.loginregister.Functions;

import android.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class decoder {
    public static String  dec(String ax,String bx,String cx){
        byte[] decodedPassbyte= decoderfun(ax);
        byte[] decodedSckeybyte=decoderfun(bx);
        byte[] decodedIVbyte=decoderfun(cx);
        SecretKey orgScKey= new SecretKeySpec(decodedSckeybyte,0,decodedSckeybyte.length,"AES");
        String decryptedPass= (Decrypt.decrypt(decodedPassbyte,orgScKey,decodedIVbyte));
        return decryptedPass;
    }
    public static byte[] decoderfun(String enval) {
        byte[] conVal = Base64.decode(enval,Base64.DEFAULT);
        return conVal;

    }
}
