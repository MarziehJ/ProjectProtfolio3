package test;

import model.RegisterCourseModel;
import model.Semester;
import model.Student;
import model.StudentGrade;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegisterCourseModelTest {

    String connectionString = "jdbc:sqlite:C:/RUC/SD/DB/ProjectProtfolio3.db";
    RegisterCourseModel model;
    @BeforeEach
    void before() {
        model = new RegisterCourseModel(connectionString);
    }

    @org.junit.jupiter.api.Test
    void getAllSemester() {
        List<Semester> result = model.getAllSemester();
        int expected= 3;
        assertEquals(expected, result.size());
    }

    @org.junit.jupiter.api.Test
    void getAllStudent() {
        List<Student> result = model.getAllStudent();
        int expected= 10;
        assertEquals(expected, result.size());
    }

    @org.junit.jupiter.api.Test
    void getRegisteredCoursed_Student_1() {
        //studentid = 1 -> Aisha
        List<StudentGrade> result = model.getRegisteredCoursed(1, -1, -1);
        int expected = 2;
        assertEquals(expected, result.size());

    }


    @org.junit.jupiter.api.Test
    void getRegisteredCourse_Course_2() {
        //course id = 3 -> ES1 - ebbe - spring 202
        List<StudentGrade> result = model.getRegisteredCoursed(-1, -1, 3);
        int expected = 10;
        assertEquals(expected, result.size());

    }

    @org.junit.jupiter.api.Test
    void getSemeterAverage() {
        //Anya, spring 2020
        double result =   model.getSemeterAverage(2, 2);
        double expected = 12.00;
        assertEquals(expected, result);
    }

    @org.junit.jupiter.api.Test
    void getOverallAverage() {
        //Aisha, overall
        //(12*10+10*5)/15 = 11.33
        double result =   model.getOverallAverage(1);
        double expected = 11.333333015441895;
        assertEquals(expected, result);
    }

    @org.junit.jupiter.api.Test
    void getOverallCourseAverage() {
        //course id = 3 -> ES1 - ebbe - spring 202
        double result =   model.getOverallCourseAverage(3);
        double expected = 8.600000381469727;
        assertEquals(expected, result);
    }
}