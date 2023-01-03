package dev.fobo66.crypto

interface Hash {
    fun compute(message: ByteArray): ByteArray
}
