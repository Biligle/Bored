package com.wgl.mvpretrofit.mvp.v;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

/**
 * 视图层代理的接口协议
 * @author Biligle
 */
public interface IDelegate {
    void create(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    View getRootView();
    View getBrotherView(int n);
    void initWidget();
}
