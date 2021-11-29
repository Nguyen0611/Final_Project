package fpt.toeic.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Iterator;


/**
 *
 * support convert string Json to class
 */
public class JsonUtils {

    public static final String STRING_KEY = "String";

    public static final String JSONOBJECT_KEY = "JSONObject";

    public static final String JSONARRAY_KEY = "JSONArray";

    /**
     * Constructor
     */
    private JsonUtils() {
        throw new IllegalAccessError("JsonUtils class");
    }


    public static JSONObject transferJsonKey(JSONObject jsonObject, boolean isLowerCase) {
        JSONObject object = new JSONObject();
        Iterator<?> iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            String jsonKey = (String) iterator.next();
            Object valueObject = jsonObject.get(jsonKey);
//            if (isLowerCase) {
//                jsonKey = jsonKey.toLowerCase();
//            } else {
//                jsonKey = jsonKey.toUpperCase();
//            }
            if (valueObject.getClass().toString().endsWith(JSONOBJECT_KEY)) {
                JSONObject checkObject = new JSONObject(valueObject);
                // When the value is null, the valueObject is still a JSONObject object, the
                // null is not true, to determine whether it is nullObject
                if (checkObject != null) {
                    // object.accumulate(jsonKey, transferJsonKey((JSONObject) valueObject,
                    // isLowerCase));
                    object.put(jsonKey, transferJsonKey((JSONObject) valueObject, isLowerCase));
                }
            } else if (valueObject.getClass().toString().endsWith(JSONARRAY_KEY)) {
                // Get json array
                JSONArray array = null;
                if (jsonObject.has(jsonKey.toLowerCase())) {
                    array = jsonObject.getJSONArray(jsonKey.toLowerCase());
                } else if (jsonObject.has(jsonKey.toUpperCase())) {
                    array = jsonObject.getJSONArray(jsonKey.toUpperCase());
                } else if (jsonObject.has(jsonKey)) {
                    array = jsonObject.getJSONArray(jsonKey.toUpperCase());
                }
                JSONArray arrayTranfer = transferJsonArray(array, isLowerCase);

                // object.accumulate(jsonKey, arrayTranfer);
                object.put(jsonKey, arrayTranfer);
            } else {
                object.put(jsonKey, valueObject);
                // object.accumulate(jsonKey, valueObject);
            }
        }
        return object;
    }

    /**
     * JSONArray key case conversion
     *
     */
    public static JSONArray transferJsonArray(JSONArray jsonArray, boolean transferMode) {
        JSONArray array = new JSONArray();
        if (null != jsonArray && jsonArray.length() > 0) {
            for (Object object : jsonArray) {
                if (object.getClass().toString().endsWith(JSONOBJECT_KEY)) {
                    array.put(transferJsonKey((JSONObject) object, transferMode));
                } else if (object.getClass().toString().endsWith(JSONARRAY_KEY)) {
                    array.put(transferJsonArray((JSONArray) object, transferMode));
                }
            }
        }
        return array;
    }

    public static Object getIgnoreCase(JSONObject jobj, String key) {

        Iterator<String> iter = jobj.keySet().iterator();
        while (iter.hasNext()) {
            String key1 = iter.next();
            if (key1.equalsIgnoreCase(key)) {
                return jobj.get(key1);
            }
        }
        return null;
    }

    public static JSONObject objectToJSONObject(Object object) {
        Object json = null;
        JSONObject jsonObject = null;
        try {
            json = new JSONTokener(object.toString()).nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (json instanceof JSONObject) {
            jsonObject = (JSONObject) json;
        }
        return jsonObject;
    }

    public static JSONArray objectToJSONArray(Object object) {
        Object json = null;
        JSONArray jsonArray = null;
        try {
            json = new JSONTokener(object.toString()).nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (json instanceof JSONArray) {
            jsonArray = (JSONArray) json;
        }
        return jsonArray;
    }

    public static String getValueFromKey(JSONObject obj, String key) {
        try {
            Iterator<String> iter = obj.keys();
            while (iter.hasNext()) {
                String key1 = iter.next();
                if (key1.equalsIgnoreCase(key)) {
                    return (String) obj.get(key1);
                }
            }
        } catch (Exception e) {
            return "";
        }
        return "";
    }
}

