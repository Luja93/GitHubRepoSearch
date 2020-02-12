package com.luja93.githubreposearch.common.mvvm.pagination

/**
 * Created by lleopoldovic on 23/01/2020.
 *
 * Use this class to pass pagination params to [GenericPaginationDataSourceFactory].
 * Your repository function which will be passed as the [GenericPaginationDataSourceFactory.call] must accept
 * the same definition.
 *
 * @param pageNumber The page number you want to start the pagination from (it can be any page; e.g. the 4th page if
 *                   you want to paginate backwards as well).
 * @param pageSize The number of items to fetch per page. This number will be overridden by the value passed with
 *                 setPageSize() method of the PagedList.Config.Builder().
 * @param apiFirstPageNumber The first page on the the API (generally 0 or 1). Used for proper handling of loadBefore()
 *                           method in GenericPaginationDataSourceFactory.
 * @param loadAfter True for pagination with increasing pageNumber (regular pagination).
 * @param loadBefore True for pagination with decreasing pageNumber (backwards pagination).
 * @param params A class extending [GenericPaginationParams] and providing all the other params which need to be used
 *               for fetching the data (e.g. search query, unique identifiers etc.).
 */

open class GenericPaginationParams<R>(
    var pageNumber: Int = 1,
    var pageSize: Int = 20,
    var apiFirstPageNumber: Int = 1,
    var loadAfter: Boolean = true,
    var loadBefore: Boolean = false,
    var params: R? = null
)