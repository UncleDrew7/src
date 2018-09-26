package build.dao;

import build.model.ParentCourseChildCourses;
import java.util.List;
/**
 * Created by apple on 01/12/2017.
 */
public interface ParentCourseChildCoursesDao {

    public String superDeleteAllParentCourseChildCourseRelations();

    public String addChildCourseToParentCourse(long parentCourseId, int childCourseId );

    public String updateParentCourseChildCourses(long newParentCourseId , int childCourseId);

    public String deleteChildCourseFromParentCourse(long parentCourseId, int childCourse);

    public String deleteParentCourseChildCoursesRecord(int recordId);



}
