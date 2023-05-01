package io.github.copecone.warp.event

import io.github.copecone.warp.mixins.mixinclass.util.Wrapper
open class ClientEvent{
    var era = Era.PRE
        protected set
    val partialTicks: Float = Wrapper.getMinecraft().tickDelta

    enum class Era {
        PRE, POST
    }
}