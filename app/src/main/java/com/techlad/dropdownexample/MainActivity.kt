package com.techlad.dropdownexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.techlad.dropdowntextview.DropDownTextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Simple Text Array
        val arrayList = arrayListOf<String>()

        arrayList.add("Option 1")
        arrayList.add("Option 2")
        arrayList.add("Option 3")

        //Set dropDown options to our custom textView and Boom you are done with it
        optionsTv.setOptions(arrayList)

        //This is how you set click listener
        optionsTv.setClickListener(object : DropDownTextView.DropDownClickListener{
            override fun onDropDownClick(value: String?, index: Int) {
                //Value and index of selected item
            }
        })

        //As it is textview so you can get selected text
        var text = optionsTv.text.toString()

        /*
        Second dropdown for bottom example
        It will auto adjust space according to available screen
        */
        options2Tv.setOptions(arrayList)

    }
}
