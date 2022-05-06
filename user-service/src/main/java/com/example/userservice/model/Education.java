package com.example.userservice.model;

import java.util.Date;

public class Education {
    public String institutionType;
    public String institutionName;
    public String title;
    public Date startDate;
    public Date endDate;

    public Education(){}
    public Education(String institutionType, String institutionName,
                     String title, Date startDate, Date endDate){
        this.institutionType = institutionType;
        this.institutionName = institutionName;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
