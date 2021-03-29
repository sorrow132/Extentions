/**
* Helper nullability function
*/
inline fun <reified T> T?.orIfNull(input: () -> T): T {
  return this ?: input()
}

/**
* get Enum from String
* */
inline fun <reified T : Enum<*>> enumValueOrNull(name: String): T? =
    T::class.java.enumConstants.firstOrNull { it.name == name }

inline fun <reified T : Enum<*>> enumValueOrDefault(name: String, default: T): T =
    T::class.java.enumConstants.firstOrNull { it.name == name } ?: default

/**
* try default fun realisation
*/
fun <T : Any> tryOrDefault(tryFunc: () -> T, defaultIfCatches: T): T {
  return try {
    tryFunc()
    } catch (e: Exception) {
      //pls add logging here, for example in android: Log.e ("tag", "tryOrDefault exception: ${e.message}", e)
      defaultIfCatches
    }
}

fun <T : Any> tryOrNull(tryFunc: () -> T): T? {
    return try {
        tryFunc()
    } catch (e: Exception) {
        //pls add logging here, for example in android: Log.e ("tag", "tryOrNull exception: ${e.message}", e)
        null
    }
}

fun tryOrEmpty(tryFunc: () -> String) = tryOrDefault({ tryFunc() }, "")

fun tryOrNow(tryFunc: () -> Date) = tryOrDefault({ tryFunc() }, Date())

/**
* unsafe lazy delegate realisation
*/
inline fun <reified T, reified R> R.unsafeLazy(noinline init: () -> T): Lazy<T> {
  return lazy(LazyThreadSafetyMode.NONE, init)
}

fun <T, V> T?.orLet(variable: V?, letBlock: (V) -> T) = this ?: variable?.let(letBlock::invoke)

/**
* Shorter version of coroutine context swtiching to Default dispatcher
*/
suspend fun <T> doOnDefault(doFun: () -> T) = withContext(Dispatchers.Default) {
    doFun()
}

/**
* Shorter version of coroutine context swtiching to Main dispatcher
*/
suspend fun <T> doOnMain(doFun: () -> T) = withContext(Dispatchers.Main) {
    doFun()
}

/**
* Shorter version of coroutine context swtiching to IO dispatcher
*/
suspend fun <T> doOnIO(doFun: () -> T) = withContext(Dispatchers.IO) {
    doFun()
}
