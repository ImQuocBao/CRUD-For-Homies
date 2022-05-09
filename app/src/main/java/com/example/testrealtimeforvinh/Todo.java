package com.example.testrealtimeforvinh;

public class Todo {
    int id;
    String title;
    int status;

    public Todo() {
    }

    public Todo(String title, int status) {
        this.title = title;
        this.status = status;
    }

    public Todo(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
