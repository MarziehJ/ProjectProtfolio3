package view;

import controller.StudentGradeController;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;


public class StudentGradeView {
    StudentGradeController controller;
    private GridPane startview;
    Label studentLbl = new Label("Student:");
    Label courseLbl = new Label("Course:");
    Label semesterLbl = new Label("Semester:");
    Label gradeLbl = new Label("Grade:");

    TextField studentText = new TextField();
    TextField courseText = new TextField();
    TextField semesterText = new TextField();

    Button UpdateBtn = new Button("Update");
    Button CancelBtn = new Button("Cancel");


    ComboBox<Integer> gradeCombo = new ComboBox<Integer>();

    public StudentGradeView(StudentGradeController controller)
    {
        this.controller = controller;
        createAndConfigure();
    }

    private void createAndConfigure() {
        startview = new GridPane();
        startview.setMinSize(300, 200);
        startview.setPadding(new Insets(10,10,10,10));
        startview.setVgap(5);
        startview.setHgap(1);

        startview.add(studentLbl, 1, 1);
        startview.add(studentText, 2, 1);
        startview.add(courseLbl, 1, 2);
        startview.add(courseText, 2, 2);
        startview.add(semesterLbl, 1, 3);
        startview.add(semesterText, 2, 3);
        startview.add(gradeLbl, 1, 4);
        startview.add(gradeCombo, 2, 4);

        startview.add(UpdateBtn, 2, 8);
        startview.add(CancelBtn, 3, 8);

        studentText.setText(controller.getStudentGrade().getStudentName());
        courseText.setText(controller.getStudentGrade().getCourseName());
        semesterText.setText(controller.getStudentGrade().getSemesterName());
        gradeCombo.setItems(controller.getGradeList());
        if (controller.getStudentGrade().getGrade() != null)
            gradeCombo.getSelectionModel().select(controller.getStudentGrade().getGrade());


    }

    public Parent asParent(){
        return startview;
    }

}
