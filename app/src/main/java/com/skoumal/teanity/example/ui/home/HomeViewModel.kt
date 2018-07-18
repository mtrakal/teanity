package com.skoumal.teanity.example.ui.home

import com.skoumal.teanity.BR
import com.skoumal.teanity.databinding.ComparableRvItem
import com.skoumal.teanity.example.data.repository.PhotoRepository
import com.skoumal.teanity.example.model.entity.LoadingRvItem
import com.skoumal.teanity.example.model.entity.Photo
import com.skoumal.teanity.example.model.entity.PhotoRvItem
import com.skoumal.teanity.extensions.applySchedulers
import com.skoumal.teanity.util.DiffObservableList
import com.skoumal.teanity.viewmodel.LoadingViewModel
import me.tatarka.bindingcollectionadapter2.OnItemBind
import java.util.concurrent.TimeUnit

class HomeViewModel(
    private val photoRepository: PhotoRepository
) : LoadingViewModel() {

    val items = DiffObservableList(ComparableRvItem.callback)
    val itemBinding = OnItemBind<ComparableRvItem<*>> { itemBinding, _, item ->
        item.bind(itemBinding)
        itemBinding.bindExtra(BR.viewModel, this@HomeViewModel)
    }
    private var loadedPage = 0

    init {
        loadItems()
    }

    fun retryLoadingButtonClicked() {
        loadItems()
    }

    private fun loadItems(page: Int = 1) {
        photoRepository.getPhotos(page = page)
            .delay(1000, TimeUnit.MILLISECONDS)
            .applySchedulers()
            .doOnSubscribe {
                if (page == 1) {
                    setLoading()
                } else {
                    val last = items.lastOrNull() as? LoadingRvItem
                    last?.failed?.set(false)
                }
            }
            .subscribe({
                setLoaded()
                loadedPage = page
                itemsLoaded(it)
            }, {
                if (page == 1) {
                    setLoadingFailed()
                } else {
                    val last = items.lastOrNull() as? LoadingRvItem
                    last?.failed?.set(true)
                }
            })
            .add()
    }

    fun loadMoreItems() {
        loadItems(loadedPage + 1)
    }

    private fun itemsLoaded(newItems: List<Photo>) {
        val listItems = mutableListOf<ComparableRvItem<*>>()

        listItems += newItems.map { PhotoRvItem(it) }

        listItems += LoadingRvItem("Loading failed", "Try again", ::loadMoreItems)

        items.removeLast()
        items.addAll(listItems)
    }

    fun photoClicked(photo: Photo) {
        NavigateToPhotoDetailEvent(photo).publish()
    }
}