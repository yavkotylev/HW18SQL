package ru.sbt.scheduleStat;

import ru.sbt.course.Course;
import ru.sbt.lesson.Lesson;
import ru.sbt.student.Student;

import java.sql.SQLException;
import java.util.Map;

/**
 * Created by Yaroslav on 23.10.16.
 */
public interface StatisticDao {
    Map<Course, Long> getMostAttendanceCourse() throws SQLException;

    Map<Lesson, Long> getMostAttendanceLesson() throws SQLException;

    Map<Course, Long> getLeastAttendanceCourse() throws SQLException;

    Map<Lesson, Long> getLeastAttendanceLesson() throws SQLException;

    Map<Student, Long> getMostDiligentStudent() throws SQLException;

    Map<Student, Long> getLeastDiligentStudent() throws SQLException;
}
