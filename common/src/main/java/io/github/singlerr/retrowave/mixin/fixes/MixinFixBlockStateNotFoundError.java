package io.github.singlerr.retrowave.mixin.fixes;

import mod.chiselsandbits.api.blockinformation.IBlockInformation;
import mod.chiselsandbits.block.entities.storage.SimpleStateEntryPalette;
import mod.chiselsandbits.block.entities.storage.SimpleStateEntryStorage;
import mod.chiselsandbits.utils.ByteArrayUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.BitSet;

@Mixin(SimpleStateEntryStorage.class)
public abstract class MixinFixBlockStateNotFoundError {

    @Shadow protected abstract int doCalculatePositionIndex(int x, int y, int z);

    @Shadow private BitSet data;

    @Shadow private int entryWidth;

    @Shadow @Final private SimpleStateEntryPalette palette;

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public IBlockInformation getBlockInformation(final int x, final int y, final int z) {
        final int offSetIndex = doCalculatePositionIndex(x, y, z);
        final int blockStateId = ByteArrayUtils.getValueAt(data, entryWidth, offSetIndex);
        try{
            return palette.getBlockState(blockStateId);
        }catch (Exception e){
            return palette.getBlockState(blockStateId);
        }
    }
}
