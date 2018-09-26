package build.dao;

import build.model.ParentCoursesMajor;
import java.util.List;
/**
 * Created by apple on 01/12/2017.
 */
public interface ParentCoursesMajorDao {

//    public String superUserDeleteAllParentCoursesMajorsRelations();

    public String addParentCourseToMajor(long parentCourseId , int majorId);

    public String updateParentCoursesMajor( long parentCourseId, int newMajorId);

    public String deleteParentCourseFromMajor(long parentCourseId , int majorId);

    public boolean checkIfCourseHasMajor(long parentCourseId, int majorId);




}
