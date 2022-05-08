package mcp.mobius.waila.overlay.tooltiprenderers;

import mcp.mobius.waila.api.IWailaCommonAccessor;
import mcp.mobius.waila.api.IWailaTooltipRenderer;
import mcp.mobius.waila.config.OverlayConfig;
import mcp.mobius.waila.overlay.DisplayUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class TTRenderFluidBar implements IWailaTooltipRenderer {

    Minecraft mc = Minecraft.getMinecraft();
    ResourceLocation texture = new ResourceLocation("waila", "textures/sprites.png");

    @Override
    public Dimension getSize(String[] params, IWailaCommonAccessor accessor) {
        return new Dimension(126, 24);
    }

    @Override
    public void draw(String[] params, IWailaCommonAccessor accessor) {
        long currentValue = Long.valueOf(params[0]);
        long maxValue = Long.valueOf(params[1]);
        String fluid = String.format(params[2]);

        DisplayUtil.drawString(fluid + ":" , 0, 0, 0xa1bcff, true);

        long progress = (currentValue * 124) / maxValue;

        DisplayUtil.drawString(" ", 0, 0, OverlayConfig.gradient1, false);

        this.mc.getTextureManager().bindTexture(texture);

        DisplayUtil.drawTexturedModalRect(0, 10, 4, 36, 126, 14, 126, 14);

        DisplayUtil.drawString(" ", 0, 0, OverlayConfig.uncolored, false);

        this.mc.getTextureManager().bindTexture(texture);

        DisplayUtil.drawTexturedModalRect(1, 11, 4, 87, 124, 12, 124, 12);
        DisplayUtil.drawTexturedModalRect(1, 11, 4, 75, (int) progress, 12, (int) progress, 12);

        DisplayUtil.drawString(currentValue + "/" + maxValue + " mB" , 5, 13, 0xa1bcff, true);


    }

}
