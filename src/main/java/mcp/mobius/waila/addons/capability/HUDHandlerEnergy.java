package mcp.mobius.waila.addons.capability;

import mcp.mobius.waila.addons.capability.compat.IBigPower;
import mcp.mobius.waila.addons.capability.compat.RedstoneFluxTools;
import mcp.mobius.waila.addons.capability.compat.TeslaTools;
import mcp.mobius.waila.api.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nonnull;
import java.util.List;

public class HUDHandlerEnergy implements IWailaDataProvider {

    static final IWailaDataProvider INSTANCE = new HUDHandlerEnergy();

    @Nonnull
    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        TileEntity te = accessor.getTileEntity();
        if (!config.getConfig("capability.energyinfo") || accessor.getTileEntity() == null)
            return currenttip;

        if (te == null || !te.hasCapability(CapabilityEnergy.ENERGY, null))
            return currenttip;

        if (TeslaTools.isEnergyHandler(te)) {
            long stored = TeslaTools.getEnergy(te);
            long capacity = TeslaTools.getMaxEnergy(te);

            currenttip.add(SpecialChars.getRenderString("waila.progress.energy.RF", String.valueOf(stored), String.valueOf(capacity)));

            return currenttip;
        }
        else if (te instanceof IBigPower) {
            long stored = ((IBigPower) te).getStoredPower();
            long capacity = ((IBigPower) te).getCapacity();

            currenttip.add(SpecialChars.getRenderString("waila.progress.energy.RF", String.valueOf(stored), String.valueOf(capacity)));

            return currenttip;
        }
        else if (RedstoneFluxTools.isEnergyHandler(te)) {
            int stored = RedstoneFluxTools.getEnergy(te);
            int capacity = RedstoneFluxTools.getMaxEnergy(te);

            currenttip.add(SpecialChars.getRenderString("waila.progress.energy.RF", String.valueOf(stored), String.valueOf(capacity)));

            return currenttip;
        }
        else if (te != null && te.hasCapability(CapabilityEnergy.ENERGY, null)) {
            net.minecraftforge.energy.IEnergyStorage handler = te.getCapability(CapabilityEnergy.ENERGY, null);
            if (handler != null) {
                currenttip.add(SpecialChars.getRenderString("waila.progress.energy.RF", String.valueOf(handler.getEnergyStored()), String.valueOf(handler.getMaxEnergyStored())));

                return currenttip;
            }
        }
        else if (accessor.getNBTData().hasKey("forgeEnergy")) {
            NBTTagCompound energyTag = accessor.getNBTData().getCompoundTag("forgeEnergy");
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
            if (te.hasCapability(CapabilityEnergy.ENERGY, null)) {
                IEnergyStorage energyStorage = te.getCapability(CapabilityEnergy.ENERGY, null);
                NBTTagCompound energyTag = new NBTTagCompound();
                energyTag.setInteger("capacity", energyStorage.getMaxEnergyStored());
                energyTag.setInteger("stored", energyStorage.getEnergyStored());
                tag.setTag("forgeEnergy", energyTag);
            }
        }
        return tag;
    }
}
