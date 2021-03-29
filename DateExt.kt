/**
* With that you can write something like this: Date() + Date()
*/
operator fun Date.plus(other: Date): Date = Date(this.time + other.time)

fun Date?.orNow(): Date {
    return this ?: Date()
}
