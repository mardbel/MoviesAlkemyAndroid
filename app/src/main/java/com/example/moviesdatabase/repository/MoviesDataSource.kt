package com.example.moviesdatabase.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviesdatabase.api.TheMovieDBService
import com.example.moviesdatabase.data.Movie
import javax.inject.Inject
import javax.inject.Singleton

private const val MOVIES_STARTING_PAGE_INDEX = 1
private const val API_KEY = "6a9a49ab06a8dbb88e7e296a11608515"

@Singleton
class MoviesDataSource @Inject constructor(
    private val moviesService: TheMovieDBService
) : PagingSource<Int, Movie>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Movie> {
        return try {
            val pageNumber = params.key ?: MOVIES_STARTING_PAGE_INDEX
            val response = moviesService.getMoviesPlayingNow(API_KEY, "en-US", pageNumber, "US")
            val nextElements = response.totalResults - (pageNumber * params.loadSize)
            val nextKey = if (nextElements > 0) response.page + 1 else null

            LoadResult.Page(
                data = response.movies,
                prevKey = null, // Only paging forward.
                nextKey = nextKey
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}

