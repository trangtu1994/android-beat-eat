package com.example.core.domain.filters

import com.example.core.domain.sorters.MovieSorter

class MovieConfig(var page: Int = 0,
                  var isFavorite: Boolean,
                  var sorter: MovieSorter
)
