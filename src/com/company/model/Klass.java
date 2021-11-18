package com.company.model;

import java.util.*;

public class Klass {
    public String name;
    private final List<Student> students; //private final
    public Map<Integer, List<GregorianCalendar>> schedule;
    public int klassId;

    public Klass(String name, List<Student> students, Map<Integer, List<GregorianCalendar>> schedule,
                 int klassId) {
        this.name = name;
        this.students = students;
        this.schedule = schedule;
        this.klassId = klassId;
    }

    public Klass(String name, int klassId) {
//        this(name, Collections.emptyList(), Collections.emptyMap(), klassId);
        this(name, new ArrayList<>(), new HashMap<>(), klassId);
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

    public List<Student> getStudents() {
        return this.students;
    }

    public void removeStudent(Student student) {
        this.students.remove(student);
    }

}
