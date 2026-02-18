package com.fosents.kotlinvendingmachine.sound

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.fosents.kotlinvendingmachine.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SoundManager @Inject constructor(
    @param:ApplicationContext private val context: Context ) {

    private var soundPool: SoundPool
    private val soundMap = mutableMapOf<SoundType, Int>()

    enum class SoundType { CLICK, COIN, ERROR }

    init {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool =  SoundPool.Builder()
            .setMaxStreams(3)
            .setAudioAttributes(audioAttributes)
            .build()

        loadSounds()
    }

    private fun loadSounds() {
        soundMap[SoundType.CLICK] = soundPool.load(context, R.raw.click_default, 1)
        soundMap[SoundType.COIN] = soundPool.load(context, R.raw.click_coin, 1)
        soundMap[SoundType.ERROR] = soundPool.load(context, R.raw.out_of_order, 1)
    }

    fun play(type: SoundType) {
        val soundId = soundMap[type] ?: return
        if (soundId != 0) {
            soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
        }
    }

    fun release() {
        soundPool.release()
    }
}
