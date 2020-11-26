package gun0912.tedimagepicker.util

import java.text.DecimalFormat
import java.util.concurrent.TimeUnit

class TextFormatUtil {
    companion object {
        @JvmStatic
        fun getMediaCountText(imageCountFormat: String, count: Int): String {
            val decimalCount = DecimalFormat("#,###").format(count)
            return String.format(imageCountFormat, decimalCount)
        }

        @JvmStatic
        fun getVideoMediaDurationText(duration: Long): String {
            if(TimeUnit.MILLISECONDS.toHours(duration) > 0) {
                return String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(duration),
                        TimeUnit.MILLISECONDS.toMinutes(duration),
                        TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
                )
            }

            return String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(duration),
                    TimeUnit.MILLISECONDS.toSeconds(duration) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
            )
        }
    }
}