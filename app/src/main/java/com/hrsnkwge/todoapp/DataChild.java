package com.hrsnkwge.todoapp;

public class DataChild{
    private String title;
    private String content;
    private Long time;
    private int id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(Long time) {
        this.time = time;
    }


    DataChild(int id,String title,String content,Long time){
        setId(id);
        setTitle(title);
        setContent(content);
        setTime(time);
    }
}