package com.learning.dogify.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


internal actual fun getDispatcherProvider(): DispatcherProvider {
 return AndroidDispatcherProvider()
}

private class AndroidDispatcherProvider(
    override val main: CoroutineDispatcher = Dispatchers.Main,
    override val io: CoroutineDispatcher = Dispatchers.IO,
    override val unconfined: CoroutineDispatcher = Dispatchers.Unconfined
) : DispatcherProvider

