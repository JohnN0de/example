package com.timeweb.checkdomain.presentation.cart

import androidx.annotation.MainThread
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.timeweb.checkdomain.domain.check.model.DomainForm
import com.timeweb.checkdomain.domain.model.Resource
import com.timeweb.checkdomain.presentation.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class SharedCartViewModel @ViewModelInject constructor(
//    private val schedulersProvider: SchedulersProvider,
//    private val parseExceptionUseCase: ParseExceptionUseCase
//    private val cartDataStore: CartDataStore
) : BaseViewModel() {

    private var cartList = mutableListOf<DomainForm>()
    private val cartListChanged: PublishSubject<List<DomainForm>> = PublishSubject.create()
    var cartListResource = MutableLiveData<Resource<List<DomainForm>>>()

    init {
        compositeDisposable += cartListChanged
            .map {
                cartListResource.value = Resource.Loading()
                it
            }
            .debounce(300, TimeUnit.MILLISECONDS)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                cartListResource.postValue(Resource.Success(list))
            }, {
                it.printStackTrace()
                cartListResource.postValue(Resource.Failure(it.localizedMessage))
            })
    }

    @MainThread
    fun addItemCart(form: DomainForm) {
        cartList.add(form)
        cartListChanged.onNext(cartList)
    }

    @MainThread
    fun removeItemCart(form: DomainForm) {
        val index = cartList.indexOfFirst { it.domain == form.domain }
        if (index != -1)
            cartList.removeAt(index)
        cartListChanged.onNext(cartList)
    }

    fun getCartList(): List<DomainForm> =
        cartList.toList()

    fun setCarList(list: MutableList<DomainForm>) {
        cartList = list
        cartListChanged.onNext(cartList)
    }

    fun checkInCart(form: DomainForm): Boolean =
        cartList.indexOfFirst {
            it.domain == form.domain
        } != -1
}