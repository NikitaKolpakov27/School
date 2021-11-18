package com.company.model;

public class Subject {
    public String name;
    public int subjectId;

    public Subject(String name, int subjectId) {
        this.name = name;
        this.subjectId = subjectId;
    }

    public Subject() {

    }

    @Override
    public String toString() {
        return this.name;
    }
}
