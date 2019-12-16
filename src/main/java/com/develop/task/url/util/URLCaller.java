package com.develop.task.url.util;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.zip.GZIPInputStream;

public class URLCaller
{
    private HttpURLConnection connection;

    private BufferedReader gZipDecoded(InputStream stream) throws IOException {
        GZIPInputStream inputStream =  new GZIPInputStream(stream);
        InputStreamReader reader = new InputStreamReader(inputStream);
        return new BufferedReader(reader);
    }

    private String getStringResponse(BufferedReader reader) throws IOException {
        StringBuffer result = new StringBuffer();
        String line = "";

        while ((line = reader.readLine()) != null)
            result.append(line);

        return result.toString();
    }

    public JSONObject getResponse(URLQueryBuilder url) {
        String result = "";

        try {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            connection = (HttpURLConnection) new URL(url.getFullUrl()).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            if (connection.getResponseCode() < 299)
                result = getStringResponse(gZipDecoded(connection.getInputStream()));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new JSONObject(result);
    }
}
