package com.luja93.githubreposearch.githubreposearch.repository.repo

import com.luja93.githubreposearch.githubreposearch.model.Repo
import io.reactivex.Completable
import io.reactivex.Observable

interface RepoData {

    fun saveRepository(repo: Repo): Completable

    fun getRepositories(query: String): Observable<List<Repo>>

}