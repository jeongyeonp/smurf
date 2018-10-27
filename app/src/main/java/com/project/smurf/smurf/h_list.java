package com.project.smurf.smurf;

import java.util.Date;

public class h_list {
    private String foodname;
    private String hdate;

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getHdate() { return hdate; }

    public void setHdate(String hdate) { this.hdate = hdate; }

    public h_list(String foodname, String hdate){
        this.foodname = foodname;
        this.hdate = hdate;
    }
}


