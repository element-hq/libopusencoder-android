/*
 * Copyright (c) 2022 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package im.vector.opusencoder

import android.util.Log
import androidx.annotation.IntRange
import im.vector.opusencoder.configuration.SampleRate

/**
 * JNI bridge to CodecOggOpus in the native opuscodec library.
 */
class OggOpusEncoder {

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
    fun init(filePath: String, sampleRate: SampleRate): Int {
        return init(filePath, sampleRate.value)
    }
    private external fun init(filePath: String, sampleRate: Int): Int

    /**
     * Sets a custom [bitrate] for the encoder.
     */
    external fun setBitrate(@IntRange(from = 500, to = 512000) bitrate: Int): Int

    /**
     * Writes the [shorts] raw frame into the destination file, using [samplesPerChannel].
     */
    fun encode(shorts: ShortArray, samplesPerChannel: Int): Int {
        return writeFrame(shorts, samplesPerChannel)
    }
    private external fun writeFrame(shorts: ShortArray, samplesPerChannel: Int): Int

    /**
     * Releases and destroys the current encoder.
     */
    fun release() {
        encoderRelease()
    }
    private external fun encoderRelease()
}
