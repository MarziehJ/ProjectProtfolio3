package model;

public class Semester {
    private int id;
    private String name;
    private short year;

    public Semester(int id, String name, short year) {
        this.id = id;
        this.name = name;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public short getYear() {
        return year;
    }

    @Override
    public String toString() {
        if (id != -1)
            return String.format("%s %s", name, year);
        else
            return name;
    }
}
