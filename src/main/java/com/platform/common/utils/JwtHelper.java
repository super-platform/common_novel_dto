//package com.platform.common.utils;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import com.auth0.jwt.interfaces.Claim;
//
//public class JwtHelper {
//    public static String getUsername(String bearerToken){
//        DecodedJWT jwt = JWT.decode(getJWTToken(bearerToken));
//        Claim emailClaim = jwt.getClaims().get("preferred_username");
//        return emailClaim != null ? emailClaim.asString() : null;
//    }
//
//    public static String getJWTToken(String bearerToken){
//        String jwtToken = bearerToken.replace("Bearer ","");
//        return jwtToken;
//    }
//}
