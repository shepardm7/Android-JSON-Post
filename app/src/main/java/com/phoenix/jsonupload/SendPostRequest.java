package com.phoenix.jsonupload;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Phoenix on 26-Feb-18.
 */

public class SendPostRequest extends AsyncTask<Void, Void, String> {

    @Override
    protected String doInBackground(Void... voids) {

        try {
            URL url = new URL("http://192.168.2.28:3000/users");
            JSONObject postDataParams = new JSONObject();
            //postDataParams.put("id", 5);
            postDataParams.put("name", MainActivity.name);
            postDataParams.put("contact", MainActivity.contact);
            postDataParams.put("age", MainActivity.age);
            postDataParams.put("email", MainActivity.email);


            Log.d("params", postDataParams.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(60000);
            conn.setConnectTimeout(60000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_CREATED) {
                return "Data uploaded";
            } else {
                return new String("Error: " + responseCode);
            }

//            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
//            //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
//            os.writeBytes(data);
//
//            os.flush();
//            os.close();
//
//            conn.disconnect();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        Toast.makeText(MainActivity.mainContext, s, Toast.LENGTH_SHORT).show();
    }

    public String getPostDataString(JSONObject params) throws Exception {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator<String> itr = params.keys();
        while(itr.hasNext()) {
            String key = itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }
        return result.toString();
    }
}
