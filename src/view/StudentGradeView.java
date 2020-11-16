package view;

import controller.StudentGradeController;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;


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

    public Button UpdateBtn = new Button("Update");
    public  Button CancelBtn = new Button("Cancel");
    public ComboBox<Integer> gradeCombo = new ComboBox<Integer>();

    public StudentGradeView(StudentGradeController controller)
    {
        this.controller = controller;
        createAndConfigure();
    }

    private void createAndConfigure() {
        startview = new GridPane();

        startview.getColumnConstraints().add(new ColumnConstraints(100));
        startview.getColumnConstraints().add(new ColumnConstraints(100));
        startview.getColumnConstraints().add(new ColumnConstraints(100));

        startview.getRowConstraints().add(new RowConstraints());
        startview.getRowConstraints().add(new RowConstraints());
        startview.getRowConstraints().add(new RowConstraints());
        startview.getRowConstraints().add(new RowConstraints());
        startview.getRowConstraints().add(new RowConstraints());
        startview.getRowConstraints().add(new RowConstraints());
        startview.getRowConstraints().add(new RowConstraints());
        startview.getRowConstraints().add(new RowConstraints());
        startview.getRowConstraints().add(new RowConstraints());

        startview.setMinSize(340, 250);
        startview.setPadding(new Insets(10,10,10,10));
        startview.setVgap(5);
        startview.setHgap(1);

        startview.add(studentLbl, 0, 0);
        startview.add(studentText, 1, 0, 2, 1);
        startview.add(courseLbl, 0, 1);
        startview.add(courseText, 1, 1, 2, 1);
        startview.add(semesterLbl, 0, 2);
        startview.add(semesterText, 1, 2, 2, 1);
        startview.add(gradeLbl, 0, 3);
        startview.add(gradeCombo, 1, 3, 2, 1);

        FlowPane flowPane = new FlowPane();
        flowPane.setPadding(new Insets(5,5,5,5));
        flowPane.getChildren().add(UpdateBtn);
        flowPane.getChildren().add(CancelBtn);


        startview.add(flowPane, 2, 12, 2, 1);


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
