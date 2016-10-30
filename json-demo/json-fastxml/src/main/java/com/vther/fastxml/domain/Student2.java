package com.vther.fastxml.domain;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Student2 {
    private String name;
    private int age;
    private String school;

    private String teacher;

    // 默认格式化成为：1477824317593
    // or DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
    // myObjectMapper.setDateFormat(df);
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
    private Date entranceTime;

    @Override
    public String toString() {
        return "Student2{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", school='" + school + '\'' +
                ", teacher='" + teacher + '\'' +
                ", entranceTime=" + entranceTime +
                '}';
    }

    public Date getEntranceTime() {
        return entranceTime;
    }

    public void setEntranceTime(Date entranceTime) {
        this.entranceTime = entranceTime;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
