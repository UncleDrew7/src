package build.dao;

import build.model.Class ;
import build.model.User;

import java.util.List;

/**
 * Created by apple on 15/08/2017.
 */
public interface ClassDao {

    public int getLargestClassId();

    public String createNewClass(Class newClass);

    public String deleteClassInClassTableByClassId(int classId);

    public String deleteClassInClassTableByClassName(String className);

    public String updateExistingClass(Class existingClass);

    public String deleteExistingClass(int classId);

    public String deleteExistingClassStudents(int classId);

    public String deleteClassMajor(int classId);

    public List<User> getClassStudentList();

    public List<Class> displayExistingClasses();

    //MANAGE-USER | MANAGE CLASS
    public List<Class> getAllSystemClasses();

    public List<Class> getAllSystemClassesFilteredByIntake(String intake);

    public List<Class> getAllSystemClassesFilteredByMajor(int majorId);

    public List<Class> getAllSystemClassesFilteredByMajorAndIntake(int majorId, String intake);

    //MANAGE -USER | MANAGE CLASS STATS
    public int getActiveSystemClassesCount();

    //ADIM GET CLASS LIST FOR CLASS
    public List<User> getClassListByClassId(int classId);

    //ADMIN GET SINGLE CLASS DETAILS
    public Class getSingleClassDetailsByClassId(int classId);

    //ADMIN ENROLL STUDENT IN CLASS
    public String enrollStudentInClass(int classId,long userId);

    //ADMIN DELETE STUDENT FROM CLASS
    public String deleteStudentFromClass(int classId, long userId);

    //ADMIN CHECK IF STUDENT BELONGS TO A CLASS
    public boolean checkIfStudentBelongsToClassInSystem(long userId);

    public boolean checkIfClassNameAlreadyInSystem(String className);

    //ADMIN GET STUDENT CLASS
    public String getStudentClassByUserId(long userId);

    //ADMIN GET TOTAL STUDNETS ENROLLED IN CLASS
    public int getCountOfTotalEnrolledStudentsInClass(int classId);

    //ADMIN GET LAST ADDED CLASSES
    public List<Class> getLastAddedClasses();

    //list
    public List<Class> getAllClassesNamesSelectList();

    public List<Class> getAllIntakeNamesSelectList();
}
