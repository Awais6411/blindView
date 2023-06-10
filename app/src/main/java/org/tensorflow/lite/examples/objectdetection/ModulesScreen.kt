package org.tensorflow.lite.examples.objectdetection

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import org.tensorflow.lite.examples.objectdetection.databinding.ActivityModulesScreenBinding
import java.util.*

class ModulesScreen : AppCompatActivity() {

    lateinit var binding: ActivityModulesScreenBinding
    private lateinit var textToSpeech: TextToSpeech
    private var speechRecognizer: SpeechRecognizer? = null
    // private var isListening = false
    private var SPEECH_REQUEST_CODE = 0
    var feedback=false;
//    private var tvSpeechText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModulesScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.framelt.isInvisible = true

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        var msg =
            "Which one would you like to choose, start detection,start navigation , feedback or exit ,"
        textToSpeechCoverter(msg)



        /*
          textToSpeech = TextToSpeech(this) { status ->
              if (status == TextToSpeech.SUCCESS) {
                  val result = textToSpeech!!.setLanguage(Locale.getDefault())
                  if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                      Log.e("TTS", "Language not supported")
                  } else {
                      val message =
                          "Hello, we are going to show different modules for your ease which one would you like to choose, start navigation,start detection ,Exit ,Thaaanks"
                      textToSpeech!!.speak(message, TextToSpeech.QUEUE_FLUSH, null, null)
                  }
              } else {
                  Log.e("TTS", "Initialization failed")
              }
          }
  */
        binding.startdtect.setOnClickListener {

            startActivity(Intent(baseContext, MainActivity::class.java))
        }
        binding.btnfeedback.setOnClickListener {

        }
        binding.btnexit.setOnClickListener() {
            binding.framelt.isInvisible = false
            //binding.framelt.visibility=View.VISIBLE

            textToSpeechCoverter("Are you sure you want to exit the app?")


        }


        binding.btncancel.setOnClickListener() {
            finish();
        }
        binding.btnreset.setOnClickListener() {
            binding.framelt.isVisible = false
        }
        binding.btnfeedback.setOnClickListener() {

            //   startListening();
        }
    }

    public fun textToSpeechCoverter(msg: String) {
        textToSpeech = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = textToSpeech.setLanguage(Locale.getDefault())
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "Language not supported")
                } else {
                    val message = msg
                    textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null)
                }
//                startListening()
                myFunction()
            } else {
                Log.e("TTS", "Initialization failed")
            }
        }


    }

    /*
    private fun startListening() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        startActivityForResult(intent, SPEECH_REQUEST_CODE)
        speechRecognizer!!.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle) {
                isListening = true
            }

            override fun onBeginningOfSpeech() {
                // Called when user starts speaking
            }

            override fun onRmsChanged(rmsdB: Float) {
                // Called when the sound level of the user's voice changes
            }

            override fun onBufferReceived(bytes: ByteArray) {}
            override fun onPartialResults(partialResults: Bundle) {
                // Called when the recognizer has partial results
            }

            override fun onSegmentResults(segmentResults: Bundle) {
                //  super@RecognitionListener.onSegmentResults(segmentResults)
            }

            override fun onEndOfSegmentedSession() {
                // super@RecognitionListener.onEndOfSegmentedSession()
            }

            override fun onEndOfSpeech() {
                isListening = false
            }

            override fun onError(error: Int) {
                isListening = false
            }

            override fun onResults(results: Bundle) {
                // Called when the recognizer has final results
                val matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (matches != null && !matches.isEmpty()) {
                    val spokenText = matches[0]
                    // tvSpeechText.setText(spokenText)
                //     Toast.makeText(this, spokenText, Toast.LENGTH_LONG).show()
                   // Toast.makeText(applicationContext, spokenText, Toast.LENGTH_SHORT).show()

                }
                isListening = false
            }

            override fun onEvent(eventType: Int, params: Bundle) {
                // Called when the recognizer has an event
            }
        })
        speechRecognizer!!.startListening(intent)
    }
*/

    fun startVoiceInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak something!")
        startActivityForResult(intent, SPEECH_REQUEST_CODE)
    }

    //stop listening method
    /*
    private fun stopListening() {
        if (speechRecognizer != null && isListening) {
            speechRecognizer!!.stopListening()
            isListening = false
        }
    }

*/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === SPEECH_REQUEST_CODE && resultCode === RESULT_OK) {
            val results: List<String>? = data!!.getStringArrayListExtra(
                RecognizerIntent.EXTRA_RESULTS
            )
            val spokenText = results!![0]
            Toast.makeText(this, spokenText, Toast.LENGTH_SHORT).show()
            if(spokenText == "exit"){

                binding.framelt.isInvisible = false
                textToSpeechCoverter("Are you sure you want to exit the app?")

            }
            else if (spokenText=="yes"){
                finish()
            }
            else{
                binding.framelt.isInvisible = true;
            }

            if (spokenText=="feedback"){
                feedback=true;
                textToSpeechCoverter("Please give your feedback Thanks.")
                Toast.makeText(this, spokenText, Toast.LENGTH_SHORT).show()

            }
            if(spokenText=="start detection"){

                val i = Intent(baseContext, MainActivity::class.java)
                startActivity(i)
            }

//            finish()
//            tvSpeechText.setText(spokenText)

        }

    }

    override fun onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
        // stopListening();
        super.onDestroy()
    }

    fun myFunction() {
        // Do something before delay
        val background: Thread = object : Thread() {
            override fun run() {
                try {
                    // Thread will sleep for 4 seconds
                    sleep((6 * 1000).toLong())

                    // After 4 seconds redirect to another intent
                    startVoiceInput()

                    //Remove activity
                    //finish()
                } catch (e: Exception) {
                }
            }
        }
        // start thread
        // start thread

        background.start()

    }
    // Do something after delay
}
