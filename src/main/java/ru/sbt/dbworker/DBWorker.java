package ru.sbt.dbworker;

import ru.sbt.course.CourseDao;
import ru.sbt.lesson.LessonDao;
import ru.sbt.student.StudentDao;
import ru.sbt.visit.VisitDao;

import java.sql.SQLException;

/**
 * Created by Yaroslav on 17.10.16.
 */
public interface DBWorker {
    void createTables() throws SQLException;

    void fillTables() throws SQLException;

    CourseDao getCourseDao();

    LessonDao getLessonDao();

    StudentDao getStudentDao();

    VisitDao getVisitDao();
}
