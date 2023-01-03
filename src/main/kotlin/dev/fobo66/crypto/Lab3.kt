package dev.fobo66.crypto

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import java.io.IOException
import java.math.BigInteger
import java.nio.file.Paths
import kotlin.io.path.readText

fun main(args: Array<String>) {
    var message = "Hello World!"

    val parser = ArgParser("lab3")
    val inputFile by parser.option(ArgType.String, shortName = "f", fullName = "file", description = "Input file")
    val algorithm by parser.option(
        ArgType.Choice<Algorithm>(),
        shortName = "a",
        fullName = "algorithm",
        description = "Hashing algorithm, either MD5 or SHA1. Defaults to MD5"
    ).default(Algorithm.MD5)


    parser.parse(args)

    if (inputFile != null) {
        val filePath = inputFile!!
        println("Reading cleartext from file $filePath...")
        message = loadClearTextFromFile(filePath)
    }
    val hash = resolveHashFunction(algorithm)

    printResults(message, hash.compute(message.toByteArray()), algorithm)
}

fun resolveHashFunction(algorithm: Algorithm): Hash {
    return when (algorithm) {
        Algorithm.MD5 -> MD5()
        Algorithm.SHA1 -> SHA1()
    }
}

fun printResults(clearText: String, encryptedText: ByteArray, algorithm: Algorithm) {
    println("Message: \"$clearText\"")
    println("Hashed message: " + BigInteger(encryptedText).toString(16))
    println("Used algorithm: $algorithm")
}

@Throws(IOException::class)
private fun loadClearTextFromFile(filePath: String): String {
    return Paths.get(filePath).readText()
}
