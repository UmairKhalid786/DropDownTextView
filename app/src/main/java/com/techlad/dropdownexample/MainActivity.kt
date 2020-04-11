package com.techlad.dropdownexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val arrayList = arrayListOf<String>()
        arrayList.add("Option 1")
        arrayList.add("Option 2")
        arrayList.add("Option 3")

        optionsTv.setOptions(arrayList)

        options2Tv.setOptions(arrayList)
    }
}
