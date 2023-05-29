package com.example.flashcardapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    // initialize every View
    lateinit var questionButton : EditText
    lateinit var answerButton : EditText
    lateinit var doneButton : Button
    lateinit var newCardButton : Button
    lateinit var numCardsText : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.flash_add)

        Log.w("MainActivity", "Inside onCreate MainActivity")
        // instantiate a flashcard object
        flashcards = Flashcards()

        // find the views
        questionButton = findViewById(R.id.question)
        answerButton = findViewById(R.id.answer)
        doneButton = findViewById(R.id.done)
        newCardButton = findViewById(R.id.addCard)
        numCardsText = findViewById(R.id.number)

        // update the view based on the model
        updateView()
    }

    override fun onStart() {
        super.onStart()

        Log.w("MainActivity", "Inside MainActivity onStart")

        updateView()
    }

    override fun onResume() {
        super.onResume()

        Log.w("MainActivity", "Inside MainActivity onResume")

        updateView()
    }

    // update view will update the view based on the model
    fun updateView() {
        Log.w("MainActivity", "Inside MainActivity updateView")

        var numcards = flashcards.sizeOfDeck
        Log.w("MainActivity", "[MA] NumOfCards: $numcards, raw size: ${flashcards.getFlashcards().size}")

        // for loop print out all the cards with their question and answer with index
        for (i in 0 until flashcards.getFlashcards().size) {
            Log.w("MainActivity", "[MA] Card $i: ${flashcards.getFlashcards()[i].getQuestion()} : ${flashcards.getFlashcards()[i].getAnswer()}")
        }

        // set the text of the number of cards
        numCardsText.text = "Number of Cards: " + numcards
    }

    // update flashcards will update the model based on the view
    fun updateFlashcards() {
        // get the question and answer from the view
        var question = questionButton.text.toString()
        var answer = answerButton.text.toString()

        // add the flashcard to the model
        flashcards.addFlashcard(question, answer)
    }

    // if the question and answer EditText views are not null or empty add their strings to the flashcard hashmap with currentFlashcardIndex += 1
    fun addNewCard(view : View) {
        // if the question and answer are not empty
        if (!questionButton.text.toString().isEmpty() && !answerButton.text.toString().isEmpty()) {
            // update the flashcards
            updateFlashcards()

            // update the view
            updateView()
        }
    }

    // Check if the the flashcards hashmap is not empty and if it is not finish() and then transition to MainActivity
    fun doneAdding ( v : View) {
        Log.w("Flashcards", "Called Done! Check if we have any cards before finishing.")

        // if the flashcards hashmap is not empty
        if (flashcards.sizeOfDeck > 0) {

            // Test finish
            // TODO: Fix that we completely loose our array of cards when we finish b/c it will do an onCreate call.
            finish()

            // go to DataActivity
            var myIntent : Intent = Intent( this, LearnCards::class.java )

            // push the intent onto the stack
            this.startActivity( myIntent )

            // transition
            overridePendingTransition( R.anim.slide_from_left, 0 )
        }
    }

    companion object {
        lateinit var flashcards : Flashcards
    }
}