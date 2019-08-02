package com.firebasespring.pe.FirebaseSpring.api.models;

public class Gallery {
    private  String file_id;
    private String url;

    public  Gallery(){

    }

    public Gallery(String file_id, String url) {
        this.file_id = file_id;
        this.url = url;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Gallery{" +
                "file_id='" + file_id + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
