package com.example.pilgrim;

import java.io.Serializable;

public class PersonalRD implements Serializable {
    public String name;
    public String my_choice1;
    public String my_choice2;
    public String my_choice3;
    public String my_choice4;
    public String my_choice5;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMy_choice1() {
        return my_choice1;
    }

    public void setMy_choice1(String my_choice1) {
        this.my_choice1 = my_choice1;
    }

    public String getMy_choice2() {
        return my_choice2;
    }

    public void setMy_choice2(String my_choice2) {
        this.my_choice2 = my_choice2;
    }

    public String getMy_choice3() {
        return my_choice3;
    }

    public void setMy_choice3(String my_choice3) {
        this.my_choice3 = my_choice3;
    }

    public String getMy_choice4() {
        return my_choice4;
    }

    public void setMy_choice4(String my_choice4) {
        this.my_choice4 = my_choice4;
    }

    public String getMy_choice5() {
        return my_choice5;
    }

    public void setMy_choice5(String my_choice5) {
        this.my_choice5 = my_choice5;
    }
}
