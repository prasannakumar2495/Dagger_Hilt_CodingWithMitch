package com.example.daggerhilt_codingwithmitch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity2 : AppCompatActivity() {

    //field injection
    @Inject
    lateinit var someClass: SomeClassSample

    /**
    @Inject
    lateinit var randomClass: RandomClass

    We cannot FragmentScoped injection in Activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println(someClass.someThing())
        println(someClass.someOtherThing())
    }
}

@AndroidEntryPoint
class FragmentClass : Fragment() {
    @Inject
    lateinit var someClass: SomeClassSample

    @Inject
    lateinit var randomClass: RandomClass
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println(randomClass.randomFunction())
    }
}

@ActivityScoped
class SomeClassSample @Inject constructor(
//below line is called as constructor injection
    private val someOtherClass: SomeOtherClass,
) {

    fun someThing(): String = "Do Something..."
    fun someOtherThing(): String = someOtherClass.someOtherThing()

}

class SomeOtherClass @Inject constructor() {
    fun someOtherThing(): String = "Do some other thing..."
}

@FragmentScoped
class RandomClass @Inject constructor() {
    fun randomFunction(): String = "This is a fragment scoped function..."
}