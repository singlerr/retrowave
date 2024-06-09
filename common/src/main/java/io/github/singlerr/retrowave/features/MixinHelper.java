package io.github.singlerr.retrowave.features;

import mod.chiselsandbits.api.blockinformation.IBlockInformation;
import mod.chiselsandbits.api.chiseling.ChiselingOperation;
import mod.chiselsandbits.utils.ItemStackUtils;
import net.minecraft.world.entity.player.Player;

public interface MixinHelper {

    default ChiselingOperationExtension getChiselingOpExtension(ChiselingOperation operation){
        return ((ChiselingOperationExtension) (Object) operation);
    }

    default IBlockInformation getHeldBlockState(Player player){
        return ItemStackUtils.getHeldBitBlockInformationFromPlayer(player);
    }
}
