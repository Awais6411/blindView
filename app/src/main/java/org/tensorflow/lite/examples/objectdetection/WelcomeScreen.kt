package org.tensorflow.lite.examples.objectdetection

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import org.tensorflow.lite.examples.objectdetection.databinding.ActivityWelcomeScreenBinding
import java.util.*

class WelcomeScreen : AppCompatActivity() {
    private var textToSpeech: TextToSpeech? = null

    var isClicked = false;
    lateinit var binding: ActivityWelcomeScreenBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        deliverMessage()



        binding.welcomeButton.setOnClickListener() {
//        startActivity(Intent(this,ModulesScreen::class.java))

            if (!isClicked)  {
                val i = Intent(baseContext, ModulesScreen::class.java)
                startActivity(i)
            }
            isClicked=true
        }


    }

    protected fun deliverMessage() {

        textToSpeech = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = textToSpeech!!.setLanguage(Locale.getDefault())
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "Language not supported")
                } else {
                    val message =
                        "Hello, The Application has been started successfully"
                    textToSpeech!!.speak(message, TextToSpeech.QUEUE_FLUSH, null, null)
                    val background: Thread = object : Thread() {
                        override fun run() {
                            try {
                                // Thread will sleep for 4 seconds
                                sleep((4 * 1000).toLong())

                                // After 4 seconds redirect to another intent
                                val i = Intent(baseContext, ModulesScreen::class.java)
                                startActivity(i)

                                //Remove activity
                                finish()
                            } catch (e: Exception) {
                            }
                        }
                    }
                    // start thread
                    // start thread
                    if (!isClicked) {
                        background.start()
                        isClicked=true

                    }


                }

            } else {
                Log.e("TTS", "Initialization failed")
            }
        }

    }

    override fun onDestroy() {
        if (textToSpeech != null) {
            textToSpeech!!.stop()
            textToSpeech!!.shutdown()
        }
        super.onDestroy()
    }

}