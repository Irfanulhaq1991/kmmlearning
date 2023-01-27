
package com.learning.dogify.util

import kotlinx.coroutines.CoroutineDispatcher

internal expect fun getDispatcherProvider(): DispatcherProvider


interface DispatcherProvider{
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}
