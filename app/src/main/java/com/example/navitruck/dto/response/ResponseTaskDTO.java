package com.example.navitruck.dto.response;

import java.util.List;

public class ResponseTaskDTO<T> {
    private boolean success;
    private T content;
    private long count;
    private List<String> errors;


    public boolean isSuccess() {
        return success;
    }

    public T getContent() {
        return content;
    }

    public long getCount() {
        return count;
    }

    public List<String> getErrors() {
        return errors;
    }
}