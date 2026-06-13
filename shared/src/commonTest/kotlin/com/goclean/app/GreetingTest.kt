package com.goclean.app

import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertNotNull

/**
 * Example test for the Greeting class
 * Tests that the greeting message is generated correctly
 */
class GreetingTest {

    @Test
    fun testGreetingIsNotNull() {
        val greeting = Greeting()
        val message = greeting.greet()
        assertNotNull(message, "Greeting message should not be null")
    }

    @Test
    fun testGreetingContainsHello() {
        val greeting = Greeting()
        val message = greeting.greet()
        assertContains(message, "Hello", ignoreCase = true, message = "Greeting should contain 'Hello'")
    }

    @Test
    fun testGreetingContainsPlatformInfo() {
        val greeting = Greeting()
        val message = greeting.greet()
        assertContains(message, "(", message = "Greeting should contain platform version in parentheses")
    }
}
