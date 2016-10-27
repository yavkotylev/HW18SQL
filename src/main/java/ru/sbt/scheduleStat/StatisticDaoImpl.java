package ru.sbt.scheduleStat;

import ru.sbt.course.Course;
import ru.sbt.dbworker.DBWorker;
import ru.sbt.lesson.Lesson;
import ru.sbt.student.Student;
import ru.sbt.visit.Visit;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * Created by Yaroslav on 23.10.16.
 */
public class StatisticDaoImpl implements StatisticDao {
    private final DBWorker dbWorker;

    public StatisticDaoImpl(DBWorker dbWorker) {
        this.dbWorker = dbWorker;
    }

    @Override
    public Map<Course, Long> getMostAttendanceCourse() throws SQLException {
        return getMostCourse((current, general) -> current > general);
    }

    @Override
    public Map<Course, Long> getLeastAttendanceCourse() throws SQLException {
        return getMostCourse((current, general) -> current < general);
    }

    @Override
    public Map<Lesson, Long> getMostAttendanceLesson() throws SQLException {
        return getMostLesson((current, general) -> current > general);
    }

    @Override
    public Map<Lesson, Long> getLeastAttendanceLesson() throws SQLException {
        return getMostLesson((current, general) -> current < general);
    }

    @Override
    public Map<Student, Long> getMostDiligentStudent() throws SQLException {
        return getMostStudent((current, general) -> current > general);
    }

    @Override
    public Map<Student, Long> getLeastDiligentStudent() throws SQLException {
        return getMostStudent((current, general) -> current < general);
    }

    private <T> Map<T, Long> getMost(List<T> list, Function<Visit, T> getInstance,
                                     Function<Map<T, Long>, Long> getMostAttendance) throws SQLException {
        List<Visit> visits = dbWorker.getVisitDao().getVisits();
        Map<T, Long> attendanceMap = new HashMap<>();
        list.forEach(instance -> attendanceMap.put(instance, 0L));
        visits.forEach(visit -> {
            T instance = getInstance.apply(visit);
            attendanceMap.replace(instance, attendanceMap.get(instance) + 1);
        });
        long attendance = getMostAttendance.apply(attendanceMap);
        list.forEach(instance -> {
            if (attendanceMap.get(instance) != attendance) {
                attendanceMap.remove(instance);
            }
        });
        return attendanceMap;
    }

    private <T> long getMostAttendance(Map<T, Long> attendanceMap, BiPredicate<Long, Long> most) {
        long attendance = 0;
        for (T something : attendanceMap.keySet()) {
            if (most.test(attendanceMap.get(something), attendance)) {
                attendance = attendanceMap.get(something);
            }
        }
        return attendance;
    }

    private Map<Course, Long> getMostCourse(BiPredicate<Long, Long> howToChooseBoundary) throws SQLException {
        Function<Visit, Course> getInstance = (visit) -> {
            try {
                Long courseId = dbWorker.getLessonDao().getCourseId(visit.getLessonId());
                return dbWorker.getCourseDao().getCourse(courseId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };
        return getMost(dbWorker.getCourseDao().getCourses(), getInstance,
                (courseMap -> getMostAttendance(courseMap, howToChooseBoundary)));
    }

    private Map<Lesson, Long> getMostLesson(BiPredicate<Long, Long> howToChooseBoundary) throws SQLException {
        Function<Visit, Lesson> getInstance = (visit) -> {
            try {
                return dbWorker.getLessonDao().getLesson(visit.getLessonId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };
        return getMost(dbWorker.getLessonDao().getLessons(), getInstance,
                (lessonMap -> getMostAttendance(lessonMap, howToChooseBoundary)));
    }

    private Map<Student, Long> getMostStudent(BiPredicate<Long, Long> howToChooseBoundary) throws SQLException {
        Function<Visit, Student> getInstance = (visit) -> {
            try {
                return dbWorker.getStudentDao().getStudent(visit.getStudentId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };
        return getMost(dbWorker.getStudentDao().getStudents(), getInstance,
                (studentMap -> getMostAttendance(studentMap, howToChooseBoundary)));
    }
}