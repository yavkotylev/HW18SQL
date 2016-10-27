package ru.sbt.dbworker;

import ru.sbt.course.CourseDao;
import ru.sbt.course.CourseDaoImpl;
import ru.sbt.dbworker.tables.TableCreator;
import ru.sbt.dbworker.tables.TableCreatorImpl;
import ru.sbt.lesson.LessonDao;
import ru.sbt.lesson.LessonDaoImpl;
import ru.sbt.student.StudentDao;
import ru.sbt.student.StudentDaoImpl;
import ru.sbt.visit.VisitDao;
import ru.sbt.visit.VisitDaoImpl;


import java.sql.SQLException;

/**
 * Created by Yaroslav on 15.10.16.
 */
public class DBWorkerImpl implements DBWorker {
    private final CourseDao courseDao;
    private final LessonDao lessonDao;
    private final StudentDao studentDao;
    private final VisitDao visitDao;
    private final TableCreator tableCreator;

    public DBWorkerImpl(String url, String name, String password) {
        DBConnection connection = new DBConnection(url, name, password);
        courseDao = new CourseDaoImpl(connection);
        lessonDao = new LessonDaoImpl(connection);
        studentDao = new StudentDaoImpl(connection);
        visitDao = new VisitDaoImpl(connection);
        tableCreator = new TableCreatorImpl(connection);
    }

    @Override
    public void createTables() throws SQLException {
        tableCreator.createTables();
    }

    @Override
    public void fillTables() throws SQLException {
        tableCreator.fillTables(courseDao, studentDao, lessonDao, visitDao);
    }

    @Override
    public CourseDao getCourseDao() {
        return courseDao;
    }

    @Override
    public LessonDao getLessonDao() {
        return lessonDao;
    }

    @Override
    public StudentDao getStudentDao() {
        return studentDao;
    }

    @Override
    public VisitDao getVisitDao() {
        return visitDao;
    }
}