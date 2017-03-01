package com.captumia.network;

import com.captumia.data.*;
import com.captumia.data.PackageSubscription;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface RestApiClient {
    String DOMAIN = "www.captumia.com";
    String BASE_URL = "https://" + DOMAIN + "/";

    @GET("wp-json/wp/v2/job_listing")
    Call<List<Post>> getPostsByCategory(@Query("job_listing_category") int categoryId,
                                        @Query("page") int page,
                                        @Query("per_page") int itemsPerPage);

    @GET("wp-json/wp/v2/job_listing")
    Call<List<Post>> getPostsByTag(@Query("job_listing_tag") int tagId,
                                   @Query("page") int page,
                                   @Query("per_page") int itemsPerPage);

    @GET("wp-json/wp/v2/job_listing")
    Call<List<Post>> searchPosts(@Query("search") String search,
                                 @Query("page") int page,
                                 @Query("per_page") int itemsPerPage);

    @GET("wp-json/wp/v2/job_listing_category?parent=0&orderby=count&per_page=6")
    Call<List<Category>> getTopRootCategories();

    @GET("wp-json/wp/v2/job_listing_category?orderby=count")
    Call<List<Category>> getCategories(@Query("parent") int parent,
                                       @Query("page") int page,
                                       @Query("per_page") int itemsPerPage);

    @GET("wp-json/wp/v2/job_listing_tag?per_page=50")
    Call<List<Tag>> getTags();

    @GET("wp-json/wp/v2/job_listing_region?per_page=50")
    Call<List<Region>> getRegions();

    @GET("wp-json/wp/v2/product?product_type=11&per_page=50")
    Call<List<PackageSubscription>> getPackages();

    @GET("wp-json/custom/get_user_packages")
    Call<List<PackageSubscription>> getUserPackages();

    @GET("wp-json/wp/v2/job_listing_category?orderby=count&per_page=50")
    Call<List<Category>> getCategories();

    @FormUrlEncoded
    @POST("wp-json/jwt-auth/v1/token")
    Call<TokenRequestData> getToken(@Field("username") String login,
                                    @Field("password") String password);

    @Multipart
    @POST("wp-json/custom/create_post")
    Call<Post> createService(@Part("title") String title,
                             @Part("region") Integer region,
                             @Part("email") String email,
                             @Part("category") List<Integer> categories,
                             @Part("tags[]") List<Integer> tags,
                             @PartMap Map<String, RequestBody> operatingHours,
                             @Part MultipartBody.Part coverImage,
                             @Part MultipartBody.Part[] galleryImages,
                             @Part("description") String description,
                             @Part("company_website") String webSite,
                             @Part("phone") String phone,
                             @Part("company_video") String video);

    @FormUrlEncoded
    @POST("wp-json/custom/login")
    Call<User> login(@Field("username") String login,
                     @Field("password") String password);

    @GET("wp-json/wp/v2/users/me")
    Call<User> me();

    @GET("wp-json/custom/test")
    Call<ResponseBody> test();
    @GET("wp-json/custom/get_nonce")
    Call<Nonce> getNonce();
}
