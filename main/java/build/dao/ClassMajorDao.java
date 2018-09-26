package build.dao;

import build.model.ClassMajor;
import java.util.List;
/**
 * Created by apple on 01/12/2017.
 */
public interface ClassMajorDao {

    public String addClassToMajor(int majorId , int classId);

    public String updateClassMajor(int newMajorId, int classId);

    public String deleteClassFromMajor(int majorId, int classId);
}
