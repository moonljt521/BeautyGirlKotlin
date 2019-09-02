package com.moon.beautygirlkotlin

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test(){
        val user = User(2,"xiaoming")
        val user2 = user.copy(id = 3)
        println(user.toString())
        println(user2.toString())
        println("user === user : "+ (user === user))
        println("user === user2 : "+ (user === user2))

        println("user == user2 : "+ (user == user2))

    }
}
