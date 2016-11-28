package com.wgl.sunshan.base;

import com.wgl.sunshan.IPublicNetManager;
import com.wgl.sunshan.base.BaseModle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 联网基类（剪切到自己项目中）
 * @Author Biligle.
 */
public abstract class BasePersenterImp<T extends BaseModle> {

    public IListener mListener;
    public Call mCall;

    public void toNet(IPublicNetManager manager, boolean flagDialog, String dialogStr) {
        if (mCall != null && mCall.isExecuted()) {
            mCall.cancel();
            mCall = null;
        }
        mCall = getCall(manager);

        if (mCall == null) {
            return;
        }
        if (flagDialog) {
            mListener.showLoadingDialog(dialogStr);
        }
        mCall.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        //根据返回数据判断
                        if (response.body().retCode) {
                            mListener.onSuccess(response.body());
                        } else {
                            if (response.body().retMsg != null) {
                                mListener.onFailure(response.body().retMsg,null);
                            } else {
                                mListener.onFailure("网络异常，请检查网络",null);
                            }
                        }
                    }
                } else {
                    if (response.body() != null && response.body().retMsg != null) {
                        mListener.onFailure(response.body().retMsg,null);
                    } else {
                        mListener.onFailure("网络异常，请检查网络",null);
                    }

                }
                mListener.stopLoadingDialog();
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                mListener.onFailure("网络异常，请检查网络",t);
                mListener.stopLoadingDialog();
            }

        });

    }

    public abstract Call getCall(IPublicNetManager manager);

    public abstract void setListener(IListener listener);

    public interface IListener<T> {
        void onSuccess(T bean);

        void onFailure(String str, Throwable t);

        void showLoadingDialog(String str);

        void stopLoadingDialog();
    }

}
