package io.fobo66.crypto

import javax.xml.bind.DatatypeConverter

fun main(args: Array<String>) {
    val md5 = MD5()
    val testStrings = arrayOf(
            "",
            "a",
            "abc",
            "message digest",
            "abcdefghijklmnopqrstuvwxyz",
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789",
            "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
    )

    for (s in testStrings) {
        printResults(s, md5.compute(s.toByteArray()))
    }
}

fun printResults(clearText: String, encryptedText: ByteArray) {
    println("Clear text: \"$clearText\"")
    println("Encrypted text: " + DatatypeConverter.printHexBinary(encryptedText))
}