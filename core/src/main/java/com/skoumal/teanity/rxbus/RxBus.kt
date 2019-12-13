package com.skoumal.teanity.rxbus

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

@Deprecated(
    """
       RxBus is deprecated by superior Channels (or Flows) contained in the coroutines library. 
       It will reach EOL in 1.1. Vessel is a successor.  
    """, level = DeprecationLevel.ERROR
)
class RxBus {

    private val _bus = PublishSubject.create<Event>()

    val bus: Observable<Event> get() = _bus

    fun post(event: Event) {
        _bus.onNext(event)
    }

    fun post(event: Int) {
        _bus.onNext(SimpleEvent(event))
    }

    inline fun <reified T : Event> register(noinline predicate: (T) -> Boolean = { true }): Observable<T> {
        return bus
            .ofType(T::class.java)
            .filter(predicate)
    }

    fun register(eventId: Int): Observable<Int> {
        return bus
            .ofType(SimpleEvent::class.java)
            .map { it.eventId }
            .filter { it == eventId }
    }

    interface Event

    private class SimpleEvent(val eventId: Int) : Event
}