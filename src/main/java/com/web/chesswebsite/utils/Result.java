package com.web.chesswebsite.utils;

import com.web.chesswebsite.enums.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Result extends HashMap<String, Object> {
    private final List<String> errors = new ArrayList<>();

    public void error(String error) {
        errors.add(error);
        this.put("errors", errors);
    }
    public void status(Status status){
        this.put("status",status);
    }

}
