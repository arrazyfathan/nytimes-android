package com.arrazyfathan.nytimes.core.di

import com.arrazyfathan.nytimes.core.data.TopStoriesRepository
import com.arrazyfathan.nytimes.core.domain.repository.ITopStoriesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Ar Razy Fathan Rabbani on 27/03/23.
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(topStoriesRepository: TopStoriesRepository): ITopStoriesRepository
}
