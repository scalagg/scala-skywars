package gg.scala.skywars.plugin.game.frontend

import gg.scala.cgs.common.player.CgsGamePlayer
import gg.scala.cgs.common.player.nametag.CgsGameNametagAdapter
import net.evilblock.cubed.nametag.NametagInfo
import net.evilblock.cubed.nametag.NametagProvider
import net.evilblock.cubed.util.CC

/**
 * @author GrowlyX
 * @since 12/3/2021
 */
object SkyWarsNametagAdapter : CgsGameNametagAdapter
{
    @JvmStatic
    val RED_TAG = NametagProvider
        .createNametag(CC.RED, "")

    @JvmStatic
    val GREEN_TAG = NametagProvider
        .createNametag(CC.GREEN, "")

    override fun computeNametag(
        viewer: CgsGamePlayer,
        target: CgsGamePlayer
    ): NametagInfo
    {
        if (viewer == target)
        {
            return GREEN_TAG
        }

        return RED_TAG
    }
}
