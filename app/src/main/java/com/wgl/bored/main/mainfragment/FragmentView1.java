package com.wgl.bored.main.mainfragment;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wgl.bored.R;
import com.wgl.mvpretrofit.mvp.v.AppDelegate;

/**
 * @Author Biligle.
 * 首页FragmentView
 */

public class FragmentView1 extends AppDelegate {

    public XRecyclerView xRecyclerView;

    /**
     * 获取根布局
     * @return
     */
    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_view1;
    }

    /**
     * 获取所有相关布局（弹出框之类的）
     * @return
     */
    @Override
    public int[] getBrotherLayoutId() {
        return new int[0];
    }

    /**
     * 初始化布局中控件
     */
    @Override
    public void initWidget() {
        xRecyclerView = get(R.id.list_content);
    }
}
