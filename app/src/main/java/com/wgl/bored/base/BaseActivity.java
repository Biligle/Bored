package com.wgl.bored.base;

import android.os.Bundle;

import com.wgl.bored.IPublicNetManager;
import com.wgl.bored.util.NetUtils;
import com.wgl.bored.util.ToastUtil;
import com.wgl.mvpretrofit.mvp.p.ActivityPresenter;
import com.wgl.mvpretrofit.mvp.v.IDelegate;

/**
 * 所有Activity的基类
 * @Author Biligle.
 */

public abstract class BaseActivity<T extends IDelegate> extends ActivityPresenter<T>{
    private BaseNet baseNet;
    private IPublicNetManager iPublicNetManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public IPublicNetManager getIPublicNetManager(boolean flagIsCache) {
        if (baseNet == null) {
            baseNet = new BaseNet();
        }
        if (iPublicNetManager == null) {
            iPublicNetManager = baseNet.getApiClient(IPublicNetManager.class, this.getCacheDir(), NetUtils.isConnected(this), flagIsCache);
        }
        return iPublicNetManager;
    }

    private long time;
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if(System.currentTimeMillis() - time >2000){
            time = System.currentTimeMillis();
            ToastUtil.getInstance().toastInCenter(this,"再按一次退出");
        }else{
            MyApplication.exit();
        }
        return false;
    }
}
