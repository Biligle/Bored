package com.wgl.bored.main.mainfragment;

import com.wgl.bored.IPublicNetManager;
import com.wgl.bored.base.BasePersenterImp;

import retrofit2.Call;

/**
 * @Author Biligle.
 */

public class MainModleNet extends BasePersenterImp<MainModle> {

    @Override
    public Call getCall(IPublicNetManager manager) {
        return manager.deadWallking();
    }

    @Override
    public void setListener(IListener listener) {
        mListener = listener;
    }

    @Override
    public Class<MainModle> getT() {
        return MainModle.class;
    }
}
