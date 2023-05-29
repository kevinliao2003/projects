package com.example.flashcardapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AnalogClock
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DoneActivity : AppCompatActivity() {

    // initialize every View
    lateinit var msgTV : TextView
    lateinit var restartButon : Button
    lateinit var newCardsButton : Button

    var questionSide = true
    lateinit var currentCard : Flashcards.Card

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.done)

        // find the views
        msgTV = findViewById(R.id.msg)
        restartButon = findViewById(R.id.restart)
        newCardsButton = findViewById(R.id.new_cards)

        // update the view based on the model
        updateView()
    }

    override fun onStart() {
        super.onStart()

        Log.w("LearnCards", "Inside LearnCards onStart")

        updateView()
    }

    // update view will update the view based on the model
    fun updateView() {
        Log.w("DoneActivity", "Inside LearnCards updateView")
    }

    // add the onClick methods from done.xml
    fun addCards( v : View ) {
        // reset the current index
        MainActivity.flashcards.setCurrentFlashcardIndex(0)

        // Test to pop this off the stack
        finish()

        // set the intent to MainActivity
        // TODO: Pass in the flashcards object so it doesn't get lost forever.
        var myIntent : Intent = Intent( this, MainActivity::class.java )

        // push the intent onto the stack
        this.startActivity( myIntent )

        // transition
        overridePendingTransition( R.anim.slide_from_left, 0 )
    }

    fun restart ( v : View ) {
        // go back to LearnCards if we have them
        if( MainActivity.flashcards.sizeOfDeck > 0 ) {
            // reset the current index
            MainActivity.flashcards.setCurrentFlashcardIndex(0)

            finish()
        }
    }
}