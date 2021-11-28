package com.example.moviecatalogue.utils

import com.example.moviecatalogue.data.source.local.entity.FavoriteShowItems
import com.example.moviecatalogue.data.source.remote.response.*

object DataDummy {

    fun generateItems(): List<FavoriteShowItems> {
        val movies = ArrayList<FavoriteShowItems>()
        movies.apply{
            add(FavoriteShowItems(
                0,
                0,
                "123",
                "dummyTitle",
                "dummyCategory",
                "2020",
                "9.0",
                "dummyPath"
            ))
            add(FavoriteShowItems(
                0,
                0,
                "123",
                "dummyTitle",
                "dummyCategory",
                "2020",
                "9.0",
                "dummyPath"
            ))
            add(FavoriteShowItems(
                0,
                0,
                "123",
                "dummyTitle",
                "dummyCategory",
                "2020",
                "9.0",
                "dummyPath"
            ))
            add(FavoriteShowItems(
                0,
                0,
                "123",
                "dummyTitle",
                "dummyCategory",
                "2020",
                "9.0",
                "dummyPath"
            ))
            add(FavoriteShowItems(
                0,
                0,
                "123",
                "dummyTitle",
                "dummyCategory",
                "2020",
                "9.0",
                "dummyPath"
            ))

        }
        return movies
    }

    fun generateMoviesNew(): List<MovieSimple> {

        val movies = ArrayList<MovieSimple>()

        movies.apply {
            add(
                MovieSimple(
                    "-",
                    "Paul Atreides, a brilliant and gifted young man born into a great destiny beyond his understanding, must travel to the most dangerous planet in the universe to ensure the future of his family and his people. As malevolent forces explode into conflict over the planet's exclusive supply of the most precious resource in existence-a commodity capable of unlocking humanity's greatest potential-only those who can conquer their fear will survive.",
                    "09/16/2021",
                    23123.0,
                    8.8,
                    111,
                    "DummyTitle",
                    listOf(23,2,2,2),
                    "/DummyPath.jpg")
            )
            add(
                MovieSimple(
                    "-",
                    "Paul Atreides, a brilliant and gifted young man born into a great destiny beyond his understanding, must travel to the most dangerous planet in the universe to ensure the future of his family and his people. As malevolent forces explode into conflict over the planet's exclusive supply of the most precious resource in existence-a commodity capable of unlocking humanity's greatest potential-only those who can conquer their fear will survive.",
                    "09/16/2021",
                    23123.0,
                    8.8,
                    123123,
                    "DummyTitle",
                    listOf(23,2,2,2),
                    "/DummyPath.jpg")
            )
            add(
                MovieSimple(
                    "-",
                    "Paul Atreides, a brilliant and gifted young man born into a great destiny beyond his understanding, must travel to the most dangerous planet in the universe to ensure the future of his family and his people. As malevolent forces explode into conflict over the planet's exclusive supply of the most precious resource in existence-a commodity capable of unlocking humanity's greatest potential-only those who can conquer their fear will survive.",
                    "09/16/2021",
                    23123.0,
                    8.8,
                    123123,
                    "DummyTitle",
                    listOf(23,2,2,2),
                    "/DummyPath.jpg")
            )
        }
        return movies
    }

    fun generateTvNew(): List<TvSimple> {

        val movies = ArrayList<TvSimple>()

        movies.apply {
            add(
                TvSimple(
                    "-",
                    "Paul Atreides, a brilliant and gifted young man born into a great destiny beyond his understanding, must travel to the most dangerous planet in the universe to ensure the future of his family and his people. As malevolent forces explode into conflict over the planet's exclusive supply of the most precious resource in existence-a commodity capable of unlocking humanity's greatest potential-only those who can conquer their fear will survive.",
                    "09/16/2021",
                    "dummyname",
                    2314.5,
                    6.7,
                    "dummyName",
                    222,
                    listOf(23,2,2,2),
                    "/dummypath"
                )
            )
            add(
                TvSimple(
                    "-",
                    "Paul Atreides, a brilliant and gifted young man born into a great destiny beyond his understanding, must travel to the most dangerous planet in the universe to ensure the future of his family and his people. As malevolent forces explode into conflict over the planet's exclusive supply of the most precious resource in existence-a commodity capable of unlocking humanity's greatest potential-only those who can conquer their fear will survive.",
                    "09/16/2021",
                    "dummyname",
                    2314.5,
                    6.7,
                    "dummyName",
                    23412,
                    listOf(23,2,2,2),
                    "/dummypath"
                )
            )
            add(
                TvSimple(
                    "-",
                    "Paul Atreides, a brilliant and gifted young man born into a great destiny beyond his understanding, must travel to the most dangerous planet in the universe to ensure the future of his family and his people. As malevolent forces explode into conflict over the planet's exclusive supply of the most precious resource in existence-a commodity capable of unlocking humanity's greatest potential-only those who can conquer their fear will survive.",
                    "09/16/2021",
                    "dummyname",
                    2314.5,
                    6.7,
                    "dummyName",
                    23412,
                    listOf(23,2,2,2),
                    "/dummypath"
                )
            )

        }
        return movies
    }

    fun generateDetailMovie(): MovieDetailResponse {
        return MovieDetailResponse(
            "dummyOverview",
            "dummyTitle",
            "2131",
            453,
            "dummyTitle",
            "/dummypath",
            "/dummybackdrop",
            "32-23-232",
            listOf(GenresItem("asd", 21), GenresItem("asd", 22)),
            12312.3,
            8.8,
            "dummyTagline",
            111,
            "www.dummy.com"
        )
    }

    fun generateDetailTv(): TvDetailResponse {
        return TvDetailResponse(
            "93-293-212",
            "pada zaman dahulu kala",
            213,
            listOf(CreatedByItem(123,"budi","dummypath"),CreatedByItem(123,"budi","dummypath"),CreatedByItem(123,"budi","dummypath")),
            "/dummypath",
            "/dummypath",
            listOf(GenresItem("asd", 21), GenresItem("asd", 22)),
            "dummyname",
            2131.4,
            312.3,
            "dummyname",
            "dummyTagline",
            222,
            123,
            "www.asd.com"
        )
    }


}