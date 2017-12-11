package br.com.silas.letusknow.ws;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;

public class LetUsKnowWs {

    private String rootUrl;

    private LetUsKnowWs() {
    }

    public static LetUsKnowWs criar() {
        LetUsKnowWs ws = new LetUsKnowWs();
        ws.rootUrl = "";
        return ws;
    }

    public LetUsKnowWs rootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
        return this;
    }

    public LetUsKnowWs autenticar(final String usuario, final String senha) {
        Authenticator autenticador = new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, senha.toCharArray());
            }

        };
        Authenticator.setDefault(autenticador);
        return this;
    }

    public String get(String endpoint) {
        try {
            URL url = new URL(rootUrl + endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString();
        } catch (Exception e) {
            Log.e("LetUsKnow", "Erro ao executar método GET", e);
            return "";
        }
    }

    public String post(String endpoint, Object request) {
        try {
            URL url = new URL(rootUrl + endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
            writer.writeBytes(new Gson().toJson(request));
            writer.flush();
            writer.close();
            //TODO: terminar implementação
            return "";
        } catch (Exception e) {
            Log.e("LetUsKnow", "Erro ao executar método POST", e);
            return "";
        }
    }

}
