package com.project.app.ws.io.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity(name="category")
public class CategoryEntity implements Serializable {
    private static final long serialVersionID = 1L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String categoryId;

    @Column(nullable = false)
    private String name;
}
