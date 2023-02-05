package com.basetechz.quizo.Home.popularCourse.Video;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.basetechz.quizo.CheatsFragment;
import com.basetechz.quizo.Home.popularCourse.Video.VideoPlayLIstFragment;
import com.basetechz.quizo.ProgramFragment;

public class ViewPagerLessonAdapter extends FragmentPagerAdapter {

    FragmentManager fm;
    String courseId;
    public ViewPagerLessonAdapter(@NonNull FragmentManager fm,String courseId) {
        super(fm);
        this.courseId = courseId;
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            Bundle bundle = new Bundle();
            bundle.putString("courseId",courseId);
            VideoPlayLIstFragment vpLFragment = new VideoPlayLIstFragment();
            vpLFragment.setArguments(bundle);

            return  vpLFragment;

        }else if(position==1){
            return  new ProgramFragment();
        }else {
            return new CheatsFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return "PlayList";
        }else if(position ==1){
            return "Programs";
        }else{
            return "Cheats";
        }
    }
}
