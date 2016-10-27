package ru.sbt.student;

import ru.sbt.dbworker.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yaroslav on 14.10.16.
 */
public class StudentDaoImpl implements StudentDao {

    private final DBConnection connection;

    public StudentDaoImpl(DBConnection connection) {
        this.connection = connection;
    }

    @Override
    public void saveStudent(String name, String surname) throws SQLException {
        Connection conn = connection.getConnection();
        conn.setAutoCommit(false);
        PreparedStatement statement = conn.prepareStatement("INSERT INTO " +
                "STUDENTS (STUDENT_NAME, STUDENT_SURNAME) VALUES (?, ?);");
        statement.setString(1, name);
        statement.setString(2, surname);
        statement.execute();
        conn.commit();
    }

    @Override
    public void changeStudentName(long id, String oldName, String newName) throws SQLException {
        connection.getConnection().createStatement().execute("UPDATE STUDENTS SET " +
                "STUDENT_NAME ='" + newName + "' " +
                "WHERE STUDENT_ID = '" + id + "';");
    }

    @Override
    public void changeStudentName(String oldName, String surname, String newName) throws SQLException {
        connection.getConnection().createStatement().execute("UPDATE STUDENTS SET " +
                "STUDENT_NAME ='" + newName + "' " +
                "WHERE STUDENT_NAME = '" + oldName + "' " +
                "AND STUDENT_SURNAME = '" + surname + "';");
    }

    @Override
    public void changeStudentSurname(long studentId, String oldSurname, String newSurname) throws SQLException {
        connection.getConnection().createStatement().execute("UPDATE STUDENTS SET " +
                "STUDENT_SURNAME ='" + newSurname + "' " +
                "WHERE STUDENT_ID = '" + studentId + "';");
    }

    @Override
    public void changeStudentSurname(String name, String oldSurname, String newSurname) throws SQLException {
        connection.getConnection().createStatement().execute("UPDATE STUDENTS SET " +
                "STUDENT_SURNAME ='" + newSurname + "' " +
                "WHERE STUDENT_NAME = '" + name + "' " +
                "AND STUDENT_SURNAME = '" + oldSurname + "';");
    }

    @Override
    public void deleteStudent(long studentId) throws SQLException {
        connection.getConnection().createStatement().execute("DELETE FROM STUDENTS" +
                " WHERE STUDENT_ID = '" + studentId + "';");
    }

    @Override
    public void deleteStudent(String name, String surname) throws SQLException {
        connection.getConnection().createStatement().execute("DELETE FROM STUDENTS" +
                " WHERE STUDENT_NAME = '" + name + "' " +
                "AND STUDENT_SURNAME = '" + surname + "';");
    }

    @Override
    public long getStudentId(String name, String surname) throws SQLException {
        ResultSet resultSet = connection.getConnection().createStatement().executeQuery("SELECT STUDENT_ID FROM " +
                "STUDENTS WHERE STUDENT_NAME = '" + name + "' AND " +
                "STUDENT_SURNAME = '" + surname + "';");
        resultSet.next();
        return resultSet.getLong(1);
    }

    @Override
    public String getStudentName(long studentId) throws SQLException {
        ResultSet resultSet = connection.getConnection().createStatement().executeQuery("SELECT STUDENT_NAME FROM " +
                "STUDENTS WHERE STUDENT_ID = '" + studentId + "';");
        resultSet.next();
        return resultSet.getString(1);
    }

    @Override
    public String getStudentSurname(long studentId) throws SQLException {
        ResultSet resultSet = connection.getConnection().createStatement().executeQuery("SELECT STUDENT_SURNAME FROM " +
                "STUDENTS WHERE STUDENT_ID = '" + studentId + "';");
        resultSet.next();
        return resultSet.getString(1);
    }

    @Override
    public List<Student> getStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        ResultSet resultSet = connection.getConnection().createStatement().executeQuery("SELECT * " +
                "FROM STUDENTS");
        while (resultSet.next()) {
            students.add(new Student(resultSet.getLong("STUDENT_ID"), resultSet.getString("STUDENT_NAME"),
                    resultSet.getString("STUDENT_SURNAME")));
        }
        return students;
    }

    @Override
    public Student getStudent(long studentId) throws SQLException {
        ResultSet resultSet = connection.getConnection().createStatement().executeQuery("SELECT * FROM " +
                "STUDENTS WHERE STUDENT_ID = '" + studentId + "';");
        if (resultSet.next() == true) {
            return new Student(studentId,
                    resultSet.getString("STUDENT_NAME"), resultSet.getString("STUDENT_SURNAME"));
        }
        throw new SQLException("There aren't such element");
    }

    @Override
    public Student getStudent(String studentName, String studentSurname) throws SQLException {
        ResultSet resultSet = connection.getConnection().createStatement().executeQuery("SELECT * FROM " +
                "STUDENTS WHERE STUDENT_NAME = '" + studentName + "' " +
                "AND STUDENT_SURNAME = '" + studentSurname + "';");
        if (resultSet.next() == true) {
            return new Student(resultSet.getLong("STUDENT_ID"), studentName, studentSurname);
        }
        throw new SQLException("There aren't such element");
    }
}
