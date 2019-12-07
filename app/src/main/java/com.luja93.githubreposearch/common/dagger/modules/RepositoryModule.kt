package com.luja93.githubreposearch.common.dagger.modules

import com.luja93.githubreposearch.githubreposearch.repository.repo.RepoRepo
import com.luja93.githubreposearch.githubreposearch.repository.repo.RepoRepoImpl
import com.luja93.githubreposearch.githubreposearch.repository.user.UserRepo
import com.luja93.githubreposearch.githubreposearch.repository.user.UserRepoImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by lleopoldovic on 06/12/2019.
 */

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepoRepo(repoRepoImpl: RepoRepoImpl): RepoRepo = repoRepoImpl

    @Provides
    @Singleton
    fun provideUserRepo(userRepoImpl: UserRepoImpl): UserRepo = userRepoImpl

}