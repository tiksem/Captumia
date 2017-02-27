package com.captumia.network;

import com.captumia.data.Category;
import com.captumia.data.Nonce;
import com.captumia.data.Post;
import com.captumia.data.Region;
import com.captumia.data.Tag;
import com.captumia.data.TokenRequestData;
import com.captumia.data.User;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface RestApiClient {
    String BASE_URL = "https://www.captumia.com/";

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

    @GET("wp-json/wp/v2/job_listing_category?orderby=count&per_page=50")
    Call<List<Category>> getCategories();

    @FormUrlEncoded
    @POST("wp-json/jwt-auth/v1/token")
    Call<TokenRequestData> getToken(@Field("username") String login,
                                    @Field("password") String password);

    @Multipart
    @POST("add-your-listing/")
    Call<ResponseBody> createService(@Part("job_title") String title,
                                     @Part("job_region") Integer region,
                                     @Part("application") String email,
                                     @Part("job_category[]") List<Integer> categories,
                                     @Part("tax_input[job_listing_tag][]") List<Integer> tags,
                                     @PartMap Map<String, RequestBody> operatingHours,
                                     @Part("featured_image") File coverImage,
                                     @Part("gallery_images[]") List<File> galleryImages,
                                     @Part("job_description") String description,
                                     @Part("company_website") String webSite,
                                     @Part("phone") String phone,
                                     @Part("company_video") String video,
                                     @Part("job_id") String jobId,
                                     @Part("job_manager_form") String formName,
                                     @Part("step") String step);

    @FormUrlEncoded
    @POST("add-your-listing/")
    Call<ResponseBody> publishService(@Field("job_id") String jobId,
                                      @Field("step") String step,
                                      @Field("job_manager_form") String formName);

    @FormUrlEncoded
    @POST("wp-json/custom/login")
    Call<User> login(@Field("username") String login,
                     @Field("password") String password);
    @GET("wp-json/custom/test")
    Call<ResponseBody> test();
    @GET("wp-json/custom/get_nonce")
    Call<Nonce> getNonce();
}
