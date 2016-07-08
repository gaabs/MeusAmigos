package com.example.gaabs.meusamigos;

/**
 * Created by gaabs on 08/07/16.
 */
public class Friend {
    private String phone;
    private String name;
    private String category;
    private String photo;

    public Friend() {}

    public Friend(String phone, String name, String category, String photo) {
        this.phone = phone;
        this.name = name;
        this.category = category;
        this.photo = photo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
