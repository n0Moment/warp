
@file:Suppress("UNUSED_PARAMETER", "unused")

package io.github.copecone.warp.event

import com.google.common.base.Predicate
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.client.gui.hud.ClientBossBar
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.network.AbstractClientPlayerEntity
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.client.render.Camera
import net.minecraft.client.render.LightmapTextureManager
import net.minecraft.client.util.Window
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.client.world.ClientWorld
import net.minecraft.entity.Entity
import net.minecraft.entity.MovementType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.network.PacketBundler
import net.minecraft.network.packet.s2c.play.ChunkDataS2CPacket
import net.minecraft.text.Text
import net.minecraft.util.hit.EntityHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.Direction
import net.minecraft.util.math.MatrixUtil
import net.minecraft.util.math.Vec3d
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.chunk.Chunk
import org.joml.Matrix4f

class AddCollisionBoxToListEvent(
    val block: Block,
    val state: BlockState,
    val world: World,
    val pos: BlockPos,
    val entityBox: Box,
    val collidingBoxes: MutableList<Box>,
    val entity: Entity,
    val isBool: Boolean
) : ClientEvent()

class ApplyFogEvent : ClientEvent()

class BindEvent(val key: Int, val scancode: Int, val i: Int) : ClientEvent() {
    val pressed = i != 0
    //val ingame
}

class CharTypedEvent(val char: Char) : ClientEvent()

class CameraHurtEvent(val tickDelta: Float) : ClientEvent()

class CanBeControlledEvent(val entity: Entity, var canBeSteered: Boolean?) : ClientEvent()

open class ChunkEvent private constructor(val chunk: Chunk?) : ClientEvent() {
    class Load(chunk: Chunk?, val packet: ChunkDataS2CPacket) : ChunkEvent(chunk)
    class Unload(chunk: Chunk?) : ChunkEvent(chunk)
}

class ClipAtLedgeEvent(val player: PlayerEntity, var clip: Boolean? = null) : ClientEvent()

class CloseScreenInPortalEvent(val screen: Screen?) : ClientEvent()

class DisplaySizeChangedEvent

open class EntityEvent(val entity: Entity) : ClientEvent() {
    class EntityCollision(
        entity: Entity,
        var x: Double,
        var y: Double,
        var z: Double
    ) : EntityEvent(entity)

    class UpdateHealth(entity: Entity, val health: Float) : EntityEvent(entity)
}

class EntityJoinWorldEvent(val id: Int, val entity: Entity) : ClientEvent()

class EntityVelocityMultiplierEvent(val entity: Entity?, var multiplier: Float) : ClientEvent()

class MoveEntityFluidEvent(val entity: Entity, var movement: Vec3d) : ClientEvent()

class PlayerAttackBlockEvent(val position: BlockPos, val facing: Direction)

class PlayerAttackEntityEvent(val entity: Entity) : ClientEvent()

class PlayerMoveEvent(val type: MovementType, val vec: Vec3d) : ClientEvent()

open class RenderBossBarEvent : ClientEvent() {
    class GetIterator(var iterator: Iterator<ClientBossBar>) : RenderBossBarEvent()
    class GetText(val bossBar: ClientBossBar, var text: Text) : RenderBossBarEvent()
    class Spacing(var spacing: Int) : RenderBossBarEvent()
}

open class RenderEvent private constructor(private val stage: Stage) : ClientEvent() {
    enum class Stage {
        WORLD, SCREEN
    }

    class World(
        val tickDelta: Float,
        val matrixStack: MatrixStack,
        val projection: Matrix4f
    ) :
        RenderEvent(Stage.WORLD) {

        init {
            era = Era.POST
        }
    }
}

class RenderGuiEvent(
    val window: Window,
    val matrixStack: MatrixStack
) : ClientEvent()

class RenderPlayerNametagEvent(val entity: AbstractClientPlayerEntity) : ClientEvent()

class RenderWeatherEvent(
    val manager: LightmapTextureManager,
    val f: Float,
    val d: Double,
    val e: Double,
    val g: Double
) : ClientEvent()

open class ScreenEvent(var screen: Screen?) : ClientEvent() {

    class Displayed(screen: Screen?) : ScreenEvent(screen)
    class Closed(screen: Screen?) : ScreenEvent(screen)
}

class TargetEntityEvent(
    val entity: Entity,
    val vec3d: Vec3d,
    val vec3d2: Vec3d,
    val box: Box,
    val predicate: Predicate<Entity>,
    val d: Double,
    var trace: EntityHitResult?
) : ClientEvent()





