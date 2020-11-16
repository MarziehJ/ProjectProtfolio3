package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegisterCourseModel {
    String connectionString;

    public RegisterCourseModel(String connectionString) {
        this.connectionString = connectionString;
    }

    private String getSemesterTableName() {
        return "Semester";
    }

    private String getStudentTableName() {
        return "Student";
    }

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(this.connectionString);
    }

    public List<Semester> getAllSemester() {
        ArrayList result = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = connect();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(String.format(" SELECT * FROM %s;", getSemesterTableName()));

            while (rs.next()) {
                Semester semester = new Semester(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getShort("year"));
                result.add(semester);
            }

            conn.close();
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    public List<Student> getAllStudent() {
        ArrayList result = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = connect();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(String.format(" SELECT * FROM %s;", getStudentTableName()));

            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("lastName"),
                        rs.getString("city"));
                result.add(student);
            }

            conn.close();
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    private static String getRegisteredSemsterCoursedSQL =
            "SELECT studentId, studentName, SemesterName, semesterId, courseId, courseName, teacherName, ECTS, grade " +
                    "FROM vwStudentGrade  " +
                    "WHERE StudentId = ? AND SemesterId = ?";
    private static String getRegisteredAllCoursedSQL =
            "SELECT studentId, studentName, SemesterName, semesterId, courseId, courseName, teacherName, ECTS, grade " +
                    "FROM vwStudentGrade  " +
                    "WHERE StudentId = ? ";
    private static String getCoursedGradsSQL =
            "SELECT studentId, studentName, SemesterName, semesterId, courseId, courseName, teacherName, ECTS, grade " +
                    "FROM vwStudentGrade  " +
                    "WHERE CourseId = ? ";
    private static String getSemesterAverageSQL =
            "SELECT CAST(SUM(CASE WHEN grade IS NULL THEN 0 ELSE grade*ECTS END) AS FLOAT) " +
                    "  /(SUM(CASE WHEN grade IS NULL THEN 0 ELSE ECTS END))  AS AvgGrage " +
                    "FROM vwStudentGrade AS SG " +
                    "WHERE StudentId = ? AND SemesterId = ?";

    private static String getOverallAverageSQL =
            "SELECT CAST(SUM(CASE WHEN grade IS NULL THEN 0 ELSE grade*ECTS END) AS FLOAT)  " +
                    "  /(SUM(CASE WHEN grade IS NULL THEN 0 ELSE ECTS END))  AS AvgGrage " +
                    "FROM vwStudentGrade AS SG " +
                    "WHERE StudentId = ? ";

    private static String getSemesterCourseAverageSQL =
            "SELECT CAST(SUM(CASE WHEN grade IS NULL THEN 0 ELSE grade*ECTS END) AS FLOAT) " +
                    "  /(SUM(CASE WHEN grade IS NULL THEN 0 ELSE ECTS END))  AS AvgGrage " +
                    "FROM vwStudentGrade AS SG " +
                    "WHERE CourseId = ? AND SemesterId = ?";

    private static String getOverallCourseAverageSQL =
            "SELECT CAST(SUM(CASE WHEN grade IS NULL THEN 0 ELSE grade*ECTS END) AS FLOAT)  " +
                    "  /(SUM(CASE WHEN grade IS NULL THEN 0 ELSE ECTS END))  AS AvgGrage " +
                    "FROM vwStudentGrade AS SG " +
                    "WHERE CourseId = ? ";
    private static String updateStudentGradeSQL =
            "UPDATE Studentgrade SET Grade = ? WHERE studentId = ? AND courseId = ?";

    public List<StudentGrade> getRegisteredCoursed(int studentId, int semesterId, int courseId) {
        ArrayList result = new ArrayList();
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = connect();
            String sql = courseId != -1 ? getCoursedGradsSQL :
                    (semesterId == -1 ? getRegisteredAllCoursedSQL : getRegisteredSemsterCoursedSQL);
            stmt = conn.prepareStatement(sql);
            if (studentId != -1)
                stmt.setInt(1, studentId);
            if (semesterId != -1)
                stmt.setInt(2, semesterId);
            if (courseId != -1)
                stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Integer grade = null;
                if (rs.getObject("grade") != null)
                    grade = rs.getInt("grade");
                StudentGrade studentGrade = new StudentGrade(
                        rs.getInt("studentId"),
                        rs.getInt("semesterId"),
                        rs.getInt("courseId"),
                        rs.getString("CourseName"),
                        rs.getString("teacherName"),
                        rs.getShort("ECTS"),
                        grade,
                        rs.getString("StudentName"),
                        rs.getString("SemesterName"));
                result.add(studentGrade);
            }

            conn.close();
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    public Float getSemeterAverage(int studentId, int semesterId) {
        if (semesterId == -1)
            return null;

        Float result = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = connect();
            stmt = conn.prepareStatement(getSemesterAverageSQL);
            stmt.setInt(1, studentId);
            stmt.setInt(2, semesterId);
            ResultSet rs = stmt.executeQuery();
            if (rs.getObject("AvgGrage") != null)
                result = rs.getFloat("AvgGrage");
            conn.close();
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public Float getOverallAverage(int studentId) {
        Float result = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = connect();
            stmt = conn.prepareStatement(getOverallAverageSQL);
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.getObject("AvgGrage") != null)
                result = rs.getFloat("AvgGrage");
            conn.close();
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public Float getSemeterCourseAverage(int courseId, int semesterId) {
        if (semesterId == -1)
            return null;

        Float result = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = connect();
            stmt = conn.prepareStatement(getSemesterCourseAverageSQL);
            stmt.setInt(1, courseId);
            stmt.setInt(2, semesterId);
            ResultSet rs = stmt.executeQuery();
            if (rs.getObject("AvgGrage") != null)
                result = rs.getFloat("AvgGrage");
            conn.close();
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public Float getOverallCourseAverage(int courseId) {
        Float result = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = connect();
            stmt = conn.prepareStatement(getOverallCourseAverageSQL);
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();
            if (rs.getObject("AvgGrage") != null)
                result = rs.getFloat("AvgGrage");
            conn.close();
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    public int UpdateStudentGrade(int studentId, int courseId, int grade) {
        int result = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = connect();
            stmt = conn.prepareStatement(updateStudentGradeSQL);
            stmt.setInt(1, grade);
            stmt.setInt(2, studentId);
            stmt.setInt(3, courseId);
            result = stmt.executeUpdate();
            conn.close();
            stmt.close();;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}

