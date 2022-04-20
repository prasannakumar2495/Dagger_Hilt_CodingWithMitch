package com.example.daggerhilt_codingwithmitch


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var someClass: SomeClass

    @Inject
    lateinit var someClass2: SomeClass2

    @Inject
    lateinit var someClass3: SomeClass3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println("SomeClass ${someClass.doAThing()}")
        println("SomeClass2 ${someClass2.doAThing()}")
        println("SomeClass3 ${someClass3.doAThing()}")
    }
}

class SomeClass
@Inject
constructor(
    private val someDependency: SomeDependency,
) {
    fun doAThing(): String {
        return "Look I got: ${someDependency.getAThing()}"
    }
}

class SomeDependency
@Inject
constructor() {
    fun getAThing(): String {
        return "A Thing"
    }
}

/**
 * Below is the demonstration of Constructor Interface.
 * We cannot do Constructor Injection with an Interface.
 */
class SomeClass2
@Inject
constructor(
    private val someInterface: SomeInterface,
) {
    fun doAThing(): String {
        return "Look I got: ${someInterface.getAThing()}"
    }
}

class SomeInterfaceImplementation
@Inject
constructor(
    private val someString: String,
) : SomeInterface {
    override fun getAThing(): String {
        return someString
    }
}

interface SomeInterface {
    fun getAThing(): String
}

/**
 * Below is the demonstration of 3rd party classes in Constructor Injection.
 * We cannot do Constructor Injection with a 3rd party class.
 */
class SomeClass3
@Inject
constructor(
    private val gson: Gson,
) {
    fun doAThing(): String {
        return gson.toString()
    }
}

/*
@InstallIn(SingletonComponent::class)
@Module
abstract class MyModuleWithBinds {
@Singleton
@Binds
abstract fun bindSomeDependency(someInterfaceImplementation: SomeInterfaceImplementation)
: SomeInterface
}
*/

@InstallIn(SingletonComponent::class)
@Module
class MyModuleWithProvides {
    @Singleton
    @Provides
    fun provideSomeString(): String {
        return "SomeString"
    }

    @Singleton
    @Provides
    fun provideSomeInterface(
        someString: String,
    ): SomeInterface {
        return SomeInterfaceImplementation(someString)
    }

    @Singleton
    @Provides
    fun provideGSON(): Gson {
        return Gson()
    }
}