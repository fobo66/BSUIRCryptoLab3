package io.fobo66.crypto

import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.Options
import org.apache.commons.cli.ParseException
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import javax.xml.bind.DatatypeConverter


fun main(args: Array<String>) {
    var message = "Hello World!"

    val options = Options()
    options.addOption("f", "file", true, "Input file")
    options.addOption("a", "algorithm", true, "Hashing algorithm, either MD5 or SHA1. Defaults to MD5")

    val parser = DefaultParser()

    try {
        val cmd = parser.parse(options, args)

        if (cmd.hasOption('f')) {
            val filePath = cmd.getOptionValue("file")
            System.out.format("Reading cleartext from file %s...%n", filePath)
            message = loadClearTextFromFile(filePath)
        }
        val hash = resolveHashFunction(cmd)

        printResults(message, hash.compute(message.toByteArray()))
    } catch (e: ParseException) {

    }
}

fun resolveHashFunction(cmd: CommandLine): Hash {
    if (cmd.hasOption('a')) {
        return when (cmd.getOptionValue('a').toUpperCase()) {
            "MD5" -> MD5()
            "SHA1", "SHA-1" -> SHA1()
            else -> throw IllegalArgumentException("Wrong algorithm. Must be either SHA1 or MD5")
        }
    }

    return MD5()
}

fun printResults(clearText: String, encryptedText: ByteArray) {
    println("Message: \"$clearText\"")
    println("Hashed message: " + DatatypeConverter.printHexBinary(encryptedText))
}

@Throws(IOException::class)
private fun loadClearTextFromFile(filePath: String): String {
    return String(Files.readAllBytes(Paths.get(filePath)))
}