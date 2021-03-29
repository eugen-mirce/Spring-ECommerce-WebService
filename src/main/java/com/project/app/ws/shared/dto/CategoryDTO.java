package com.project.app.ws.shared.dto;

import java.io.Serializable;

public class CategoryDTO implements Serializable {
    private static final long serialVersionId = 1L;

    private long id;
    private String name;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
