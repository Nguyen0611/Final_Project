package fpt.toeic.utils;

import java.util.HashMap;
import java.util.Map;

public class MappingColumn {

    private MappingColumn(){}

    static Map<String, String> map;
    //<keyinentity, keyinjsonobject>
    static {
        map = new HashMap<>();
    }

    public static String getKeyMap(String key) {
        return map.get(key);
    }
}
