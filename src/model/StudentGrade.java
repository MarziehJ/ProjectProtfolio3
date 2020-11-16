package model;

public class StudentGrade {
    private int studentId;
    private int semesterId;
    private int courseId;
    private String courseName;
    private String teacherName;
    private short ECTS;
    private Integer grade;
    private String studentName;
    private String semesterName;

    public StudentGrade(int studentId, int semesterId, int courseId, String courseName, String teacherName, short ects, Integer grade, String studentName, String semesterName) {
        this.studentId = studentId;
        this.semesterId = semesterId;
        this.courseId = courseId;
        this.courseName = courseName;
        this.teacherName = teacherName;
        ECTS = ects;
        this.grade = grade;
        this.studentName = studentName;
        this.semesterName = semesterName;
    }


    public String getCourseName() {
        return courseName;
    }

    public short getECTS() {
        return ECTS;
    }

    public Integer getGrade() {
        return grade;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public  String getCourseInfo() { return  String.format("%s %s teacher: %s", courseName, getSemesterName(), teacherName);}

    public String getStudentName() {
        return studentName;
    }

    public String getSemesterName() {
        return semesterName;
    }
}
