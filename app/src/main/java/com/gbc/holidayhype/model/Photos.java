package com.gbc.holidayhype.model;


public class Photos {
    String title;
    String count;
    String photo;
    String src;
    String nid;

    public Photos(String title, String count, String photo, String src, String nid) {
        this.title = title;
        this.count = count;
        this.photo = photo;
        this.src = src;
        this.nid = nid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }
}
