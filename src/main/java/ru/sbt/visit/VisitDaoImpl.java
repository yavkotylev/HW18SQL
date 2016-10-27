package ru.sbt.visit;

import ru.sbt.dbworker.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yaroslav on 14.10.16.
 */
public class VisitDaoImpl implements VisitDao {

    private final DBConnection connection;

    public VisitDaoImpl(DBConnection connection) {
        this.connection = connection;
    }

    @Override
    public void saveVisit(long lessonId, long studentId) throws SQLException {
        Connection conn = connection.getConnection();
        conn.setAutoCommit(false);
        PreparedStatement statement = conn.prepareStatement("INSERT INTO VISITS " +
                "(LESSON_ID, STUDENT_ID) VALUES (?, ?);");
        statement.setLong(1, lessonId);
        statement.setLong(2, studentId);
        statement.executeUpdate();
        conn.commit();
    }

    @Override
    public void deleteVisit(long lessonId, long studentId) throws SQLException {
        connection.getConnection().createStatement().execute("DELETE FROM VISITS WHERE " +
                "LESSON_ID = '" + lessonId + "' AND " +
                "STUDENT_ID = '" + studentId + "';");
    }

    @Override
    public List<Visit> getVisits() throws SQLException {
        List<Visit> visits = new ArrayList<>();
        ResultSet resultSet = connection.getConnection().createStatement().executeQuery("SELECT * " +
                "FROM VISITS");
        while (resultSet.next()) {
            visits.add(new Visit(resultSet.getLong("VISIT_ID"),
                    resultSet.getLong("STUDENT_ID"), resultSet.getLong("LESSON_ID")));
        }
        return visits;
    }

    @Override
    public Visit getVisit(long visitId) throws SQLException {
        ResultSet resultSet = connection.getConnection().createStatement().executeQuery("SELECT * FROM " +
                "VISITS WHERE VISTIT_ID = '" + visitId + "';");
        if (resultSet.next() == true) {
            return new Visit(visitId, resultSet.getLong("STUDENT_ID"), resultSet.getLong("LESSON_ID"));
        }
        throw new SQLException("There aren't such element");
    }
}
