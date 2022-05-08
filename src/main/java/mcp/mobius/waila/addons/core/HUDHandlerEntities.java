package mcp.mobius.waila.addons.core;

import com.google.common.base.Strings;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mcp.mobius.waila.config.FormattingConfig;
import mcp.mobius.waila.utils.ModIdentification;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import javax.annotation.Nonnull;
import java.util.List;

import static mcp.mobius.waila.api.SpecialChars.getRenderString;

public class HUDHandlerEntities implements IWailaEntityProvider {

    static final IWailaEntityProvider INSTANCE = new HUDHandlerEntities();

    public static int nhearts = 20;
    public static float maxhpfortext = 40.0f;

    @Nonnull
    @Override
    public List<String> getWailaHead(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {
        if (!Strings.isNullOrEmpty(FormattingConfig.entityFormat)) {
            try {
                currenttip.add("\u00a7r" + String.format(FormattingConfig.entityFormat, entity.getName()));
            } catch (Exception e) {
                currenttip.add("\u00a7r" + String.format(FormattingConfig.entityFormat, "Unknown"));
            }
        } else currenttip.add("Unknown");

        return currenttip;
    }

    @Nonnull
    @Override
    public List<String> getWailaBody(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {
        if (config.getConfig("general.showhp") && entity instanceof EntityLivingBase) {
            nhearts = nhearts <= 0 ? 20 : nhearts;
            float health = ((EntityLivingBase) entity).getHealth() / 2.0f;
            float maxhp = ((EntityLivingBase) entity).getMaxHealth() / 2.0f;

            if (((EntityLivingBase) entity).getMaxHealth() > maxhpfortext)
                currenttip.add(String.format(I18n.translateToLocal("hud.msg.health") + ": %.0f / %.0f", ((EntityLivingBase) entity).getHealth(), ((EntityLivingBase) entity).getMaxHealth()));
            else
                currenttip.add(getRenderString("waila.health", String.valueOf(nhearts), String.valueOf(health), String.valueOf(maxhp)));
        }

        return currenttip;
    }

    @Nonnull
    @Override
    public List<String> getWailaTail(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {
        if (!Strings.isNullOrEmpty(FormattingConfig.modNameFormat) && !Strings.isNullOrEmpty(getEntityMod(entity)))
            currenttip.add(String.format(FormattingConfig.modNameFormat, getEntityMod(entity)));

        return currenttip;
    }

    private static String getEntityMod(Entity entity) {
        EntityEntry entityEntry = EntityRegistry.getEntry(entity.getClass());
        if (entityEntry == null)
            return "Unknown";

        ModContainer container = ModIdentification.findModContainer(entityEntry.getRegistryName().getNamespace());
        return container.getName();
    }
}