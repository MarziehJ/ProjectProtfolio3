package model;

public class Student {
    private int id;
    private String name;
    private String lastName;
    private String city;

    public Student(int id, String name, String lastName, String city) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCity() {
        return city;
    }

    public int getId() {
        return id;
    }

    public static class StudentDAL {


    }
}
