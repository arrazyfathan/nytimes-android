package com.arrazyfathan.nytimes.di

import com.arrazyfathan.nytimes.core.domain.usecase.TopStoriesInteractor
import com.arrazyfathan.nytimes.core.domain.usecase.TopStoriesUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Created by Ar Razy Fathan Rabbani on 27/03/23.
 */

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    @Binds
    @ViewModelScoped
    abstract fun provideTopStoriesUseCase(topStoriesInteractor: TopStoriesInteractor): TopStoriesUseCase
}
