package com.basetechz.quizo.Home.popularCourse;

public class PopularCourseModel {
    private String courseId,courseImg,courseName,courseLesson,courseCost,courseStar;

    public  PopularCourseModel(){

    }
    public PopularCourseModel(String courseId,String courseImg,String courseName,String courseLesson,String courseCost,String courseStar){
        this.courseId = courseId;
        this.courseImg = courseImg;
        this.courseName = courseName;
        this.courseLesson = courseLesson;
        this.courseCost = courseCost;
        this.courseStar = courseStar;

    }

    public String getCourseImg() {
        return courseImg;
    }

    public void setCourseImg(String courseImg) {
        this.courseImg = courseImg;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseLesson() {
        return courseLesson;
    }

    public void setCourseLesson(String courseLesson) {
        this.courseLesson = courseLesson;
    }

    public String getCourseCost() {
        return courseCost;
    }

    public void setCourseCost(String courseCost) {
        this.courseCost = courseCost;
    }

    public String getCourseStar() {
        return courseStar;
    }

    public void setCourseStar(String courseStar) {
        this.courseStar = courseStar;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
