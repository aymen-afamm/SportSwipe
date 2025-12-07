package com.sportmatch.app.di

import com.sportmatch.app.data.repository.ChatRepository
import com.sportmatch.app.data.repository.MatchRepository
import com.sportmatch.app.data.repository.SwipeRepository
import com.sportmatch.app.data.repository.UserRepository
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    // Repositories are already annotated with @Singleton and @Inject constructor
    // Hilt will automatically provide them
}

