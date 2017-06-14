package POJO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohamed Fawzy on 3/31/2017.
 */
public class Question {

    @SerializedName("ID")
    int ID;
    @SerializedName("Q")
    String Q;
    @SerializedName("A")
    String A;
    @SerializedName("choiceA")
    String choiceA;
    @SerializedName("choiceB")
    String choiceB;
    @SerializedName("choiceC")
    String choiceC;
    @SerializedName("school_id")
    int school_id;
    @SerializedName("subject")
    String subject;
    @SerializedName("level")
    int level;

    public String getQ() {
        return Q;
    }

    public void setQ(String q) {
        Q = q;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getChoiceA() {
        return choiceA;
    }

    public void setChoiceA(String choiceA) {
        this.choiceA = choiceA;
    }

    public String getChoiceB() {
        return choiceB;
    }

    public void setChoiceB(String choiceB) {
        this.choiceB = choiceB;
    }

    public String getChoiceC() {
        return choiceC;
    }

    public void setChoiceC(String choiceC) {
        this.choiceC = choiceC;
    }

    public int getSchool_id() {
        return school_id;
    }

    public void setSchool_id(int school_id) {
        this.school_id = school_id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getID() {

        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
