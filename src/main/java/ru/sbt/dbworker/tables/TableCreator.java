package ru.sbt.dbworker.tables;

import ru.sbt.course.CourseDao;
import ru.sbt.lesson.LessonDao;
import ru.sbt.student.StudentDao;
import ru.sbt.visit.VisitDao;

import java.sql.SQLException;

/**
 * Created by Yaroslav on 21.10.16.
 */
public interface TableCreator {
    void createTables() throws SQLException;

    void fillTables(CourseDao courseDao, StudentDao studentDao,
                    LessonDao lessonDao, VisitDao visitDao) throws SQLException;
}
