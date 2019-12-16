package com.develop.task;
import com.develop.task.url.util.ResponseHolder;
import com.develop.task.url.util.URLQueryBuilder;

import java.util.List;
import java.util.stream.Collectors;


public class App
{
    static URLQueryBuilder getAttributeTemplate()
    {
        return new URLQueryBuilder()
                .appendQueryParam("order", "desc")
                .appendQueryParam("sort", "activity")
                .appendQueryParam("site", "stackoverflow")
                .appendQueryParam("pagesize","100");
    }

    public static void main( String[] args )
    {
        String qUsers = "https://api.stackexchange.com/2.2/users";
        String qAnswersPrototype = "https://api.stackexchange.com/2.2/users/%s/answers";
        String qQuestionsPrototype = "https://api.stackexchange.com/2.2/users/%s/questions";
        String qTagsPrototype = "https://api.stackexchange.com/2.2/users/%s/tags";

        URLQueryBuilder queryUsers = new URLQueryBuilder(qUsers)
                .appendQueryParam("order", "desc")
                .appendQueryParam("sort", "reputation")
                .appendQueryParam("site", "stackoverflow")
                .appendQueryParam("min", "223")
                .appendQueryParam("pagesize","100");


        ResponseHolder usersResponse = new ResponseHolder(qUsers, queryUsers);

        do{
            List<StackUser> filteredUsers = usersResponse.getPageItems().stream()
                    .filter( e -> e.has("location"))
                    .filter( e -> e.get("location").toString().toLowerCase().contains("romania")
                            || e.get("location").toString().toLowerCase().contains("moldova")
                    ).map(e -> new StackUser(e))
                    .collect(Collectors.toList());

            filteredUsers.forEach(e -> {
                ResponseHolder questionResponse = new ResponseHolder(String.format(qQuestionsPrototype, e.getUserID()), getAttributeTemplate());
                e.setAnswers(questionResponse.getItemsCount());

                ResponseHolder answersResponse = new ResponseHolder(String.format(qAnswersPrototype, e.getUserID()), getAttributeTemplate());
                e.setQuestion(answersResponse.getItemsCount());

                ResponseHolder tagsResponse = new ResponseHolder(String.format(qTagsPrototype, e.getUserID()), getAttributeTemplate());
                tagsResponse.getSetOfElements(e.getTags(),"name");
            });

            filteredUsers.forEach(System.out::println);

            filteredUsers.stream()
                .filter(e -> e.getAnswers() > 0 )
                .filter(
                    e -> e.getTags().contains("java")
                        || e.getTags().contains(".net")
                        || e.getTags().contains("docker")
                        || e.getTags().contains("c#")
                ).forEach(System.out::println);

            usersResponse.incrementPage();
        } while (usersResponse.getHasMore());
    }
}
