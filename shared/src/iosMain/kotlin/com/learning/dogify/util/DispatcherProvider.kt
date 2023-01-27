package com.learning.dogify.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


internal actual fun getDispatcherProvider(): DispatcherProvider {
  return IoSDispatcherProvider()
}


private class IoSDispatcherProvider(
    override val main: CoroutineDispatcher = Dispatchers.Main,
    override val io: CoroutineDispatcher= Dispatchers.Default,
    override val unconfined: CoroutineDispatcher = Dispatchers.Unconfined
) : DispatcherProvider


