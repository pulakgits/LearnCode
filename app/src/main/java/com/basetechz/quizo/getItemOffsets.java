package com.basetechz.quizo;

import android.graphics.Rect;
import android.view.View;

import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

public interface getItemOffsets extends LifecycleOwner {
    void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state);
}
