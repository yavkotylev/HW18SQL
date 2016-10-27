package ru.sbt.student;


/**
 * Created by Yaroslav on 14.10.16.
 */
public class Student {
    private final long id;
    private final String name;
    private final String surname;

    public Student(long id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public String toString() {
        return "Student {id = " + id +
                ", name = " + name +
                ", last name = " + surname + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj == this || !(obj instanceof Student)) return false;
        Student student = (Student) obj;
        return (student.getId() == id && student.getName().equals(name)
                && student.getSurname().equals(surname));
    }

    @Override
    public int hashCode() {
        return (int) id;
    }
}
