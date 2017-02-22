package com.example.questionappex.model;


/**
 * Created by daniel on 2015/8/3.
 */
//@DatabaseTable(tableName = "tb_answer")
public class Choice {

   public enum State {
        ERROR, RIGHT, NORMAL
    }

    public static final String F_TITLE_ID = "f_titleID";
    public static final String f_ANSWER = "f_answer";

//  题目ID
    String titleID;
//    位置
    String position;
    String answer;
    State state=State.NORMAL;

    public Choice(String titleID, String position, String answer) {
        this.titleID = titleID;
        this.position = position;
        this.answer = answer;
        this.state = State.NORMAL;
    }


    public String getTitleID() {
        return titleID;
    }

    public void setTitleID(String titleID) {
        this.titleID = titleID;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }



    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }


    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Choice{" +
                "titleID='" + titleID + '\'' +
                ", position='" + position + '\'' +
                ", answer='" + answer + '\'' +
                ", state=" + state +
                '}';
    }
}
