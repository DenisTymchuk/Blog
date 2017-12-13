package com.company.entity;


import java.util.List;

/**
 * Created by Oksa on 24.11.2017.
 */
public class Post {
    private long id;
    private String tittle;
    private String description;
    private User userCreator;
    private String dateOfThePost;
    private boolean published;
    private List<Category> categories;
    private String imageLink;

    public Post() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUserCreator() {
        return userCreator;
    }

    public void setUserCreator(User userCreatorId) {
        this.userCreator = userCreatorId;
    }

    public String getDateOfThePost() {
        return dateOfThePost;
    }

    public void setDateOfThePost(String dateOfThePost) {
        this.dateOfThePost = dateOfThePost;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", tittle='" + tittle + '\'' +
                ", description='" + description + '\'' +
                ", userCreator=" + userCreator +
                ", dateOfThePost='" + dateOfThePost + '\'' +
                ", published=" + published +
                ", categories=" + categories +
                '}';
    }
}
