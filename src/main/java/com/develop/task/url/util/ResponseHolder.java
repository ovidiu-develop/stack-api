package com.develop.task.url.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ResponseHolder {
    private int pageNumber;
    private URLQueryBuilder query;
    private String mainUrl;

    private JSONObject obj = null;

    private boolean hasMore;

    public ResponseHolder(String mainUrl, URLQueryBuilder query)
    {
        this.mainUrl = mainUrl;
        this.query = query;
        this.query.setMainUrl(mainUrl);
        setPageNumber(1);
    }

    public boolean getHasMore()
    {
        return this.hasMore;
    }

    public void incrementPage()
    {
       setPageNumber(pageNumber + 1);
    }

    private void setPageNumber(int pageNumber)
    {
        this.pageNumber = pageNumber;
        query.appendQueryParam("page", Integer.toString(pageNumber));
    }

    private JSONArray getItems()
    {
        return obj.getJSONArray("items");
    }

    private void getResponse()
    {
        this.obj = new URLCaller().getResponse(query);
        hasMore = false;
        if(obj.has("has_more"))
            hasMore = (boolean)obj.get("has_more");
    }

    public int getItemsCount()
    {
        setPageNumber(1);
        int result = 0;

        do {
            getResponse();
            result += getItems().length();
            incrementPage();
        } while (hasMore);

        return result;
    }

    public void getSetOfElements(Set<String> set, String elem)
    {
        setPageNumber(1);
        do {
            getResponse();
            getItems().forEach(
                e -> {
                    JSONObject o = (JSONObject) e;
                    if(o.has(elem))
                        set.add(o.getString(elem).toLowerCase());
                }
            );
            incrementPage();
        } while (hasMore);
    }

    public List<JSONObject> getPageItems()
    {
        List<JSONObject> items = new ArrayList<>();

        getResponse();
        obj.getJSONArray("items").forEach(e -> items.add((JSONObject)e));

        return items;
    }

}
