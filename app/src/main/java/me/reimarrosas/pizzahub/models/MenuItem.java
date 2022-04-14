package me.reimarrosas.pizzahub.models;

public abstract class MenuItem {

    private String id;
    private String name;
    private String imageUrl;

    public MenuItem() {

    }

    public MenuItem(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public enum MenuItemType {
        PREMADE, TOPPING, SIDE, DRINK, SIZE
    }

}
