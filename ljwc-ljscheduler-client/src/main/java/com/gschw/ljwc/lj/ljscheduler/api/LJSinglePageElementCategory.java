package com.gschw.ljwc.lj.ljscheduler.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hadoop on 8/21/15.
 *
 * Thanks http://stackoverflow.com/questions/12468764/jackson-enum-serializing-and-deserializer
 */
public enum LJSinglePageElementCategory {
    UNKNOWN,
    IMAGE;

    private static Map<String, LJSinglePageElementCategory> names2ValueMap =
            new HashMap<>();

    static {
        names2ValueMap.put("unknown", UNKNOWN);
        names2ValueMap.put("image", IMAGE);
    }

    @JsonCreator
    public static LJSinglePageElementCategory fromStringValue(String value) {
        return names2ValueMap.get(value);
    }

    @JsonValue
    public String toStringValue() {
        for (Map.Entry<String, LJSinglePageElementCategory> entry : names2ValueMap.entrySet()) {
            if (entry.getValue() == this)
                return entry.getKey();
        }

        return null;
    }
}
