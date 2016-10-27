package ru.sbt.lesson;

import ru.sbt.dbworker.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yaroslav on 15.10.16.
 */
public class LessonDaoImpl implements LessonDao {
    private final DBConnection connection;

    public LessonDaoImpl(DBConnection connection) {
        this.connection = connection;
    }

    @Override
    public void saveLesson(long courseId, Time startTime,
                           Time endTime, Date date) throws SQLException {
        Connection conn = connection.getConnection();
        conn.setAutoCommit(false);
        PreparedStatement statement = conn.prepareStatement("INSERT INTO LESSONS " +
                "(COURSE_ID, START_TIME, END_TIME, LESSON_DATE) VALUES (?, ?, ?, ?);");
        statement.setLong(1, courseId);
        statement.setTime(2, startTime);
        statement.setTime(3, endTime);
        statement.setDate(4, date);
        statement.executeUpdate();
        conn.commit();
    }

    @Override
    public long getId(long courseId, Time startTime, Time endTime, Date date) throws SQLException {
        ResultSet resultSet = connection.getConnection().createStatement().executeQuery("SELECT LESSON_ID FROM " +
                "LESSONS WHERE COURSE_ID = '" + courseId + "' AND " +
                "START_TIME = '" + startTime + "' AND " +
                "END_TIME = '" + endTime + "' AND " +
                "LESSON_DATE = '" + date + "';");
        resultSet.next();
        return resultSet.getLong(1);
    }

    @Override
    public void changeStartTime(long lessonId, Time newStartTime) throws SQLException {
        connection.getConnection().createStatement().execute("UPDATE LESSONS " +
                "SET START_TIME = '" + newStartTime + "' " +
                "WHERE LESSON_ID = '" + lessonId + "';");
    }

    @Override
    public void changeEndTime(long lessonId, Time newEndTime) throws SQLException {
        connection.getConnection().createStatement().execute("UPDATE LESSONS " +
                "SET END_TIME = '" + newEndTime + "' " +
                "WHERE LESSON_ID = '" + lessonId + "';");
    }

    @Override
    public void changeLesson(long lessonId, Date newDate) throws SQLException {
        connection.getConnection().createStatement().execute("UPDATE LESSONS " +
                "SET LESSON_DATE = '" + newDate + "' " +
                "WHERE LESSON_ID = '" + lessonId + "';");
    }

    @Override
    public void changeCourse(long lessonId, long newCourseId) throws SQLException {
        connection.getConnection().createStatement().execute("UPDATE LESSONS " +
                "SET COURSE_ID = '" + newCourseId + "' " +
                "WHERE LESSON_ID = '" + lessonId + "';");
    }

    @Override
    public void deleteLesson(long lessonId) throws SQLException {
        connection.getConnection().createStatement().execute("DELETE FROM LESSONS " +
                "WHERE LESSON_ID = '" + lessonId + "';");
    }

    @Override
    public void deleteLessons(long courseId) throws SQLException {
        connection.getConnection().createStatement().execute("DELETE FROM LESSONS " +
                "WHERE COURSE_ID = '" + courseId + "';");
    }

    @Override
    public void deleteLessons(Date date) throws SQLException {
        connection.getConnection().createStatement().execute("DELETE FROM LESSONS " +
                "WHERE LESSON_DATE = '" + date + "';");
    }

    @Override
    public void deleteLessons(Time startTime, Time endTime) throws SQLException {
        connection.getConnection().createStatement().execute("DELETE FROM LESSONS " +
                "WHERE START_TIME = '" + startTime + "' " +
                "AND END_TIME = '" + endTime + "';");
    }

    @Override
    public List<Lesson> getLessons() throws SQLException {
        List<Lesson> lessons = new ArrayList<>();
        ResultSet resultSet = connection.getConnection().createStatement().executeQuery("SELECT * " +
                "FROM LESSONS");
        while (resultSet.next()) {
            lessons.add(new Lesson(resultSet.getLong("LESSON_ID"), resultSet.getLong("COURSE_ID"),
                    resultSet.getTime("START_TIME"), resultSet.getTime("END_TIME"),
                    resultSet.getDate("LESSON_DATE")));
        }
        return lessons;
    }

    @Override
    public long getCourseId(long lessonId) throws SQLException {
        ResultSet resultSet = connection.getConnection().createStatement().executeQuery("SELECT * " +
                "FROM LESSONS WHERE LESSON_ID = '" + lessonId + "';");
        resultSet.next();
        return resultSet.getLong("COURSE_ID");
    }

    @Override
    public Lesson getLesson(long lessonId) throws SQLException {
        ResultSet resultSet = connection.getConnection().createStatement().executeQuery("SELECT * " +
                "FROM LESSONS WHERE LESSON_ID = '" + lessonId + "';");
        if (resultSet.next() == true) {
            return new Lesson(lessonId, resultSet.getLong("COURSE_ID"), resultSet.getTime("START_TIME"),
                    resultSet.getTime("END_TIME"), resultSet.getDate("LESSON_DATE"));
        }
        throw new SQLException("There aren't such element");
    }
}
