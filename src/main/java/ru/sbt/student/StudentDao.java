package ru.sbt.student;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Yaroslav on 14.10.16.
 */
public interface StudentDao {
    void saveStudent(String name, String surname) throws SQLException;

    void changeStudentName(long id, String oldName, String newName) throws SQLException;

    void changeStudentName(String oldName, String surname, String newName) throws SQLException;

    void changeStudentSurname(long studentId, String oldSurname, String newSurname) throws SQLException;

    void changeStudentSurname(String name, String oldSurname, String newSurname) throws SQLException;

    void deleteStudent(long studentId) throws SQLException;

    void deleteStudent(String name, String surname) throws SQLException;

    long getStudentId(String name, String surname) throws SQLException;

    String getStudentName(long studentId) throws SQLException;

    String getStudentSurname(long studentId) throws SQLException;

    List<Student> getStudents() throws SQLException;

    Student getStudent(long studentId) throws SQLException;

    Student getStudent(String studentName, String studentSurname) throws SQLException;
}
