package com.timeweb.checkdomain.presentation.check

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.timeweb.checkdomain.domain.api.ParseExceptionUseCase
import com.timeweb.checkdomain.domain.check.DomainUseCase
import com.timeweb.checkdomain.domain.check.model.DomainForm
import com.timeweb.checkdomain.domain.common.SchedulersProvider
import com.timeweb.checkdomain.domain.model.Resource
import com.timeweb.checkdomain.presentation.base.BaseViewModel
import com.timeweb.checkdomain.presentation.base.adapter.BaseListItem
import com.timeweb.checkdomain.presentation.cart.CartDataStore
import com.timeweb.checkdomain.presentation.check.item.DomainListItem
import com.timeweb.checkdomain.presentation.extension.toLowerCaseWithLocale
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.*
import java.util.concurrent.TimeUnit

class CheckDomainViewModel @ViewModelInject constructor(
    private val schedulersProvider: SchedulersProvider,
    private val domainUseCase: DomainUseCase,
    private val parseExceptionUseCase: ParseExceptionUseCase,
    private val cartDataStore: CartDataStore
) : BaseViewModel() {

    val inputSearchText: PublishSubject<String> = PublishSubject.create()
    val needUpdateCartList = MutableLiveData<List<DomainForm>>()
    val domainStatusResource = MutableLiveData<Resource<BaseListItem>>()
    var onChangeCartState: ((added: Boolean, DomainForm) -> Unit)? = null

    private val random: Random by lazy { // TODO: Удалить, как добавишь апи парсинга цены
        Random()
    }

    init {
        compositeDisposable += inputSearchText
            .debounce(300, TimeUnit.MILLISECONDS)
            .map { text -> text.toLowerCaseWithLocale().trim() }
            .distinctUntilChanged()
            .observeOn(schedulersProvider.main())
            .subscribe({ text ->
                checkDomain(text)
            }, {
                it.printStackTrace()
            })
        restoreCartList()
    }

    private fun restoreCartList() {
        cartDataStore.cartList?.let {
            needUpdateCartList.value = it
        }
    }

    fun saveCartList(list: List<DomainForm>) {
        cartDataStore.cartList = list
    }

    private fun checkDomain(text: String) {
        domainStatusResource.value = Resource.Loading()
        compositeDisposable += domainUseCase.checkDomain(text)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.main())
            .subscribeBy(
                onSuccess = {
                    domainStatusResource.value = Resource.Success(
                        DomainListItem(
                            it,
                            form = DomainForm(
                                text,
                                random.nextInt(10000).toDouble(),
                                random.nextInt(15) + 1
                            ),
                            onChangeCartState
                        )
                    )// TODO: Удалить, как добавишь апи парсинга цены
                },
                onError = {
                    domainStatusResource.value =
                        Resource.Failure(parseExceptionUseCase.parseException(it))
                }
            )
    }

}