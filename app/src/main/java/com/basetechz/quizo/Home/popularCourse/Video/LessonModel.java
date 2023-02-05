package com.basetechz.quizo.Home.popularCourse.Video;

public class LessonModel {
    private String videoLessonTxt,videoLessonId;

    public LessonModel(){

    }

    public LessonModel(String videoLessonTxt, String videoLessonId) {
        this.videoLessonTxt = videoLessonTxt;
        this.videoLessonId = videoLessonId;
    }

    public String getVideoLessonTxt() {
        return videoLessonTxt;
    }

    public void setVideoLessonTxt(String videoLessonTxt) {
        this.videoLessonTxt = videoLessonTxt;
    }

    public String getVideoLessonId() {
        return videoLessonId;
    }

    public void setVideoLessonId(String videoLessonId) {
        this.videoLessonId = videoLessonId;
    }
}
