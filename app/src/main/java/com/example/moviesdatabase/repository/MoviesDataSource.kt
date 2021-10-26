package com.example.moviesdatabase.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviesdatabase.api.RetrofitServiceBuilder
import com.example.moviesdatabase.api.TheMovieDBService
import com.example.moviesdatabase.data.Movie
import com.example.moviesdatabase.data.MoviesPlayingNow
import retrofit2.Call
import retrofit2.Response

private const val MOVIES_STARTING_PAGE_INDEX = 1
private const val BASE_URL = "https://api.themoviedb.org/3/"
private const val API_KEY = "6a9a49ab06a8dbb88e7e296a11608515"

class MoviesDataSource(
    private val moviesService: TheMovieDBService =
        RetrofitServiceBuilder(BASE_URL)
            .buildService(TheMovieDBService::class.java)
) : PagingSource<Int, Movie>() {


    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Movie> {
        try {
            val pageNumber = params.key ?: MOVIES_STARTING_PAGE_INDEX
            val response = moviesService.getMoviesPlayingNow(API_KEY, "en-US", pageNumber, "US")


            val nextElements = response.totalResults - (pageNumber * params.loadSize)
            val nextKey = if (nextElements > 0) response.page + 1 else null



            return LoadResult.Page(
                data = response.movies,
                prevKey = null, // Only paging forward.
                nextKey = nextKey
            )

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}

