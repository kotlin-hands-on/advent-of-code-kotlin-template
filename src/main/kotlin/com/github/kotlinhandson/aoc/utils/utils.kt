package com.github.kotlinhandson.aoc.utils

import java.math.BigInteger
import java.security.MessageDigest

/**
 * Maps a string containing integers in new lines to a list of integers.
 */
fun String.ints() = lines().map(String::toInt)

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)
