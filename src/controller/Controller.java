package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Semester;
import model.RegisterCourseModel;
import model.Student;
import model.StudentGrade;
import view.StudentGradeView;

import java.net.UnknownHostException;

public class Controller {
    public ObservableList<Student> studentList = FXCollections.observableArrayList();
    public ObservableList<Semester> semesterList = FXCollections.observableArrayList();
    public ObservableList<StudentGrade> gradeList = FXCollections.observableArrayList();


    public TableColumn studentColumnName, studentColumnLastName, studentColumnCity;
    public TableColumn gradeColumnCourse, gradeColumnECTS, gradeColumnGrade, gradeColumnTeacher;

    public TableView tableViewStudent;
    public TableView tableViewGrade;
    public ComboBox comboBoxSemester;
    public TextField txtSemesterAverage, txtOverallAverage, txtCourseSemesterAverage, txtCourseOverallAverage, txtCourseInfo;
    public Button buttonUpdateGrade;

    public RegisterCourseModel model;
    public void setModel( RegisterCourseModel model)
    {
        this.model = model;
        initializeLists();

    }

    public void initializeLists() {
        InitializeStudentList();
        InitializeSemesterList();
        InitializeStudentGradeList();
    }


    private void InitializeSemesterList() {

        Semester semester = new Semester(-1, "All Semesters", (short) 0);
        semesterList.add(semester);
        semesterList.addAll(model.getAllSemester());

        comboBoxSemester.setItems(semesterList);
        comboBoxSemester.getSelectionModel().selectFirst();

        comboBoxSemester.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldSelected, newSelected) -> {

                    if (newSelected != null) {
                        int semesterId = ((Semester) newSelected).getId();
                        int studentId = ((Student) tableViewStudent.getSelectionModel().getSelectedItem()).getId();
                        UpdateStudentGradeInfo(studentId, semesterId);
                    }
                }
        );
    }

    public void InitializeStudentList() {
        studentList.addAll(model.getAllStudent());

        //set columns content
        studentColumnName.setCellValueFactory(
                new PropertyValueFactory<Student, String>("name")
        );
        studentColumnLastName.setCellValueFactory(
                new PropertyValueFactory<Student, String>("lastName")
        );
        studentColumnCity.setCellValueFactory(
                new PropertyValueFactory<Student, String>("city")
        );

        tableViewStudent.setItems(studentList);

        tableViewStudent.getSelectionModel().selectedItemProperty().addListener(
                ((observableValue, oldSelected, newSelected) ->
                {
                    if (newSelected != null) {
                        int studentId = ((Student) newSelected).getId();
                        int semesterId = ((Semester) comboBoxSemester.getValue()).getId();
                        UpdateStudentGradeInfo(studentId, semesterId);
                    }
                })
        );

    }

    private void InitializeStudentGradeList() {
        //set columns content
        gradeColumnCourse.setCellValueFactory(
                new PropertyValueFactory<StudentGrade, String>("CourseName")
        );
        gradeColumnTeacher.setCellValueFactory(
                new PropertyValueFactory<StudentGrade, String>("TeacherName")
        );
        gradeColumnECTS.setCellValueFactory(
                new PropertyValueFactory<Student, String>("ECTS")
        );
        gradeColumnGrade.setCellValueFactory(
                new PropertyValueFactory<Student, String>("grade")
        );

        tableViewGrade.setItems(gradeList);




        tableViewGrade.getSelectionModel().selectedItemProperty().addListener(
                ((observableValue, oldSelected, newSelected) ->
                {

                    if (newSelected != null) {
                        int courseId = ((StudentGrade) newSelected).getCourseId();
                        int semesterId = ((StudentGrade) newSelected).getSemesterId();
                        UpdateCourseGradeInfo(courseId, semesterId);
                    }
                })
        );

    }

    private void UpdateStudentGradeInfo(int studentId, int semesterId) {
        gradeList.clear();
        txtOverallAverage.clear();
        txtSemesterAverage.clear();
        txtCourseOverallAverage.clear();
        txtCourseSemesterAverage.clear();
        txtCourseInfo.clear();



        gradeList.addAll(model.getRegisteredCoursed(studentId, semesterId));
        Float overallAvg = model.getOverallAverage(studentId);
        Float semesterAvg = model.getSemeterAverage(studentId, semesterId);

        if (overallAvg != null)
            txtOverallAverage.setText(Float.toString(overallAvg));
        if (semesterAvg != null)
            txtSemesterAverage.setText(Float.toString(semesterAvg));

        buttonUpdateGrade.setDisable(gradeList.isEmpty());
    }

    private void UpdateCourseGradeInfo(int courseId, int semesterId) {
        txtCourseOverallAverage.clear();
        txtCourseSemesterAverage.clear();

        Float overallAvg = model.getOverallCourseAverage(courseId);
        Float semesterAvg = model.getSemeterCourseAverage(courseId, semesterId);

        if (overallAvg != null)
            txtCourseOverallAverage.setText(Float.toString(overallAvg));
        if (semesterAvg != null)
            txtCourseSemesterAverage.setText(Float.toString(semesterAvg));
    }

    public void UpdateGrade(ActionEvent actionEvent) {
        Stage stage = new Stage();
        StudentGradeController controller = new StudentGradeController(model, (StudentGrade)tableViewGrade.getSelectionModel().getSelectedItem());
        StudentGradeView view = new StudentGradeView(controller);

        stage.setTitle("Update Student Grade Form");
        stage.setScene(new Scene(view.asParent(), 300, 300));
        stage.show();

    }
}
