package io.github.copecone.warp.events

import me.zero.alpine.event.type.Cancellable
import io.github.copecone.warp.utils.Wrapper
open class wEvent : Cancellable()
{
    var era = Era.PRE
        protected set

    val partialTicks: Float = Wrapper.getMinecraft().tickDelta
    enum class Era {
        PRE, POST
    }
}