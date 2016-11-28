package com.wgl.bored;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * 所有网络层接口定义
 * @Author Biligle.
 */
public interface IPublicNetManager {
    /**
     * 行尸走肉吧
     * @return
     */
    @GET(NetUrl.DEAD_WALKING)
    Call<ResponseBody> deadWallking();

    /**
     * 文艺吧
     * @return
     */
    @GET(NetUrl.ART)
    Call<ResponseBody> art();

}
