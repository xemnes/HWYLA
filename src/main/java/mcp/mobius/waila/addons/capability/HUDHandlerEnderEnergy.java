package mcp.mobius.waila.addons.capability;

import crazypants.enderio.base.TileEntityEio;
import crazypants.enderio.base.power.IPowerStorage;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.SpecialChars;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import java.util.List;

public class HUDHandlerEnderEnergy implements IWailaDataProvider {

    static final IWailaDataProvider INSTANCE = new HUDHandlerEnderEnergy();

    long rf, maxrf, fillMax, fillCur;

    @Nonnull
    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if (!config.getConfig("capability.energyinfo") || accessor.getTileEntity() == null)
            return currenttip;

        if (accessor.getNBTData().hasKey("forgeEnergyEnder")) {
            NBTTagCompound energyTag = accessor.getNBTData().getCompoundTag("forgeEnergyEnder");
            long stored = energyTag.getInteger("stored");
            long capacity = energyTag.getInteger("capacity");

            currenttip.add(SpecialChars.getRenderString("waila.progress.energy.RF", String.valueOf(stored), String.valueOf(capacity)));

            return currenttip;
        }

        return currenttip;
    }

    @Nonnull
    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
        if (te != null) {
            if (te.hasCapability(CapabilityEnergy.ENERGY, null) && te instanceof TileEntityEio) {
                IPowerStorage energyStorage = (IPowerStorage) te.getCapability(CapabilityEnergy.ENERGY, null);
                NBTTagCompound energyTag = new NBTTagCompound();
                energyTag.setLong("capacity", energyStorage.getMaxEnergyStoredL());
                energyTag.setLong("stored", energyStorage.getEnergyStoredL());
                tag.setTag("forgeEnergyEnder", energyTag);
            }
        }
        return tag;
    }
}
