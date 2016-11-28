package com.wgl.bored.fragment2;

import android.support.v7.widget.LinearLayoutManager;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wgl.bored.R;
import com.wgl.bored.adapter.FragmentAdapter;
import com.wgl.bored.base.BasePersenterImp;
import com.wgl.bored.defaultView.DialogManager;
import com.wgl.bored.main.MainActivity;
import com.wgl.bored.main.mainfragment.MainModle;
import com.wgl.mvpretrofit.mvp.p.FragmentPresenter;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Biligle.
 */

public class Fragment2 extends FragmentPresenter<FragmentView2> implements BasePersenterImp.IListener{
    private MainActivity mainActivity;
    private List<MainModle> data = new ArrayList<>();
    private FragmentAdapter adapter;//xRecycleView的适配器
//    private int time = 0;//页数，0代表第一页，以此类推

    @Override
    protected Class<FragmentView2> getViewClass() {
        return FragmentView2.class;
    }

    /**
     * 初始化设置，只一次
     */
    @Override
    protected void setInit() {
        super.setInit();
        mainActivity = (MainActivity)getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        //设置xRecyclerView布局的方向
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        baseView.xRecyclerView.setLayoutManager(linearLayoutManager);
        //设置刷新风格
        baseView.xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        //设置加载更多风格
        baseView.xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        //设置下拉出现的图标
        baseView.xRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);
        toNet();//访问网络
        adapter = new FragmentAdapter( mainActivity);
        baseView.xRecyclerView.setAdapter(adapter);//只执行一次setAdapter
    }

    /**
     * 监听事件
     */
    @Override
    protected void setListener() {
        super.setListener();
        //实现刷新和加载接口
        baseView.xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
//                time = 0;
                data.clear();//清楚数据
                toNet();
                adapter.notifyDataSetChanged();
                baseView.xRecyclerView.refreshComplete();//刷新完成
            }

            @Override
            public void onLoadMore() {
                //由于Retrofit访问网络的，@GET{"{path}"}失败（原因，url中有加密，解析失败了），无法加载更多
//                time+=50;
//                toNet();
                baseView.xRecyclerView.loadMoreComplete();//加载完成
                baseView.xRecyclerView.setNoMore(true);//这里我直接默认，没有更多内容可加载了
            }
        });
    }

    /**
     * 访问网络
     */
    private void toNet(){
        FragmentNet2 net = new FragmentNet2();
        net.setListener(this);
        net.toNet(mainActivity.getIPublicNetManager(true),true,"正在加载...");
    }

    @Override
    public void onSuccess(Object bean) {
        data = (List<MainModle>)bean;
        adapter.addData(data);
        if(data.size()>0){
            adapter.notifyDataSetChanged();
        }else{
//            if(time > 0){
//                adapter.notifyDataSetChanged();
//                baseView.xRecyclerView.setNoMore(true);//没有更多了，加载完成
//            }
        }
    }

    @Override
    public void onFailure(String str, Throwable t) {

    }

    @Override
    public void showLoadingDialog(String str) {
        DialogManager.getInstance().showProgressDialog(mainActivity);
    }

    @Override
    public void stopLoadingDialog() {
        DialogManager.getInstance().dissMissProgressDialog();
    }
}
