package br.com.silas.letusknow.utils;

import android.content.Context;
import android.util.Log;

import java.util.Properties;

public class PropertiesUtils {

    private Properties properties;

    public PropertiesUtils(Context context) {
        try {
            properties = new Properties();
            properties.load(context.getAssets().open("application.properties"));
        } catch (Exception e) {
            Log.e("LetUsKnow", "Erro ao carregar propriedades", e);
        }
    }

    public String get(String key) {
        return properties.getProperty(key, "");
    }

}
