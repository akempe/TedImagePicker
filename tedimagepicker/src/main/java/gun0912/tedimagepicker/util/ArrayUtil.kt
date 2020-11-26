package gun0912.tedimagepicker.util

class ArrayUtil {
    companion object {
        @JvmStatic
        fun appendString(arr: Array<String>, element: String): Array<String> {
            val list: MutableList<String> = arr.toMutableList()
            list.add(element)
            return list.toTypedArray()
        }
    }
}