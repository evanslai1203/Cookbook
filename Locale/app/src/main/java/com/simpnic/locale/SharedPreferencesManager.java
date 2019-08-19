package com.simpnic.locale;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SharedPreferencesManager {
    private static SharedPreferencesManager m_instance;

    private final String SP_FILE_NAME = "%s_app_config";

    private SharedPreferences m_sharePreferences;

    private static final String SP_KEY_LANGUAGE = "sp_key_language";
    private static final String SP_KEY_TEMPERATURE_UNIT = "sp_key_temperature_unit";


    private SharedPreferencesManager(Context context) {
        String tag = "config_tag";
        final String fileName = String.format(Locale.ENGLISH, SP_FILE_NAME, tag);
        m_sharePreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    public static SharedPreferencesManager getInstance(Context context) {
        if(null == m_instance) {
            m_instance = new SharedPreferencesManager(context);
        }
        return m_instance;
    }

    public String getLanguage() {
        if(hasKey(SP_KEY_LANGUAGE)) {
            return getData(SP_KEY_LANGUAGE);
        }
        return null;
    }

    private boolean hasKey(String key) {
        return m_sharePreferences.contains(key);
    }

    private String getData(String key) {

        String data = getString(key);
        try {
            JSONObject jsonObject = new JSONObject(data);
            String encryptKey = jsonObject.getString("date");
            String encryptData = jsonObject.getString("value");
            return decrypt(encryptKey, encryptData);
        } catch (JSONException e) {

        }
        return "";
    }

    private String getString(String key) {
        return m_sharePreferences.getString(key, null);
    }

    private boolean setString(String key, String value) {
        return m_sharePreferences.edit().putString(key,value).commit();
    }

    private String decrypt(String key, String input) {
        if (TextUtils.isEmpty(input))
            return "";
        BigInteger bi_confuse = new BigInteger(key);
        try {
            BigInteger bi_r1 = new BigInteger(input, 16);
            BigInteger bi_r0 = bi_r1.xor(bi_confuse);
            return new String(bi_r0.toByteArray());
        } catch (Exception e) {
            return "";
        }
    }

    private String encrypt(String key, String input) {
        if (TextUtils.isEmpty(input))
            return "";
        BigInteger bi_passwd = new BigInteger(input.getBytes());
        BigInteger bi_r0 = new BigInteger(key);
        BigInteger bi_r1 = bi_r0.xor(bi_passwd);
        return bi_r1.toString(16);
    }

    public boolean updateLanguage(String language) {
        return saveData(SP_KEY_LANGUAGE, language);
    }

    private boolean saveData(String key, String data) {
        JSONObject jsonObject = new JSONObject();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
            String encryptKey = sdf.format(new Date());
            jsonObject.put("date", encryptKey);
            jsonObject.put("value", encrypt(encryptKey,data));
            return setString(key, jsonObject.toString());
        } catch (JSONException e) {

            return false;
        }
    }

    public String getTemperatureUnit() {
        if(hasKey(SP_KEY_TEMPERATURE_UNIT)) {
            return getData(SP_KEY_TEMPERATURE_UNIT);
        }
        return null;
    }

    public boolean updateTemperatureUnit(String unit) {
        return saveData(SP_KEY_TEMPERATURE_UNIT, unit);
    }

}
