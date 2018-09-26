package build.dao;

import build.model.ChildCourseSemester;
import java.util.List;

/**
 * Created by apple on 14/12/2017.
 */
public interface ChildCourseSemesterDao {


    public String superDeleteAllInChildCourseSemesterRelations();

    public String addChildCourseToSemester(long parentCourseId , int childCourseId, int semesterId);

    public String updateChildCourseSemester(int recordId, int newSemesterId);

    public String removeChildCourseFromSemester(int itemRecord);


    //checks
    public boolean checkIfChildCourseIdBelongsToASemester(int childCourseId);


}
