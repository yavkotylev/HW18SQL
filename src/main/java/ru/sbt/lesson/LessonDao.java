package ru.sbt.lesson;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

/**
 * Created by Yaroslav on 15.10.16.
 */
public interface LessonDao {
    void saveLesson(long courseId, Time startTime, Time endTime, Date date) throws SQLException;

    long getId(long courseId, Time startTime, Time endTime, Date date) throws SQLException;

    long getCourseId(long lessonId) throws SQLException;

    Lesson getLesson(long lessonId) throws SQLException;

    void changeStartTime(long lessonId, Time newStartTime) throws SQLException;

    void changeEndTime(long lessonId, Time newEndTime) throws SQLException;

    void changeLesson(long lessonId, Date newDate) throws SQLException;

    void changeCourse(long lessonId, long newCourseId) throws SQLException;

    void deleteLesson(long lessonId) throws SQLException;

    void deleteLessons(long courseId) throws SQLException;

    void deleteLessons(Date date) throws SQLException;

    void deleteLessons(Time startTime, Time endTime) throws SQLException;

    List<Lesson> getLessons() throws SQLException;
}
