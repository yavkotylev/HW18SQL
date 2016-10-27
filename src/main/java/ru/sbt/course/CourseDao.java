package ru.sbt.course;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Yaroslav on 15.10.16.
 */
public interface CourseDao {
    void saveCourse(String name) throws SQLException;

    void deleteCourse(String name) throws SQLException;

    void deleteCourse(long id) throws SQLException;

    void deleteCourse(Course course) throws SQLException;

    void changeName(String oldName, String newName) throws SQLException;

    void changeName(long id, String newName) throws SQLException;

    void changeName(Course course, String newName) throws SQLException;

    long getCourseId(String courseName) throws SQLException;

    String getCourseName(long courseId) throws SQLException;

    List<Course> getCourses() throws SQLException;

    Course getCourse(long courseId) throws SQLException;

    Course getCourse(String courseName) throws SQLException;
}
