package com.luja93.githubreposearch.common.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.luja93.githubreposearch.common.data.remote.ApiInterface
import com.luja93.githubreposearch.common.mvvm.basemodels.ResourceState
import com.luja93.githubreposearch.common.scheduler.SchedulerProvider
import com.luja93.githubreposearch.common.session.SessionPrefImpl
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by lleopoldovic on 16/09/2019.
 */

abstract class BaseViewModel(private val schedulers: SchedulerProvider) : ViewModel() {

    // DI:
    @Inject
    lateinit var session: SessionPrefImpl

    @Inject
    lateinit var apiInterface: ApiInterface
    ////////////////////////////////////////////////////////////////////////////////////////////////


    /*
        Lifecycle
     */
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////


    /*
        RxJava calls
     */
    /**
     * Used for creating observable calls to remove RxJava boilerplate.
     *
     * On the other hand, if a custom action is required while doOnSubscribe and subscribe are
     * triggered, your are free to override the action. In that case, be sure to manually update
     * your liveData values.
     */
    fun <T> observableCall(
        liveData: MutableLiveData<ResourceState<T>>,
        call: () -> Observable<ResourceState<T>>,
        doOnSubscribe: ((disposable: Disposable) -> Unit)? = null,
        subscribe: ((data: ResourceState<T>) -> Unit)? = null,
        scheduler: Scheduler = schedulers.io()
    ) {
        @Suppress("unused")
        call()
            .subscribeOn(scheduler)
            .observeOn(schedulers.ui())
            .doOnSubscribe {
                compositeDisposable.add(it)
                if (doOnSubscribe != null) {
                    doOnSubscribe(it)
                } else {
                    liveData.value = ResourceState.loading()
                }
            }
            .subscribe {
                if (subscribe != null) {
                    subscribe(it)
                } else {
                    liveData.postValue(it)
                }
            }
    }

    /**
     * Used for creating completable calls to remove RxJava boilerplate.
     *
     * On the other hand, if a custom action is required while doOnSubscribe and subscribe are
     * triggered, your are free to override the action. In that case, be sure to update your
     * liveData values manually.
     */
    fun <T> completableCall(
        liveData: MutableLiveData<ResourceState<T>>,
        call: () -> Completable,
        doOnSubscribe: ((disposable: Disposable) -> Unit)? = null,
        subscribe: (() -> Unit)? = null,
        scheduler: Scheduler = schedulers.io()
    ) {
        @Suppress("unused")
        call()
            .subscribeOn(scheduler)
            .observeOn(schedulers.ui())
            .doOnSubscribe {
                compositeDisposable.add(it)
                if (doOnSubscribe != null) {
                    doOnSubscribe(it)
                } else {
                    liveData.value = ResourceState.loading()
                }
            }
            .subscribe {
                if (subscribe != null) {
                    subscribe()
                }
            }
    }

    /**
     * Used for creating completable calls from non-completable action to remove RxJava boilerplate.
     *
     * E.g.: save data only to database
     *
     * On the other hand, if a custom action is required while doOnSubscribe and subscribe are
     * triggered, your are free to override the action. In that case, be sure to update your
     * liveData values manually.
     */
    fun <T> completableCallFromAction(
        liveData: MutableLiveData<ResourceState<T>>,
        call: () -> Unit,
        doOnSubscribe: ((disposable: Disposable) -> Unit)? = null,
        subscribe: (() -> Unit)? = null,
        scheduler: Scheduler = schedulers.io()
    ) {
        @Suppress("unused")
        Completable.fromAction {
            call()
        }
            .subscribeOn(scheduler)
            .observeOn(schedulers.ui())
            .doOnSubscribe {
                compositeDisposable.add(it)
                if (doOnSubscribe != null) {
                    doOnSubscribe(it)
                } else {
                    liveData.value = ResourceState.loading()
                }
            }
            .subscribe {
                if (subscribe != null) {
                    subscribe()
                }
            }
    }

    /**
     * RxJava observable extensions for data mapping and filtration
     * */

    fun <T, R> Observable<ResourceState<T>>.mapData(mapper: (item: T) -> R): Observable<ResourceState<R>> {
        return this.map { rs ->
            rs.data?.let { data ->
                ResourceState(
                    rs.status,
                    mapper(data),
                    rs.error
                )
            } ?: ResourceState(rs.status, null, rs.error)
        }
    }

    fun <T> Observable<ResourceState<List<T>>>.filterData(filter: (item: T) -> Boolean): Observable<ResourceState<List<T>>> {
        return this.map { rs ->
            rs.data?.let { data ->
                ResourceState(
                    rs.status,
                    data.filter { filter(it) },
                    rs.error)
            } ?: rs
        }
    }

}