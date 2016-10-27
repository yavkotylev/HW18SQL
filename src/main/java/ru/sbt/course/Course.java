package ru.sbt.course;


/**
 * Created by Yaroslav on 15.10.16.
 */
public class Course {
    private final String name;
    private final long id;

    public Course(String name, long id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj == this) return true;
        if (!(obj instanceof Course)) return false;
        Course course = (Course) obj;
        return (course.getName().equals(name) && course.getId() == id);
    }

    @Override
    public int hashCode() {
        return (int) id;
    }
}
