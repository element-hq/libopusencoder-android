package io.element.android.opusencoder

import android.util.Log
import androidx.annotation.IntRange
import io.element.android.opusencoder.configuration.SampleRate

internal class OggOpusEncoderImpl : OggOpusEncoder {

    companion object {

        private const val TAG = "OggOpusEncoder"

        init {
            try {
                System.loadLibrary("opuscodec")
            } catch (e: Exception) {
                Log.e(TAG, "Couldn't load opus library: $e")
            }
        }
    }

    /**
     * Initializes the encoder, creating a file in [filePath] and setting [sampleRate].
     */
    override fun init(filePath: String, sampleRate: SampleRate): Int {
        return init(filePath, sampleRate.value)
    }

    private external fun init(filePath: String, sampleRate: Int): Int

    /**
     * Sets a custom [bitrate] for the encoder.
     */
    override fun setBitrate(@IntRange(from = 500, to = 512000) bitrate: Int): Int {
        return setEncoderBitrate(bitrate)
    }

    private external fun setEncoderBitrate(bitrate: Int): Int

    /**
     * Writes the [shorts] raw frame into the destination file, using [samplesPerChannel].
     */
    override fun encode(shorts: ShortArray, samplesPerChannel: Int): Int {
        return writeFrame(shorts, samplesPerChannel)
    }

    private external fun writeFrame(shorts: ShortArray, samplesPerChannel: Int): Int

    /**
     * Releases and destroys the current encoder.
     */
    override fun release() {
        encoderRelease()
    }

    private external fun encoderRelease()

}
