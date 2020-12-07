package com.juancoche.mydogv3.Model;

import java.util.HashMap;
import java.util.Map;

public enum Gender {
    MACHO(1L),
    HEMBRA(2L);

    private Long value;
    private static Map map = new HashMap<>();

    private Gender(Long value) {
        this.value = value;
    }

    static {
        for (Gender gender : Gender.values()) {
            map.put(gender.value, gender);
        }
    }

    public static Gender valueOf(int gender) {
        return (Gender) map.get(gender);
    }

    public Long getValue() {
        return value;
    }
}
