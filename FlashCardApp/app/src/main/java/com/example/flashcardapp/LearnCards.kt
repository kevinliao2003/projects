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


class LearnCards : AppCompatActivity() {

    // initialize every View
    lateinit var cardButton : Button
    lateinit var currentCardTV : TextView
    lateinit var analogClock : AnalogClock
    lateinit var correctButton : Button
    lateinit var incorrectBUtton : Button
    lateinit var progressBar : ProgressBar
    lateinit var progressMin : TextView
    lateinit var progressMax : TextView

    var questionSide = true
    lateinit var currentCard : Flashcards.Card
    var currentCardIdx : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn)

        // find the views
        cardButton = findViewById(R.id.card)
        currentCardTV = findViewById(R.id.current_card)
        analogClock = findViewById(R.id.analogClock)
        correctButton = findViewById(R.id.correct)
        incorrectBUtton = findViewById(R.id.incorrect)
        progressBar = findViewById(R.id.progress)
        progressMin = findViewById(R.id.progress_min)
        progressMax = findViewById(R.id.progress_max)

        // update the view based on the model
        updateView()
    }

    override fun onStart() {
        super.onStart()

        Log.w("LearnCards", "Inside LearnCards onStart")

        updateView()
    }

    override fun onResume() {
        super.onResume()

        updateView()
    }

    // update view will update the view based on the model
    fun updateView() {
        Log.w("LearnCards", "Inside LearnCards updateView")

        currentCardIdx = MainActivity.flashcards.getCurrentFlashcardIndex()
        Log.w("LearnCards", "[LearnCards] CurrentCardIndex: $currentCardIdx")


        // set the text of the current card
        currentCardTV.text = "Current Card: " + currentCardIdx

        // set the text of the progress bar
        progressBar.progress = currentCardIdx

        // set the text of the progress min
        progressMin.text = "1"

        // set the text of the progress max
        progressMax.text = MainActivity.flashcards.sizeOfDeck.toString()

        currentCard = MainActivity.flashcards.getFlashcards()[currentCardIdx]
        cardButton.text = currentCard.getQuestion()
    }

    // flip the current card, if
    fun flip ( v : View ) {
        if ( questionSide ) {
            // we are currently a question, we need to replace with the answer text
            cardButton.text = currentCard.getAnswer()

            questionSide = false
        } else {
            // replace with question text
            cardButton.text = currentCard.getQuestion()

            questionSide = true
        }
    }

    fun correctCard ( v : View ) {
        // check if we can remove a card, if not throw an exception
        if (MainActivity.flashcards.sizeOfDeck > 0) {

            Log.w("LearnCards", "[LC/correct] CurrentFlashcardIdx: $currentCardIdx")

            // remove the current card
            MainActivity.flashcards.removeFlashcard(currentCardIdx)

            // if there are no more cards left go to DoneActivity (start intent)
            if (currentCardIdx > MainActivity.flashcards.sizeOfDeck - 1) {
                Log.w("LearnCards", "[LC/correct] LEAVE THIS VIEW: $currentCardIdx")

                // test finish
                finish()

                var myIntent: Intent = Intent(this, DoneActivity::class.java)

                // push the intent onto the stack
                this.startActivity(myIntent)

                // transition
                overridePendingTransition(R.anim.slide_from_left, 0)
            } else {
                Log.w("LearnCards", "[LC/correct] GO TO NEXT CARD!: $currentCardIdx")

                // go to the next card
                currentCard = MainActivity.flashcards.getNextFlashcard()
                currentCardIdx = MainActivity.flashcards.getCurrentFlashcardIndex()
                updateView()
            }
        }
    }

    fun incorrectCard ( v : View ) {
        // if there are no more cards left go to DoneActivity (start intent)
        var deckSize : Int = MainActivity.flashcards.sizeOfDeck

        Log.w("LearnCards", "[LC/incorrect] CurrentFlashcardIdx: $currentCardIdx")

        if (currentCardIdx >= MainActivity.flashcards.sizeOfDeck - 1) {
            Log.w("LearnCards", "[LC/incorrect] LEAVE THIS VIEW: $currentCardIdx")

            // test finish
            finish()

            var myIntent: Intent = Intent(this, DoneActivity::class.java)

            // push the intent onto the stack
            this.startActivity(myIntent)

            // transition
            overridePendingTransition(R.anim.slide_from_left, 0)
        } else {
            Log.w("LearnCards", "[LC/incorrect] GO TO NEXT CARD!: $currentCardIdx")

            // go to the next card
            currentCard = MainActivity.flashcards.getNextFlashcard()
            currentCardIdx = MainActivity.flashcards.getCurrentFlashcardIndex()
            updateView()
        }
    }

}