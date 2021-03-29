fun ViewModel.launchUITryCatch(
        start: CoroutineStart = CoroutineStart.DEFAULT,
        catchBlock: ((Throwable) -> Unit)? = null, tryBlock: suspend CoroutineScope.() -> Unit
) {
    try {
        viewModelScope.launch(viewModelScope.coroutineContext, start, tryBlock)
    } catch (e: Throwable) {
      //pls add logging here
        catchBlock?.invoke(e)
    }
}

fun ViewModel.launchAsyncTryCatch(catchBlock: ((Throwable) -> Unit)? = null, tryBlock: suspend CoroutineScope.() -> Unit) {
    try {
        launchAsync(CoroutineStart.DEFAULT, tryBlock)
    } catch (e: Throwable) {
      //pls add logging here
        catchBlock?.invoke(e)
    }
}

fun ViewModel.launchAsync(
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
) {
    viewModelScope.launch(viewModelScope.coroutineContext + Dispatchers.IO, start, block)
}

inline fun <reified T> CoreViewModel.asyncLiveData(
        noinline block: suspend LiveDataScope<T>.() -> Unit
) = liveData(context = viewModelScope.coroutineContext + Dispatchers.IO, block = block)

fun <T> ViewModel.asyncTryCatchLiveData(
        catchBlock: ((Throwable) -> Unit)? = null,
        tryBlock: suspend () -> T
) = liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
    try {
        emit(tryBlock())
    } catch (e: Throwable) {
      //pls add logging here
        catchBlock?.invoke(e)
    }
}

fun <T> ViewModel.asyncTryCatchMutableLiveData(
        catchBlock: ((Throwable) -> Unit)? = null,
        tryBlock: suspend () -> T
) = liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
    try {
        emit(tryBlock())
    } catch (e: Throwable) {
      //pls add logging here
        catchBlock?.invoke(e)
    }
} as MutableLiveData<T>
