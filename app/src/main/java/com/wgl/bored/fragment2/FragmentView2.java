package com.wgl.bored.fragment2;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wgl.bored.R;
import com.wgl.mvpretrofit.mvp.v.AppDelegate;

/**
 * @Author Biligle.
 */

public class FragmentView2 extends AppDelegate {

    public XRecyclerView xRecyclerView;

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_view2;
    }

    @Override
    public int[] getBrotherLayoutId() {
        return new int[0];
    }

    @Override
    public void initWidget() {
        xRecyclerView = get(R.id.list_art);
    }
}
