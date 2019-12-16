package com.develop.task.url.util;

import java.util.HashMap;
import java.util.Map;

public class URLQueryBuilder
{
    private String mainUrl;
    private HashMap<String,String> queryParams= new HashMap<>();

    public URLQueryBuilder(String mainUrl)
    {
        this.setMainUrl(mainUrl);
    }

    public URLQueryBuilder(){};

    public String getMainUrl() {
        return mainUrl;
    }

    public void setMainUrl(String mainUrl) {
        this.mainUrl = mainUrl;
    }

    public URLQueryBuilder appendQueryParam(String key , String value)
    {
        this.queryParams.put(key, value);
        return this;
    }

    public URLQueryBuilder removeQueryParam(String key)
    {
        this.queryParams.remove(key);
        return this;
    }

    public String getFullUrl()
    {
        StringBuffer url = new StringBuffer(mainUrl + "?");

        for (Map.Entry<String,String> entry : queryParams.entrySet())
            if(entry.getValue() != "")
                url.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        url.deleteCharAt(url.length()-1);

        System.out.println(url);
        return url.toString();
    }
}
