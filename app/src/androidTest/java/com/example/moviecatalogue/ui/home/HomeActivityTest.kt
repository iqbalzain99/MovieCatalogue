package com.example.moviecatalogue.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.moviecatalogue.R
import com.example.moviecatalogue.data.source.remote.response.CreatedByItem
import com.example.moviecatalogue.data.source.remote.response.GenresItem
import com.example.moviecatalogue.data.source.remote.response.MovieDetailResponse
import com.example.moviecatalogue.data.source.remote.response.TvDetailResponse
import com.example.moviecatalogue.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test

class HomeActivityTest {


    private val firstMovie = MovieDetailResponse(overview="Shang-Chi must confront the past he thought he left behind when he is drawn into the web of the mysterious Ten Rings organization.",
        originalTitle="Shang-Chi and the Legend of the Ten Rings",
        imdbId="tt9376612",
        runtime=132,
        title="Shang-Chi and the Legend of the Ten Rings",
        posterPath="/1BIoJGKbXjdFDAqUEiA2VHqkK1Z.jpg",
        backdropPath="/cinER0ESG0eJ49kXlExM0MEWGxW.jpg",
        releaseDate="2021-09-01",
        genres= listOf(GenresItem(name="Action", id=28), GenresItem(name="Adventure", id=12), GenresItem(name="Fantasy", id=14)),
        popularity=7137.893,
        voteAverage=7.9,
        tagline=null,
        id=566525,
        homepage="https://www.marvel.com/movies/shang-chi-and-the-legend-of-the-ten-rings")

    private val firstTvShow = TvDetailResponse(firstAirDate="2021-10-12",
        overview="After a vintage Chucky doll turns up at a suburban yard sale, an idyllic American town is thrown into chaos as a series of horrifying murders begin to expose the town’s hypocrisies and secrets. Meanwhile, the arrival of enemies — and allies — from Chucky’s past threatens to expose the truth behind the killings, as well as the demon doll’s untold origins.",
        numberOfEpisodes=10,
        createdBy= listOf(CreatedByItem(gender=2, name="David Kirschner", profilePath="/eIWqwAyaYsTvuZ9stm6WeLYuUMq.jpg"), CreatedByItem(gender=2, name="Don Mancini", profilePath="/bp7vtf9n9uOPOK1WzdITpwFZqbQ.jpg"), CreatedByItem(gender=2, name="Nick Antosca", profilePath=null)),
        posterPath="/iF8ai2QLNiHV4anwY1TuSGZXqfN.jpg",
        backdropPath="/xAKMj134XHQVNHLC6rWsccLMenG.jpg",
        genres= listOf(GenresItem(name="Sci-Fi & Fantasy", id=10765), GenresItem(name="Comedy", id=35), GenresItem(name="Crime", id=80)),
        originalName="Chucky",
        popularity=4758.584,
        voteAverage=8.0,
        name="Chucky",
        tagline="A classic coming of rage story.",
        id=90462,
        numberOfSeasons=1,
        homepage="https://www.syfy.com/chucky")


    @Before
    fun setUp() {
        ActivityScenario.launch(HomeActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadMovie() {
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(20))
    }

    @Test
    fun loadMovieFavorite() {
        onView(withId(R.id.action_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.action_favorite)).perform(ViewActions.click())
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
    }

    @Test
    fun loadDetailMovie() {
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,ViewActions.click()))
        onView(withId(R.id.btn_favorite)).perform(ViewActions.click())
        onView(isRoot()).perform(ViewActions.pressBack())
        val movie = firstMovie
        var category = ""
        for(item in movie.genres!!) {
            category += (item?.name as String)
            if (item != movie.genres!!.last() ){
                category += ", "
            }
        }
        val detail1 = """
                ${movie.releaseDate?.take(4)} • $category
            """.trimIndent()
        val hour : String = if(movie.runtime!! /60 > 0) "${movie.runtime!!/60}h " else " "
        val minutes : String = if(movie.runtime!! % 60 > 0) "${movie.runtime!! % 60}m" else "0m"
        val duration : String = hour+minutes

        val detail2 = """
                $duration • Movie
                ${movie.releaseDate}
            """.trimIndent()
        onView(withId(R.id.action_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.action_favorite)).perform(ViewActions.click())
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,ViewActions.click()))
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(withText(movie.title)))
        onView(withId(R.id.tv_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail)).check(matches(withText(detail1)))
        onView(withId(R.id.tv_detail2)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail2)).check(matches(withText(detail2)))
        onView(withId(R.id.tv_rating)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_rating)).check(matches(withText(movie.voteAverage.toString())))
        onView(withId(R.id.tv_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_overview)).check(matches(withText(movie.overview)))
        onView(withId(R.id.image_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_favorite)).perform(ViewActions.click())
    }

    @Test
    fun loadTvShow() {
        onView(withText(R.string.tv_show)).perform(ViewActions.click())
        onView(withId(R.id.rv_tv_show)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_show)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(20))
    }

    @Test
    fun loadTvShowFavorite() {
        onView(withId(R.id.action_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.action_favorite)).perform(ViewActions.click())
        onView(withText(R.string.tv_show)).perform(ViewActions.click())
        onView(withId(R.id.rv_tv_show)).check(matches(isDisplayed()))
    }

    @Test
    fun loadDetailTvShow() {
        onView(withText(R.string.tv_show)).perform(ViewActions.click())
        onView(withId(R.id.rv_tv_show)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_show)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,ViewActions.click()))
        onView(withId(R.id.btn_favorite)).perform(ViewActions.click())
        onView(isRoot()).perform(ViewActions.pressBack())
        val tvShow = firstTvShow
        var category = ""
        for(item in tvShow.genres!!) {
            category += (item?.name as String)
            if (item != tvShow.genres!!.last() ){
                category += ", "
            }
        }
        val detail1 = """
                ${tvShow.firstAirDate?.take(4)} • $category
            """.trimIndent()
        val detail2 = """
                ${tvShow.numberOfEpisodes} Episodes • ${tvShow.numberOfSeasons} Seasons
            """.trimIndent()
        onView(withId(R.id.action_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.action_favorite)).perform(ViewActions.click())
        onView(withText(R.string.tv_show)).perform(ViewActions.click())
        onView(withId(R.id.rv_tv_show)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_show)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,ViewActions.click()))
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(withText(tvShow.name)))
        onView(withId(R.id.tv_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail)).check(matches(withText(detail1)))
        onView(withId(R.id.tv_detail2)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_detail2)).check(matches(withText(detail2)))
        onView(withId(R.id.tv_rating)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_rating)).check(matches(withText(tvShow.voteAverage.toString())))
        onView(withId(R.id.tv_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_overview)).check(matches(withText(tvShow.overview)))
        onView(withId(R.id.image_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_favorite)).perform(ViewActions.click())

    }


}