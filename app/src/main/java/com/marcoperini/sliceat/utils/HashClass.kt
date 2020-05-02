package com.marcoperini.sliceat.utils

import androidx.annotation.VisibleForTesting
import timber.log.Timber
import java.security.MessageDigest

class HashClass {
    companion object{

        fun transformStringToHash(base: String): String? {
            return try {
                val digest: MessageDigest = MessageDigest.getInstance("SHA-256")
                val hash: ByteArray = digest.digest(base.toByteArray(charset("UTF-8")))
                val hexString = StringBuffer()
                for (i in hash.indices) {
                    val hex = Integer.toHexString(0xff and hash[i].toInt())
                    if (hex.length == 1) hexString.append('0')
                    hexString.append(hex)
                    Timber.i("hash %s", hexString.toString())
                }
                hexString.toString()
            } catch (ex: Exception) {
                throw RuntimeException(ex)
            }
        }

    }
}
