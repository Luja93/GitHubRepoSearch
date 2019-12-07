package com.luja93.githubreposearch.githubreposearch.repository.repo

import com.luja93.githubreposearch.githubreposearch.model.Repo
import io.reactivex.Observable

/**
 * Created by lleopoldovic on 06/12/2019.
 */

interface RepoData {

    fun saveRepository(repo: Repo)

    fun getRepositories(query: String): Observable<List<Repo>>

}