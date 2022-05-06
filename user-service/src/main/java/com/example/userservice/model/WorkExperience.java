package com.example.userservice.model;

import java.util.Date;

public class WorkExperience {
    public String companyName;
    public String city;
    public String jobTitle;
    public Date startDate;
    public Date endDate;

    public WorkExperience() {}

    public WorkExperience(String companyName, String city, String jobTitle,
                          Date startDate, Date endDate) {
        this.companyName = companyName;
        this.city = city;
        this.jobTitle = jobTitle;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
