package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.RegisterCourseModel;
import model.StudentGrade;

public class StudentGradeController {

    private RegisterCourseModel model;
    private StudentGrade studentGrade;
    private ObservableList<Integer> gradeList = FXCollections.observableArrayList(-3,  0, 2, 4, 7, 10, 12);


    public StudentGradeController(RegisterCourseModel model, StudentGrade studentGrade) {
        this.model = model;
        this.studentGrade = studentGrade;
    }


    public ObservableList<Integer> getGradeList() {
        return gradeList;
    }

    public StudentGrade getStudentGrade() {
        return studentGrade;
    }
}
