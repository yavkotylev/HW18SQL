package ru.sbt.dbworker.tables;

import ru.sbt.course.CourseDao;
import ru.sbt.dbworker.DBConnection;
import ru.sbt.lesson.LessonDao;
import ru.sbt.student.StudentDao;
import ru.sbt.visit.VisitDao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

/**
 * Created by Yaroslav on 21.10.16.
 */
public class TableCreatorImpl implements TableCreator {
    private final DBConnection connection;

    public TableCreatorImpl(DBConnection connection) {
        this.connection = connection;
    }

    @Override
    public void createTables() throws SQLException {
        createCourseTable(connection.getConnection());
        createLessonTable(connection.getConnection());
        createStudentTable(connection.getConnection());
        createVisitTable(connection.getConnection());
    }

    @Override
    public void fillTables(CourseDao courseDao, StudentDao studentDao,
                           LessonDao lessonDao, VisitDao visitDao) throws SQLException {
        saveCourse(courseDao);
        saveStudents(studentDao);
        saveLessons(lessonDao, courseDao);
        saveVisits(visitDao, lessonDao, studentDao, courseDao);
    }

    private void createCourseTable(Connection connection) throws SQLException {
        connection.createStatement().execute("CREATE TABLE Courses(" +
                "course_id INTEGER PRIMARY KEY auto_increment," +
                "course_name VARCHAR (20) UNIQUE);");
    }

    private void createLessonTable(Connection connection) throws SQLException {
        connection.createStatement().execute("CREATE TABLE LESSONS(" +
                "lesson_id INTEGER PRIMARY KEY auto_increment," +
                "course_id INTEGER," +
                "start_time TIME," +
                "end_time TIME," +
                "lesson_date DATE," +
                "FOREIGN KEY (course_id) REFERENCES public.COURSES(course_id) " +
                "ON UPDATE CASCADE " +
                "ON DELETE CASCADE," +
                "UNIQUE (course_id, start_time, end_time, lesson_date));");
    }

    private void createStudentTable(Connection connection) throws SQLException {
        connection.createStatement().execute("CREATE TABLE Students(" +
                "student_id INTEGER PRIMARY KEY auto_increment," +
                "student_name VARCHAR (20), " +
                "student_surname VARCHAR (30), " +
                "UNIQUE (student_name, student_surname));");
    }

    private void createVisitTable(Connection connection) throws SQLException {
        connection.createStatement().execute("CREATE TABLE visits(" +
                "visit_id INTEGER PRIMARY KEY auto_increment," +
                "lesson_id INTEGER ," +
                "student_id INTEGER," +
                "FOREIGN KEY (lesson_id) REFERENCES public.LESSONS(lesson_id) " +
                "ON UPDATE CASCADE " +
                "ON DELETE CASCADE," +
                "FOREIGN KEY (student_id) REFERENCES public.STUDENTS(student_id) " +
                "ON UPDATE CASCADE " +
                "ON DELETE CASCADE, " +
                "UNIQUE (lesson_id, student_id));");
    }

    private void saveCourse(CourseDao courseDao) throws SQLException {
        courseDao.saveCourse("Math");
        courseDao.saveCourse("Bio");
        courseDao.saveCourse("Geo");
        courseDao.saveCourse("History");
    }

    private void saveStudents(StudentDao studentDao) throws SQLException {
        studentDao.saveStudent("Bob", "Marley");
        studentDao.saveStudent("Bob", "Dylan");
        studentDao.saveStudent("Elvis", "Presley");
        studentDao.saveStudent("John", "Lennon");
    }

    private void saveLessons(LessonDao lessonDao, CourseDao courseDao) throws SQLException {
        lessonDao.saveLesson(courseDao.getCourseId("Math"),
                Time.valueOf("10:30:00"), Time.valueOf("11:50:00"), Date.valueOf("2016-10-21"));
        lessonDao.saveLesson(courseDao.getCourseId("Math"),
                Time.valueOf("10:30:00"), Time.valueOf("11:50:00"), Date.valueOf("2016-10-22"));
        lessonDao.saveLesson(courseDao.getCourseId("Math"),
                Time.valueOf("10:30:00"), Time.valueOf("11:50:00"), Date.valueOf("2016-10-23"));

        lessonDao.saveLesson(courseDao.getCourseId("Geo"),
                Time.valueOf("12:10:00"), Time.valueOf("13:30:00"), Date.valueOf("2016-10-21"));
        lessonDao.saveLesson(courseDao.getCourseId("Geo"),
                Time.valueOf("12:10:00"), Time.valueOf("13:30:00"), Date.valueOf("2016-10-22"));
        lessonDao.saveLesson(courseDao.getCourseId("Geo"),
                Time.valueOf("12:10:00"), Time.valueOf("13:30:00"), Date.valueOf("2016-10-23"));

        lessonDao.saveLesson(courseDao.getCourseId("Bio"),
                Time.valueOf("13:40:00"), Time.valueOf("15:00:00"), Date.valueOf("2016-10-21"));
        lessonDao.saveLesson(courseDao.getCourseId("Bio"),
                Time.valueOf("13:40:00"), Time.valueOf("15:00:00"), Date.valueOf("2016-10-22"));
        lessonDao.saveLesson(courseDao.getCourseId("Bio"),
                Time.valueOf("13:40:00"), Time.valueOf("15:00:00"), Date.valueOf("2016-10-23"));

        lessonDao.saveLesson(courseDao.getCourseId("History"),
                Time.valueOf("15:10:00"), Time.valueOf("16:30:00"), Date.valueOf("2016-10-21"));
        lessonDao.saveLesson(courseDao.getCourseId("History"),
                Time.valueOf("15:10:00"), Time.valueOf("16:30:00"), Date.valueOf("2016-10-22"));
        lessonDao.saveLesson(courseDao.getCourseId("History"),
                Time.valueOf("15:10:00"), Time.valueOf("16:30:00"), Date.valueOf("2016-10-23"));
    }

