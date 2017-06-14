package POJO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohamed Fawzy on 3/30/2017.
 */
public class Student {


    @SerializedName("ID")
    private int ID;
    @SerializedName("student_name")
    private String student_name;
    @SerializedName("school_id")
    private int school_id;
    @SerializedName("level")
    private int level;
    @SerializedName("score")
    private int score;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public int getSchool_id() {
        return school_id;
    }

    public void setSchool_id(int school_id) {
        this.school_id = school_id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
