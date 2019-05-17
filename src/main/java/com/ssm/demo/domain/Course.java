package com.ssm.demo.domain;

public class Course {
    private Long id;
    private String course;
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id="+id+
                ",course="+course+",userId="+userId+"}";
    }
}
