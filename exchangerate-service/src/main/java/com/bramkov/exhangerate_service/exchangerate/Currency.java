package com.bramkov.exhangerate_service.exchangerate;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Currency {

    private int code;
    private String lateCode;
    private String nameRate;
    private Double course;

    public Currency() {}

    public Currency(int code, String lateCode, String nameRate, Double course) {
        this.code = code;
        this.lateCode = lateCode;
        this.nameRate = nameRate;
        this.course = course;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLateCode() {
        return lateCode;
    }

    public void setLateCode(String lateCode) {
        this.lateCode = lateCode;
    }

    public String getNameRate() {
        return nameRate;
    }

    public void setNameRate(String nameRate) {
        this.nameRate = nameRate;
    }

    public Double getCourse() {
        return course;
    }

    public void setCourse(Double course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency that = (Currency) o;
        return code == that.code && Objects.equals(lateCode, that.lateCode) && Objects.equals(nameRate, that.nameRate) && Objects.equals(course, that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, lateCode, nameRate, course);
    }
}
