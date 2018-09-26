package build.model;

/**
 * Created by apple on 15/08/2017.
 */
public class StudentClass {

    private int id;
    private long studentId;
    private int classId;
    private String createdAt;

    public StudentClass() {
        super();
    }

    public StudentClass(long studentId, int classId, String createdAt) {
        this.studentId = studentId;
        this.classId = classId;
        this.createdAt = createdAt;
    }

    public StudentClass(long studentId, int classId) {
        this.studentId = studentId;
        this.classId = classId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
