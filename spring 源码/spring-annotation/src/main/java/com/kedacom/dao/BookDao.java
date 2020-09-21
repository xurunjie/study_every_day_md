package com.kedacom.dao;

import org.springframework.stereotype.Repository;

@Repository
public class BookDao {
    private String label = "1";

    public String getLabel() {
        return label;
    }

    public BookDao setLabel(String label) {
        this.label = label;
        return this;
    }

    @Override
    public String toString() {
        return "BookDao{" +
                "label='" + label + '\'' +
                '}';
    }
}
