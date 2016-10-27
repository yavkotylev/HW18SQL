package ru.sbt.course;

import ru.sbt.dbworker.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yaroslav on 15.10.16.
 */
public class CourseDaoImpl implements CourseDao {
    private final DBConnection connection;

    public CourseDaoImpl(DBConnection connection) {
        this.connection = connection;
    }

    @Override
    public void saveCourse(String name) throws SQLException {
        connection.getConnection().createStatement().execute("INSERT INTO COURSES (COURSE_NAME) VALUES ('" + name + "');");
    }

    @Override
    public void deleteCourse(String name) throws SQLException {
        connection.getConnection().createStatement().execute("DELETE FROM COURSES WHERE COURSE_NAME = '" + name + "';");
    }

    @Override
    public void deleteCourse(long id) throws SQLException {
        connection.getConnection().createStatement().execute("DELETE FROM COURSES WHERE COURSE_ID = '" + id + "';");
    }

    @Override
    public void deleteCourse(Course course) throws SQLException {
        connection.getConnection().createStatement().execute("DELETE FROM COURSES WHERE COURSE_ID = '" + course.getId() + "';");
    }

    @Override
    public void changeName(String oldName, String newName) throws SQLException {
        connection.getConnection().createStatement().execute("UPDATE COURSES SET COURSE_NAME = '" + newName + "' " +
                "WHERE COURSE_NAME = '" + oldName + "';");
    }

    @Override
    public void changeName(long id, String newName) throws SQLException {
        connection.getConnection().createStatement().execute("UPDATE COURSES SET COURSE_NAME = '" + newName + "' " +
                "WHERE COURSE_ID = '" + id + "';");
    }

    @Override
    public void changeName(Course course, String newName) throws SQLException {
        connection.getConnection().createStatement().execute("UPDATE COURSES SET COURSE_NAME = '" + newName + "' " +
                "WHERE COURSE_ID = '" + course.getId() + "';");
    }

    @Override
    public long getCourseId(String courseName) throws SQLException {
        ResultSet resultSet = connection.getConnection().createStatement().executeQuery("SELECT COURSE_ID FROM COURSES " +
                "WHERE COURSE_NAME = '" + courseName + "';");
        resultSet.next();
        return resultSet.getLong(1);
    }

    @Override
    public String getCourseName(long courseId) throws SQLException {
        ResultSet resultSet = connection.getConnection().createStatement().executeQuery("SELECT COURSE_NAME FROM COURSES " +
                "WHERE COURSE_ID = '" + courseId + "';");
        resultSet.next();
        return resultSet.getString(1);
    }

    @Override
    public List<Course> getCourses() throws SQLException {

        List<Course> courses = new ArrayList<>();
        ResultSet resultSet = connection.getConnection().createStatement().executeQuery("SELECT * " +
                "FROM COURSES;");
        while (resultSet.next()) {
            courses.add(new Course(resultSet.getString(2), resultSet.getLong(1)));
        }
        return courses;
    }

    @Override
    public Course getCourse(long courseId) throws SQLException {
        ResultSet resultSet = connection.getConnection().createStatement().executeQuery("SELECT * " +
                "FROM COURSES WHERE COURSE_ID = '" + courseId + "';");
        if (resultSet.next() == true) {
            return new Course(resultSet.getString("COURSE_NAME"), courseId);
        }
        throw new SQLException("There aren't such element");
    }

    @Override
    public Course getCourse(String courseName) throws SQLException {
        ResultSet resultSet = connection.getConnection().createStatement().executeQuery("SELECT * " +
                "FROM COURSES WHERE COURSE_NAME = '" + courseName + "';");
        if (resultSet.next() == true) {
            return new Course(courseName, resultSet.getLong("COURSE_ID"));
        }
        throw new SQLException("There aren't such element");
    }
}
