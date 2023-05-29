package io.github.copecone.warp.events

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.client.gui.hud.ClientBossBar
import net.minecraft.entity.Entity
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.world.World

class AddCollisionBoxToListEvent(
    val block: Block,
    val state: BlockState,
    val world: World,
    val pos: BlockPos,
    val entityBox: Box,
    val collidingBoxes: MutableList<Box>,
    val entity: Entity,
    val isBool: Boolean
) : wEvent()

class ApplyFogEvent : wEvent()
open class EntityEvent(val entity: Entity) : wEvent() {
    class EntityCollision(
        entity: Entity,
        var x: Double,
        var y: Double,
        var z: Double
    ) : EntityEvent(entity)

    class UpdateHealth(entity: Entity, val health: Float) : EntityEvent(entity)
}

open class RenderBossBarEvent : wEvent() {
    class GetIterator(var iterator: Iterator<ClientBossBar>) : RenderBossBarEvent()
    class GetText(val bossBar: ClientBossBar, var text: Text) : RenderBossBarEvent()
    class Spacing(var spacing: Int) : RenderBossBarEvent()
}

class UpdateLookEvent(
    val deltaX: Double,
    val deltaY: Double
) : wEvent()