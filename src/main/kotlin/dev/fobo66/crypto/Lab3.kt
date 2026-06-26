package dev.fobo66.crypto

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.transform.theme
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.path
import java.math.BigInteger
import kotlin.io.path.readText

private const val HEX_RADIX = 16

class Lab3 : CliktCommand() {
    val inputFile by option("-f", "--file")
        .path(mustBeReadable = true, canBeDir = false, mustExist = true)
        .help { theme.info("Input file") }

    val algorithm by option("-a", "--algorithm")
        .choice("MD5" to Algorithm.MD5, "SHA1" to Algorithm.SHA1)
        .help { theme.info("Hashing algorithm, either MD5 or SHA1. Defaults to MD5") }
        .default(Algorithm.MD5)

    override fun run() {
        var message = "Hello World!"

        inputFile?.let {
            echo("Reading cleartext from file ${it.fileName}...")
            message = it.readText()
        }
        val hash = when (algorithm) {
            Algorithm.MD5 -> MD5()
            Algorithm.SHA1 -> SHA1()
        }

        printResults(message, hash.compute(message.toByteArray()), algorithm)
    }

    private fun printResults(
        clearText: String,
        encryptedText: ByteArray,
        algorithm: Algorithm,
    ) {
        echo("Message: \"$clearText\"")
        echo("Hashed message: ${BigInteger(1, encryptedText).toString(HEX_RADIX)}")
        echo("Used algorithm: $algorithm")
    }

}

fun main(args: Array<String>) = Lab3().main(args)
