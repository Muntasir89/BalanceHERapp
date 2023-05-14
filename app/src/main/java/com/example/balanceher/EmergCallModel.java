package com.example.balanceher;


public class EmergCallModel {
    Integer imgId;
    String name, designation, workPlace, contact;

    public EmergCallModel(Integer id, String name, String designation, String workPlace, String contact) {
        this.imgId = id;
        this.name = name;
        this.designation = designation;
        this.workPlace = workPlace;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Integer getImgId() {
        return imgId;
    }

    public void setImgId(Integer imgId) {
        this.imgId = imgId;
    }
}
