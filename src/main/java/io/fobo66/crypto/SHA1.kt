/*
 * A Kotlin implementation of the Secure Hash Algorithm, SHA-1, as defined
 * in FIPS PUB 180-1
 * Copyright (C) Sam Ruby 2004
 * All rights reserved
 *
 * Based on code Copyright (C) Paul Johnston 2000 - 2002.
 * See http://pajhome.org.uk/site/legal.html for details.
 *
 * Converted to Java by Russell Beattie 2004
 * Base64 logic and inlining by Sam Ruby 2004
 * Bug fix correcting single bit error in base64 code by John Wilson
 *
 * Changed so that input and output are byte arrays (removing Base64),
 *   added update(byte[]) and digest() methods to make it work
 *   more like java.security.MessageDigest, David Hovemeyer 2012
 *
 * Converted to Kotlin by Andrey Mukamolov 2018
 *
 *                                BSD License
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials
 * provided with the distribution.
 *
 * Neither the name of the author nor the names of its contributors may be
 * used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package io.fobo66.crypto


class SHA1 : Hash {

    private var a = 1732584193
    private var b = -271733879
    private var c = -1732584194
    private var d = 271733878
    private var e = -1009589776

    override fun compute(message: ByteArray): ByteArray {
//        val md = MessageDigest.getInstance("SHA-1")
//        return md.digest(message)
        reset()

        val blocks = IntArray(((message.size + 8 shr 6) + 1) * 16)
        var i = 0

        while (i < message.size) {
            blocks[i shr 2] = blocks[i shr 2] or (message[i].toInt() shl 24 - i % 4 * 8)
            i++
        }

        blocks[i shr 2] = blocks[i shr 2] or (0x80 shl 24 - i % 4 * 8)
        blocks[blocks.size - 1] = message.size * 8

        // calculate 160 bit SHA1 hash of the sequence of blocks

        val w = IntArray(80)

        i = 0
        while (i < blocks.size) {
            val olda = a
            val oldb = b
            val oldc = c
            val oldd = d
            val olde = e

            for (j in 0..79) {
                w[j] = if (j < 16)
                    blocks[i + j]
                else
                    rotateLeft(w[j - 3] xor w[j - 8] xor w[j - 14] xor w[j - 16], 1)

                val t = (rotateLeft(a, 5) + e + w[j] +
                        when {
                            j < 20 -> 1518500249 + (b and c or (b.inv() and d))
                            j < 40 -> 1859775393 + (b xor c xor d)
                            j < 60 -> -1894007588 + (b and c or (b and d) or (c and d))
                            else -> -899497514 + (b xor c xor d)
                        })
                e = d
                d = c
                c = rotateLeft(b, 30)
                b = a
                a = t
            }

            a += olda
            b += oldb
            c += oldc
            d += oldd
            e += olde
            i += 16
        }

        return createByteArrayDigest()
    }

    private fun reset() {
        a = 1732584193
        b = -271733879
        c = -1732584194
        d = 271733878
        e = -1009589776
    }

    /**
     * Convert result to a byte array
     */
    private fun createByteArrayDigest(): ByteArray {
        val digest = ByteArray(20)
        fill(a, digest, 0)
        fill(b, digest, 4)
        fill(c, digest, 8)
        fill(d, digest, 12)
        fill(e, digest, 16)
        return digest
    }

    /**
     * Bitwise rotate a 32-bit number to the left
     */
    private fun rotateLeft(num: Int, cnt: Int): Int {
        return num shl cnt or num.ushr(32 - cnt)
    }

    private fun fill(value: Int, arr: ByteArray, off: Int) {
        arr[off + 0] = (value shr 24 and 0xff).toByte()
        arr[off + 1] = (value shr 16 and 0xff).toByte()
        arr[off + 2] = (value shr 8 and 0xff).toByte()
        arr[off + 3] = (value shr 0 and 0xff).toByte()
    }
}