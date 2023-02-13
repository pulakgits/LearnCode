package com.basetechz.quizo.Home.popularCourse.Video;

public class LessonModel {
    private String videoLessonTxt,videoLessonId,videoImage;
    private long views = 0;
    private long like = 0;
    private long viewCount = 0 ;

    public LessonModel(){

    }

    public LessonModel(String videoLessonTxt, String videoLessonId, String videoImage, long views, long like, long viewCount) {
        this.videoLessonTxt = videoLessonTxt;
        this.videoLessonId = videoLessonId;
        this.videoImage = videoImage;
        this.views = views;
        this.like = like;
        this.viewCount = viewCount;
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

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public long getLike() {
        return like;
    }

    public void setLike(long like) {
        this.like = like;
    }

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }

    public String getVideoImage() {
        return videoImage;
    }

    public void setVideoImage(String videoImage) {
        this.videoImage = videoImage;
    }
}
