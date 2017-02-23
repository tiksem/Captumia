package com.captumia.network;

import com.captumia.data.Category;
import com.captumia.data.LoginResult;
import com.captumia.data.Post;
import com.captumia.data.Region;
import com.captumia.data.Tag;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApiClient {
    String BASE_URL = "https://www.captumia.com/wp-json/";

    @GET("wp/v2/job_listing")
    Call<List<Post>> getPostsByCategory(@Query("job_listing_category") int categoryId,
                                        @Query("page") int page,
                                        @Query("per_page") int itemsPerPage);

    @GET("wp/v2/job_listing")
    Call<List<Post>> getPostsByTag(@Query("job_listing_tag") int tagId,
                                   @Query("page") int page,
                                   @Query("per_page") int itemsPerPage);

    @GET("wp/v2/job_listing")
    Call<List<Post>> searchPosts(@Query("search") String search,
                                 @Query("page") int page,
                                 @Query("per_page") int itemsPerPage);

    @GET("wp/v2/job_listing_category?parent=0&orderby=count&per_page=6")
    Call<List<Category>> getTopRootCategories();

    @GET("wp/v2/job_listing_category?orderby=count")
    Call<List<Category>> getCategories(@Query("parent") int parent,
                                       @Query("page") int page,
                                       @Query("per_page") int itemsPerPage);

    @GET("/jwt-auth/v1/token")
    Call<LoginResult> login(@Query("parent") int parent,
                            @Query("page") int page,
                            @Query("per_page") int itemsPerPage);

    @GET("wp/v2/job_listing_tag?per_page=50")
    Call<List<Tag>> getTags();

    @GET("wp/v2/job_listing_region?per_page=50")
    Call<List<Region>> getRegions();

    @GET("wp/v2/job_listing_category?orderby=count&per_page=50")
    Call<List<Category>> getCategories();
}
