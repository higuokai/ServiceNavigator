package com.service.navigator.utils;

public class Assert {

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(StringUtils.defaultIfEmpty(message, "Element can not be null."));
        }
    }

}
