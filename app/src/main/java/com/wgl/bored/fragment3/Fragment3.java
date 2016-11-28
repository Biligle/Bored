package com.wgl.bored.fragment3;

import com.wgl.mvpretrofit.mvp.p.FragmentPresenter;

/**
 * @Author Biligle.
 */

public class Fragment3 extends FragmentPresenter<FragmentView3> {
    @Override
    protected Class<FragmentView3> getViewClass() {
        return FragmentView3.class;
    }
}
