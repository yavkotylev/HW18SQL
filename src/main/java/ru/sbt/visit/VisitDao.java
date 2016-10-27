package ru.sbt.visit;


import java.sql.SQLException;
import java.util.List;

/**
 * Created by Yaroslav on 14.10.16.
 */
public interface VisitDao {
    void saveVisit(long lessonId, long studentId) throws SQLException;

    void deleteVisit(long lessonId, long studentId) throws SQLException;

    List<Visit> getVisits() throws SQLException;

    Visit getVisit(long visitId) throws SQLException;
}