    private void saveVisits(VisitDao visitDao, LessonDao lessonDao,
                            StudentDao studentDao, CourseDao courseDao) throws SQLException {
        visitDao.saveVisit(lessonDao.getId(courseDao.getCourseId("Math"), Time.valueOf("10:30:00"),
                Time.valueOf("11:50:00"), Date.valueOf("2016-10-23")),
                studentDao.getStudentId("Bob", "Dylan"));
        visitDao.saveVisit(lessonDao.getId(courseDao.getCourseId("Geo"), Time.valueOf("12:10:00"),
                Time.valueOf("13:30:00"), Date.valueOf("2016-10-23")),
                studentDao.getStudentId("Bob", "Dylan"));
        visitDao.saveVisit(lessonDao.getId(courseDao.getCourseId("Math"), Time.valueOf("10:30:00"),
                Time.valueOf("11:50:00"), Date.valueOf("2016-10-23")),
                studentDao.getStudentId("Elvis", "Presley"));
        visitDao.saveVisit(lessonDao.getId(courseDao.getCourseId("Geo"), Time.valueOf("12:10:00"),
                Time.valueOf("13:30:00"), Date.valueOf("2016-10-23")),
                studentDao.getStudentId("Elvis", "Presley"));
        visitDao.saveVisit(lessonDao.getId(courseDao.getCourseId("Math"), Time.valueOf("10:30:00"),
                Time.valueOf("11:50:00"), Date.valueOf("2016-10-23")),
                studentDao.getStudentId("John", "Lennon"));
        visitDao.saveVisit(lessonDao.getId(courseDao.getCourseId("Geo"), Time.valueOf("12:10:00"),
                Time.valueOf("13:30:00"), Date.valueOf("2016-10-23")),
                studentDao.getStudentId("John", "Lennon"));
        visitDao.saveVisit(lessonDao.getId(courseDao.getCourseId("Math"), Time.valueOf("10:30:00"),
                Time.valueOf("11:50:00"), Date.valueOf("2016-10-22")),
                studentDao.getStudentId("Bob", "Dylan"));
        visitDao.saveVisit(lessonDao.getId(courseDao.getCourseId("Geo"), Time.valueOf("12:10:00"),
                Time.valueOf("13:30:00"), Date.valueOf("2016-10-22")),
                studentDao.getStudentId("Bob", "Dylan"));
        visitDao.saveVisit(lessonDao.getId(courseDao.getCourseId("Math"), Time.valueOf("10:30:00"),
                Time.valueOf("11:50:00"), Date.valueOf("2016-10-22")),
                studentDao.getStudentId("Elvis", "Presley"));
        visitDao.saveVisit(lessonDao.getId(courseDao.getCourseId("Geo"), Time.valueOf("12:10:00"),
                Time.valueOf("13:30:00"), Date.valueOf("2016-10-22")),
                studentDao.getStudentId("Elvis", "Presley"));
        visitDao.saveVisit(lessonDao.getId(courseDao.getCourseId("Math"), Time.valueOf("10:30:00"),
                Time.valueOf("11:50:00"), Date.valueOf("2016-10-22")),
                studentDao.getStudentId("John", "Lennon"));
        visitDao.saveVisit(lessonDao.getId(courseDao.getCourseId("Geo"), Time.valueOf("12:10:00"),
                Time.valueOf("13:30:00"), Date.valueOf("2016-10-22")),
                studentDao.getStudentId("John", "Lennon"));
        visitDao.saveVisit(lessonDao.getId(courseDao.getCourseId("Geo"), Time.valueOf("12:10:00"),
                Time.valueOf("13:30:00"), Date.valueOf("2016-10-21")),
                studentDao.getStudentId("John", "Lennon"));
    }
}
