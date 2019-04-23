package com.winterchen.model;

import java.util.ArrayList;
import java.util.List;

public class Performance {
    Long id;
    String quarter;
    String month;
    List<PerformanceEmployeeCheck> performanceEmployeeChecks=new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<PerformanceEmployeeCheck> getPerformanceEmployeeChecks() {
        return performanceEmployeeChecks;
    }

    public void setPerformanceEmployeeChecks(List<PerformanceEmployeeCheck> performanceEmployeeChecks) {
        this.performanceEmployeeChecks = performanceEmployeeChecks;
    }
}
