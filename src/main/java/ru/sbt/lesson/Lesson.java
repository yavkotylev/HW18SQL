package ru.sbt.lesson;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by Yaroslav on 14.10.16.
 */
public class Lesson {
    private final long courseId;
    private final long id;
    private final Time startTime;
    private final Time endTime;
    private final Date date;

    public Lesson(long id, long courseId, Time startTime, Time endTime, Date date) {
        this.id = id;
        this.courseId = courseId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public long getCourseId() {
        return courseId;
    }

    public long getId() {
        return id;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    @Override
    public int hashCode() {
        return (int) id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj == this) return false;
        if (!(obj instanceof Lesson)) return false;
        Lesson lesson = (Lesson) obj;
        return (lesson.getId() == id && lesson.courseId == courseId && lesson.date.equals(date)
                && lesson.startTime.equals(startTime) && lesson.endTime.equals(endTime));
    }
}
