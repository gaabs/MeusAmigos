package com.example.gaabs.meusamigos;

/**
 * Created by gaabs on 08/07/16.
 */
public class Category implements Comparable<Category>{
    private String name;
    private int color;
    private String photo;

    public Category() {}

    public Category(String name, int color, String photo) {
        this.name = name;
        this.color = color;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", color=" + color +
                ", photo='" + photo + '\'' +
                '}';
    }

    @Override
    public int compareTo(Category category) {
        return this.name.compareTo(category.getName());
    }
}
