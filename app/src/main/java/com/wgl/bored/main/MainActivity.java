package com.wgl.bored.main;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import com.wgl.bored.R;
import com.wgl.bored.base.BaseActivity;
import com.wgl.bored.defaultView.MyTabWidget;
import com.wgl.bored.fragment2.Fragment2;
import com.wgl.bored.fragment3.Fragment3;
import com.wgl.bored.fragment4.Fragment4;
import com.wgl.bored.main.mainfragment.Fragment1;

public class MainActivity extends BaseActivity<MainView> implements MyTabWidget.OnTabSelectedListener{

    private Fragment fragment1,fragment2,fragment3,fragment4;
    private int mIndex = 0;

    /**
     * 父类ActivityPresenter的onCreate方法
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //给底部导航添加点击监听
        baseView.myTabWidget.setOnTabSelectedListener(this);
        //默认显示第一页
        onTabSelected(mIndex);
    }

    /**
     * 当执行onResume方法时，防止底部导航错乱，重新加载当前页
     */
    @Override
    protected void onResume() {
        super.onResume();
        onTabSelected(mIndex);
        baseView.myTabWidget.setTabsDisplay(this, mIndex);
    }

    /**
     * 实例化MainView
     * @return
     */
    @Override
    protected Class<MainView> getViewClass() {
        return MainView.class;
    }

    /**
     * 实现自定义底部导航的点击事件
     * @param index
     */
    @Override
    public void onTabSelected(int index) {
        FragmentTransaction transaction = baseView.fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index){
            case 0:
                if (null == fragment1) {
                    fragment1 = new Fragment1();
                    transaction.add(R.id.frame, fragment1);
                } else {
                    transaction.show(fragment1);
                }
                baseView.title.setText("行尸走肉");
                break;
            case 1:
                if (null == fragment2) {
                    fragment2 = new Fragment2();
                    transaction.add(R.id.frame, fragment2);
                } else {
                    transaction.show(fragment2);
                }
                baseView.title.setText("文艺范儿");
                break;
            case 2:
                if (null == fragment3) {
                    fragment3 = new Fragment3();
                    transaction.add(R.id.frame, fragment3);
                } else {
                    transaction.show(fragment3);
                }
                baseView.title.setText("有趣视频");
                break;
            case 3:
                if (null == fragment4) {
                    fragment4 = new Fragment4();
                    transaction.add(R.id.frame, fragment4);
                } else {
                    transaction.show(fragment4);
                }
                baseView.title.setText("其他");
                break;
        }
        transaction.commitAllowingStateLoss();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (null != fragment1) {
            transaction.hide(fragment1);
        }
        if (null != fragment2) {
            transaction.hide(fragment2);
        }
        if (null != fragment3) {
            transaction.hide(fragment3);
        }
        if (null != fragment4) {
            transaction.hide(fragment4);
        }
    }
}
