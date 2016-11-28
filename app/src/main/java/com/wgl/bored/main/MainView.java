package com.wgl.bored.main;

import android.app.FragmentManager;
import android.widget.TextView;

import com.wgl.bored.R;
import com.wgl.bored.defaultView.MyTabWidget;
import com.wgl.mvpretrofit.mvp.v.AppDelegate;

/**
 * @Author Biligle.
 */

public class MainView extends AppDelegate {

    public FragmentManager fragmentManager;
    public MyTabWidget myTabWidget;
    public TextView title;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public int[] getBrotherLayoutId() {
        return new int[0];
    }

    @Override
    public void initWidget() {
        fragmentManager = getActivity().getFragmentManager();
        myTabWidget = get(R.id.myTab);
        title = get(R.id.title);
    }
}
