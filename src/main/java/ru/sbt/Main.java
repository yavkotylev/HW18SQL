package ru.sbt;

import ru.sbt.course.Course;
import ru.sbt.dbworker.DBWorker;
import ru.sbt.dbworker.DBWorkerImpl;
import ru.sbt.lesson.Lesson;
import ru.sbt.scheduleStat.StatisticDao;
import ru.sbt.scheduleStat.StatisticDaoImpl;
import ru.sbt.student.Student;

import java.sql.SQLException;
import java.util.Map;

/**
 * Created by Yaroslav on 14.10.16.
 */
public class Main {
    public static void main(String[] args) {
        String url = "jdbc:h2:./HW18SQL";
        DBWorkerImpl dbWorker = new DBWorkerImpl(url, "sa", "");

        try {
            dbWorker.createTables();
            dbWorker.fillTables();
            StatisticDao statistic = new StatisticDaoImpl(dbWorker);

            printCourses(statistic.getMostAttendanceCourse(), "Courses with the best attendance:");
            printCourses(statistic.getLeastAttendanceCourse(), "Courses with the worst attendance:");

            printLessons(dbWorker, statistic.getMostAttendanceLesson(), "Lesson with the best attendance:");
            printLessons(dbWorker, statistic.getLeastAttendanceLesson(), "Lesson with the worst attendance:");

            printStudents(statistic.getMostDiligentStudent(), "Students with best attendance:");
            printStudents(statistic.getLeastDiligentStudent(), "Students with worst attendance:");
        } catch (SQLException e) {
            System.out.println(":*(");
        }

    }

    private static void printLessons(DBWorker dbWorker, Map<Lesson, Long> attendance, String message) throws SQLException {
        System.out.println("\n" + message);
        attendance.forEach((lesson, value) -> {
            try {
                String courseName = dbWorker.getCourseDao().getCourseName(lesson.getCourseId());
                System.out.printf("Lesson id = %d (course = %s) attendance = %d\n",
                        lesson.getId(), courseName, value);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static void printCourses(Map<Course, Long> attendance, String message) {
        System.out.println("\n" + message);
        attendance.forEach((course, value) ->
                System.out.printf("%s attendance = %d\n", course.getName(), value));
    }

    private static void printStudents(Map<Student, Long> attendance, String message) {
        System.out.println("\n" + message);
        attendance.forEach((student, value) ->
                System.out.printf("%s %s attendance = %d\n",
                        student.getName(), student.getSurname(), value));
    }
}