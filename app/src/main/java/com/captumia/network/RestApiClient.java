package com.captumia.network;

import com.captumia.data.Category;
import com.captumia.data.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApiClient {
    String BASE_URL = "https://www.captumia.com/wp-json/wp/v2/";

    @GET("job_listing")
    Call<List<Post>> getPosts(@Query("job_listing_category") int categoryId,
                              @Query("page") int page,
                              @Query("per_page") int itemsPerPage);

    @GET("job_listing")
    Call<List<Post>> searchPosts(@Query("search") String search,
                                 @Query("page") int page,
                                 @Query("per_page") int itemsPerPage);

    @GET("job_listing_category?parent=0&orderby=count&per_page=6")
    Call<List<Category>> getTopRootCategories();

    @GET("job_listing_category?orderby=count")
    Call<List<Category>> getCategories(@Query("parent") int parent,
                                       @Query("page") int page,
                                       @Query("per_page") int itemsPerPage);
}
