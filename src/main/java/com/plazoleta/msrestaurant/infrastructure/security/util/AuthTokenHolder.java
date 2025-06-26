// Ruta: infrastructure.security.util.AuthTokenHolder
package com.plazoleta.msrestaurant.infrastructure.security.util;

public class AuthTokenHolder {
    private static final ThreadLocal<String> tokenHolder = new ThreadLocal<>();

    public static void setToken(String token) {
        tokenHolder.set(token);
    }

    public static String getToken() {
        return tokenHolder.get();
    }

    public static void clear() {
        tokenHolder.remove();
    }
}
