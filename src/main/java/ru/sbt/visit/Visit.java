package ru.sbt.visit;

/**
 * Created by Yaroslav on 14.10.16.
 */
public class Visit {
    private final long visitId;
    private final long studentId;
    private final long lessonId;


    public Visit(long visitId, long studentId, long lessonId) {
        this.visitId = visitId;
        this.studentId = studentId;
        this.lessonId = lessonId;
    }

    public long getStudentId() {
        return studentId;
    }

    public long getLessonId() {
        return lessonId;
    }

    public long getVisitId() {
        return visitId;
    }
}
