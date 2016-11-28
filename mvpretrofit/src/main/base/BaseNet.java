package com.wgl.sunshan.base;

import com.wgl.sunshan.IPublicNetManager;
import com.wgl.sunshan.NetUrl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit设置(剪切到自己项目中)
 * @Author Biligle.
 */
public class BaseNet {
    /**
     * 设置缓存 10M
     */
    public static final long HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 10 * 1024 * 1024;
    /**
     * 是否连网
     */
    private boolean flagToNet;

    private int dateTime= 10000;

    /**
     * 设置缓存
     * @param intance     net
     * @param baseDir     cache address
     * @param flagToNet   is to net
     * @param flagIsCache is  cache or timeout
     * @return interface
     */
    public IPublicNetManager getApiClient(Class<IPublicNetManager> intance, File baseDir, boolean flagToNet, boolean flagIsCache){
        this.flagToNet = flagToNet;
        Retrofit retrofit;
        if (flagIsCache) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(NetUrl.BASEURL)
                    .client(getOkHttpClient(baseDir))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } else {//设置client添加头部统一格式
            retrofit = new Retrofit.Builder()
                    .baseUrl(NetUrl.BASEURL)
                    .client(setNotCacheOkHttp())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }


        return retrofit.create(intance);
    }

    // 缓存根目录，由这里推荐 -> http://stackoverflow.com/a/32752861/400717.
// 小心可能为空，参考下面两个链接
// https://groups.google.com/d/msg/android-developers/-694j87eXVU/YYs4b6kextwJ 和
// http://stackoverflow.com/q/4441849/400717.
//  // final  File baseDir = context.getCacheDir();
    public OkHttpClient getOkHttpClient(File baseDir){
        OkHttpClient client = null;
        if (baseDir != null) {
            //设置缓存路径
            File httpCacheDirectory = new File(baseDir, "responses");
            //   LogUtils.e("网络缓存路径",httpCacheDirectory.getAbsolutePath());
            //设置缓存 10M
            Cache cache = new Cache(httpCacheDirectory, HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);
            //创建OkHttpClient，并添加拦截器和缓存代码
            client = new OkHttpClient.Builder()
                    ./*addNetwork*/addInterceptor(interceptor)
                    .connectTimeout(dateTime, TimeUnit.MILLISECONDS)
                    .readTimeout(dateTime, TimeUnit.MILLISECONDS)
                    .writeTimeout(dateTime, TimeUnit.MILLISECONDS)
                    .cache(cache)
                    .build();

        }
        return client;
    }
    public OkHttpClient setNotCacheOkHttp(){
        OkHttpClient client = null;

            //创建OkHttpClient，并添加拦截器和缓存代码
            client = new OkHttpClient.Builder()
                    ./*addNetwork*/addInterceptor(interceptorNotCache)
                    .connectTimeout(dateTime, TimeUnit.MILLISECONDS)
                    .readTimeout(dateTime, TimeUnit.MILLISECONDS)
                    .writeTimeout(dateTime, TimeUnit.MILLISECONDS)
                    .build();
        return client;
    }
    /**
     * 拦截器(离线可以缓存，在线就获取最新数据)
     */
    Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            int time;
            if (flagToNet) {
                //     KLog.e("返回数据--", /*request + "    " +*/ response.body().string() + "  ");
            }
            if (flagToNet) {
                time =  0; // 有网络时 设置缓存超时时间0个小时  0*60
            } else {
                time = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
            }
            request = request.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + time)
//                    .addHeader("channel", "2")//添加header
                    .build();
            return chain.proceed(request);
        }
    };

    /**
     * 拦截器(不适用缓存)
     */
    Interceptor interceptorNotCache = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!flagToNet) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
//                        .addHeader("channel", "2")
                        .build();
            }
            Response response = chain.proceed(request);

            return response;

        }
    };

    /**
     * @description response转为json输出
     * 打印Log 响应体异常
     * http://stackoverflow.com/questions/27922703/accessing-body-string-of-an-okhttp-response-twice-results-in-illegalstateexcepti
     * 一个OkHttp响应访问体中的字符串两次导致IllegalStateException异常：关闭
     */
      /*      Logger.e(String.format("接收响应: [%s] %.1fms%n%sBody:%s",
                    responseLog.request().url(), (t2 - t1) / 1e6d, responseLog.headers(),extractBody( responseLog)));*/
    private String extractBody(Response response) {

        try {
            ResponseBody responseBody = response.body();
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            String responseBodyString = buffer.clone().readString(Charset.forName("UTF-8"));
            return responseBodyString;
        } catch (IOException e) {
            e.printStackTrace();
            return "response error: " + e.getMessage().toString();
        }
    }
}