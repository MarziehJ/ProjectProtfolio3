package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.RegisterCourseModel;
import model.StudentGrade;
import view.StudentGradeView;

public class StudentGradeController {

    private RegisterCourseModel model;
    private StudentGradeView view;
    private StudentGrade studentGrade;
    private ObservableList<Integer> gradeList = FXCollections.observableArrayList(-3, 0, 2, 4, 7, 10, 12);


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


    public void setView(StudentGradeView view) {
        this.view = view;
        view.CancelBtn.setOnAction(e -> {
            CloseWindow(view);
        });
        view.UpdateBtn.setOnAction(actionEvent -> {
            UpdateStudentGrade(view);

        });

    }

    private void UpdateStudentGrade(StudentGradeView view) {
        if (view.gradeCombo.getValue() != null) {

            int result = model.UpdateStudentGrade(
                    this.studentGrade.getStudentId(),
                    this.studentGrade.getCourseId(),
                    view.gradeCombo.getValue());
            if (result > 0)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Update Successfully");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        CloseWindow(view);
                    }
                });
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Error in updating information");
                alert.showAndWait().ifPresent(rs -> {
                });
            }

        }
    }

    private void CloseWindow(StudentGradeView view) {
        Stage stage = (Stage)view.CancelBtn.getScene().getWindow();
        stage.close();
    }

}
