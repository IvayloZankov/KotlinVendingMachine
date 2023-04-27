package com.fosents.kotlinvendingmachine.sound

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.fosents.kotlinvendingmachine.R

class SoundManager {

    var soundPool: SoundPool

    private var soundClick = 0
    private var soundCoin = 0
    private var soundError = 0

    init {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool =  SoundPool.Builder().setMaxStreams(3).setAudioAttributes(audioAttributes).build()
    }

    companion object {
        @Volatile
        private var instance: SoundManager? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: SoundManager().also { instance = it }
            }
    }

    fun loadSounds(context: Context) {
        soundClick = soundPool.load(context, R.raw.click_default, 1)
        soundCoin = soundPool.load(context, R.raw.click_coin, 1)
        soundError = soundPool.load(context, R.raw.out_of_order, 1)
    }

    fun playClick() {
        soundPool.play(soundClick, 1f, 1f, 1, 0, 1f)
    }

    fun playCoin() {
        soundPool.play(soundCoin, 1f, 1f, 1, 0, 1f)
    }

    fun playError() {
        soundPool.play(soundError, 1f, 1f, 1, 0, 1f)
    }
}