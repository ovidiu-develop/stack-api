package com.develop.task;

import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class StackUser {
    private String userID;
    private String username;
    private String location;
    private String linkProfile;
    private String linkAvatar;
    private int question = 0;
    private int answers = 0;
    private Set<String> tags;

    public StackUser(JSONObject object)
    {
        setUserID(object.get("user_id").toString());
        setUsername(object.getString("display_name"));
        setLocation(object.getString("location"));
        setLinkAvatar(object.getString("profile_image"));
        setLinkProfile(object.getString("link"));
        tags = new HashSet<>();
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLinkProfile() {
        return linkProfile;
    }

    public void setLinkProfile(String linkProfile) {
        this.linkProfile = linkProfile;
    }

    public String getLinkAvatar() {
        return linkAvatar;
    }

    public void setLinkAvatar(String linkAvatar) {
        this.linkAvatar = linkAvatar;
    }

    public int getQuestion() {
        return question;
    }

    public void setQuestion(int question) {
        this.question = question;
    }

    public int getAnswers() {
        return answers;
    }

    public void setAnswers(int answers) {
        this.answers = answers;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString()
    {
        return username + " " + location + " " + answers + " " + question + " " + linkProfile + " " + linkAvatar + " " + tags;
    }
}
