package com.massadas.godot_eskills;

import org.godotengine.godot.Dictionary;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RoomApi {
    private String authorization;
    private static final String BASE_URL = "https://api.eskills.catappult.io/room/";
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    final OkHttpClient client = new OkHttpClient();

    public void setAuthorization(String session) {
        authorization = session;
    }

    public Dictionary patchRoom(int score, String status) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("score", score);
            jsonObject.put("status", status);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(jsonObject.toString(), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL)
                .header("Authorization", "Bearer " + authorization)
                .post(body)
                .build();

        Dictionary Jobject = null;
        try {
            Response response = client.newCall(request).execute();
            Jobject = jsonString2Dictionary(response.body().string());
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return Jobject;
    }

    public Dictionary getRoom() {
        Request request = new Request.Builder()
                .url(BASE_URL)
                .header("Authorization", "Bearer " + authorization)
                .get()
                .build();

        Dictionary Jobject = null;
        try {
            Response response = client.newCall(request).execute();
            Jobject = jsonString2Dictionary(response.body().string());
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return Jobject;
    }


    private static Dictionary jsonString2Dictionary(String jsonString) throws org.json.JSONException {
        Dictionary keys = new Dictionary();

        org.json.JSONObject jsonObject = new org.json.JSONObject(jsonString); // HashMap
        java.util.Iterator<?> keyset = jsonObject.keys(); // HM

        while (keyset.hasNext()) {
            String key = (String) keyset.next();
            Object value = jsonObject.get(key);
            if (value instanceof org.json.JSONObject) {
                keys.put(key, jsonString2Dictionary(value.toString()));
            } else if (value instanceof org.json.JSONArray) {
                org.json.JSONArray jsonArray = jsonObject.getJSONArray(key);
                //JSONArray jsonArray = new JSONArray(value.toString());
                keys.put(key, jsonArray2List(jsonArray));
            } else {
                keyNode(value);
                keys.put(key, value);
            }
        }
        return keys;
    }

    public static Object[] jsonArray2List(org.json.JSONArray arrayOFKeys) throws org.json.JSONException {
        Object[] array2List = new Object[arrayOFKeys.length()];

        for (int i = 0; i < arrayOFKeys.length(); i++) {
            if (arrayOFKeys.opt(i) instanceof org.json.JSONObject) {
                Dictionary subObj2Map = jsonString2Dictionary(arrayOFKeys.opt(i).toString());
                array2List[i] = subObj2Map;
            } else if (arrayOFKeys.opt(i) instanceof org.json.JSONArray) {
                Object[] subarray2List = jsonArray2List((org.json.JSONArray) arrayOFKeys.opt(i));
                array2List[i] = subarray2List;
            } else {
                keyNode(arrayOFKeys.opt(i));
                array2List[i] = arrayOFKeys.opt(i);
            }
        }
        return array2List;
    }

    public static Object keyNode(Object o) {
        if (o instanceof String || o instanceof Character) return (String) o;
        else if (o instanceof Number) return (Number) o;
        else return o;
    }
}