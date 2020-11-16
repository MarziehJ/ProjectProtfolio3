package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Semester;
import model.RegisterCourseModel;
import model.Student;
import model.StudentGrade;
import view.StudentGradeView;

import java.net.UnknownHostException;
import java.util.List;

public class Controller {
    public ObservableList<Student> studentList = FXCollections.observableArrayList();
    public ObservableList<Semester> semesterList = FXCollections.observableArrayList();
    public ObservableList<StudentGrade> gradeList = FXCollections.observableArrayList();

    private ObservableList<XYChart.Series> studentDataSeries = FXCollections.observableArrayList();
    private ObservableList<XYChart.Data> studentData = FXCollections.observableArrayList();

    private ObservableList<XYChart.Series> courseDataSeries = FXCollections.observableArrayList();
    private ObservableList<XYChart.Data> courseData = FXCollections.observableArrayList();
    private ObservableList<XYChart.Data> courseAvgData = FXCollections.observableArrayList();

    public BarChart chartStudent;
    public LineChart chartCourse;

    public TableColumn studentColumnName, studentColumnLastName, studentColumnCity;
    public TableColumn gradeColumnCourse, gradeColumnECTS, gradeColumnGrade, gradeColumnTeacher, gradeColumnSemester;

    public TableView tableViewStudent;
    public TableView tableViewGrade;
    public ComboBox comboBoxSemester;
    public TextField txtSemesterAverage, txtOverallAverage, txtCourseSemesterAverage, txtCourseOverallAverage, txtCourseInfo;
    public Button buttonUpdateGrade;


    public RegisterCourseModel model;

    public void setModel(RegisterCourseModel model) {
        this.model = model;
        initializeLists();

    }

    public void initializeLists() {
        InitializeStudentList();
        InitializeSemesterList();
        InitializeStudentGradeList();
        InitializeChartsData();
        tableViewStudent.getSelectionModel().selectFirst();
    }

    private void InitializeChartsData() {

        studentDataSeries.add(new XYChart.Series("Grades", studentData));
        chartStudent.setData(studentDataSeries);

        courseDataSeries.add(new XYChart.Series("Grades", courseData));
        courseDataSeries.add(new XYChart.Series("Average", courseAvgData));
        chartCourse.setData(courseDataSeries);
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
        gradeColumnSemester.setCellValueFactory(
                new PropertyValueFactory<StudentGrade, String>("semesterName")
        );
        gradeColumnTeacher.setCellValueFactory(
                new PropertyValueFactory<StudentGrade, String>("TeacherName")
        );
        gradeColumnECTS.setCellValueFactory(
                new PropertyValueFactory<StudentGrade, String>("ECTS")
        );
        gradeColumnGrade.setCellValueFactory(
                new PropertyValueFactory<StudentGrade, String>("grade")
        );


        tableViewGrade.setItems(gradeList);


        tableViewGrade.getSelectionModel().selectedItemProperty().addListener(
                ((observableValue, oldSelected, newSelected) ->
                {

                    if (newSelected != null) {
                        String courseInfo = ((StudentGrade) newSelected).getCourseInfo();
                        int courseId = ((StudentGrade) newSelected).getCourseId();
                        int semesterId = ((StudentGrade) newSelected).getSemesterId();
                        UpdateCourseGradeInfo(courseId, semesterId, courseInfo);
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


        gradeList.addAll(model.getRegisteredCoursed(studentId, semesterId, -1));
        Float overallAvg = model.getOverallAverage(studentId);
        Float semesterAvg = model.getSemeterAverage(studentId, semesterId);

        if (overallAvg != null)
            txtOverallAverage.setText(Float.toString(overallAvg));
        if (semesterAvg != null)
            txtSemesterAverage.setText(Float.toString(semesterAvg));

        tableViewGrade.getSelectionModel().selectFirst();
        buttonUpdateGrade.setDisable(gradeList.isEmpty());

        UpdateStudentStatistics(studentId);

    }

    private void UpdateStudentStatistics(int studentId) {
        if (studentId != -1) {
            List<StudentGrade> allStudentGrads = model.getRegisteredCoursed(studentId, -1, -1);
            studentData.clear();


            for (StudentGrade studentGrade : allStudentGrads) {
                if (studentGrade.getGrade() != null)
                    studentData.add(new XYChart.Data(studentGrade.getCourseName(), studentGrade.getGrade()));
            }
        }

    }

    private void UpdateCourseStatistics(int courseId, Float average) {
        if (courseId != -1) {
            List<StudentGrade> allStudentGrads = model.getRegisteredCoursed(-1, -1, courseId);


            courseData.clear();
            courseAvgData.clear();

           for (StudentGrade studentGrade : allStudentGrads) {
                if (studentGrade.getGrade() != null)
                    courseData.add(new XYChart.Data(studentGrade.getStudentName(), studentGrade.getGrade()));
                if (average != null)
                    courseAvgData.add(new XYChart.Data(studentGrade.getStudentName(), average));
            }
        }

    }

    private void UpdateCourseGradeInfo(int courseId, int semesterId, String courseInfo) {
        txtCourseOverallAverage.clear();
        txtCourseSemesterAverage.clear();
        txtCourseInfo.clear();

        Float overallAvg = model.getOverallCourseAverage(courseId);
        Float semesterAvg = model.getSemeterCourseAverage(courseId, semesterId);
        txtCourseInfo.setText(courseInfo);


        if (overallAvg != null)
            txtCourseOverallAverage.setText(Float.toString(overallAvg));
        if (semesterAvg != null)
            txtCourseSemesterAverage.setText(Float.toString(semesterAvg));
        UpdateCourseStatistics(courseId, overallAvg);

    }


    public void UpdateGrade(ActionEvent actionEvent) {

        StudentGrade studentGrade = (StudentGrade) tableViewGrade.getSelectionModel().getSelectedItem();
        Stage stage = new Stage();
        StudentGradeController controller = new StudentGradeController(model, studentGrade);
        StudentGradeView view = new StudentGradeView(controller);
        controller.setView(view);

        stage.setTitle("Update Student Grade Form");
        stage.setScene(new Scene(view.asParent(), 340, 250));
        stage.show();
        stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                UpdateStudentGradeInfo(studentGrade.getStudentId(), ((Semester) comboBoxSemester.getValue()).getId());
            }
        });


    }
}
