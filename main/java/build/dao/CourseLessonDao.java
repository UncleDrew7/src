package build.dao;
import build.model.CourseLesson;

import java.util.List;

/**
 * Created by apple on 29/08/2017.
 */
public interface CourseLessonDao {

    public String addCourseLesson(CourseLesson courseLesson);

    //STUDENT COURSE PAGE
    public List<CourseLesson> lessonContent(int courseId);

    //GET SINGLE COURSE LESSON DATA
    public CourseLesson getSingleCourseLessonByLessonId(int lessonId);

    //UPDATE COURSE LESSON
    public String updateCourseLessonData(CourseLesson courseLesson);

    public List<CourseLesson> getCurrentlyAddedLessonsList(int courseId);

    public String deleteLessonContent(int lessonId);

}
