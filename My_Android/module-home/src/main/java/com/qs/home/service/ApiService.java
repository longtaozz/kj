package com.qs.home.service;

import com.qs.home.entity.AttentionEntity;

import java.util.Map;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.http.BaseResponse;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {

    @GET("action/apiv2/banner?catalog=1")
    Observable<BaseResponse<AttentionEntity>> demoGet();

    /**
     * Demo 关注列表
     *
     * @param catalog 列表数
     * @return AttentionEntity
     */
    @GET("action/apiv2/banner")
    Observable<BaseResponse<AttentionEntity>> getDemoAttention(@Query("catalog") int catalog);

    /**
     * Demo 关注列表
     *
     * @param map 参数
     * @return AttentionEntity
     */
    @GET("action/apiv2/banner")
    Observable<BaseResponse<AttentionEntity>> getDemoAttention(@QueryMap Map<String, String> map);

    /**
     * Demo 自定义拼接
     *
     * @param start 参数
     * @return AttentionEntity
     */
    @GET("action/apiv2/banner/{start}/{count}")
    Observable<BaseResponse<AttentionEntity>> getDemoAttention2(@Path("start") int start);

    /**
     * Demo post 单个参数
     *
     * @param token token
     * @param id    id
     * @return AttentionEntity
     */
    @FormUrlEncoded
    @POST("请求地址")
    Observable<BaseResponse<AttentionEntity>> getAttetionInfo(@Field("token") String token, @Field("id") int id);

    /**
     * Demo post map参数
     *
     * @param map 参数
     * @return AttentionEntity
     */
    @FormUrlEncoded
    @POST("请求地址")
    Observable<BaseResponse<AttentionEntity>> getAttetionInfo(@FieldMap Map<String, String> map);
}
