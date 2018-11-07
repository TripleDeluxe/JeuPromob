package com.iot.jeupromob.util;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class AsyncHTTPGetJSON extends AsyncTask {
    private String stringURL = null;

    public AsyncHTTPGetJSON(String url){
        stringURL = url;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try{
            URL url = new URL(stringURL);
            URLConnection conn = (URLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String result = ReadStream(in);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    private String ReadStream(InputStream in) throws IOException{
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in), 1000);
        for(String line = reader.readLine(); line != null; line = reader.readLine()){
            sb.append(line);
        }
        in.close();

        return sb.toString();
    }
}

