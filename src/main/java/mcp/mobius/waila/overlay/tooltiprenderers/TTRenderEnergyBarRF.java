package mcp.mobius.waila.overlay.tooltiprenderers;

import mcp.mobius.waila.api.IWailaCommonAccessor;
import mcp.mobius.waila.api.IWailaTooltipRenderer;
import mcp.mobius.waila.api.SpecialChars;
import mcp.mobius.waila.config.OverlayConfig;
import mcp.mobius.waila.overlay.DisplayUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class TTRenderEnergyBarRF implements IWailaTooltipRenderer {

    Minecraft mc = Minecraft.getMinecraft();
    ResourceLocation texture = new ResourceLocation("waila", "textures/sprites.png");

    @Override
    public Dimension getSize(String[] params, IWailaCommonAccessor accessor) {
        return new Dimension(126, 14);
    }

    @Override
    public void draw(String[] params, IWailaCommonAccessor accessor) {
        long currentValue = Long.valueOf(params[0]);
        long maxValue = Long.valueOf(params[1]);

        long progress = (currentValue * 124) / maxValue;

        DisplayUtil.drawString(" ", 0, 0, OverlayConfig.gradient1, true);

        this.mc.getTextureManager().bindTexture(texture);

        DisplayUtil.drawTexturedModalRect(0, 0, 4, 36, 126, 14, 126, 14);

        DisplayUtil.drawString(" ", 0, 0, OverlayConfig.uncolored, false);

        this.mc.getTextureManager().bindTexture(texture);

        DisplayUtil.drawTexturedModalRect(1, 1, 4, 63, 124, 12, 124, 12);
        DisplayUtil.drawTexturedModalRect(1, 1, 4, 51, (int) progress, 12, (int) progress, 12);

        DisplayUtil.drawString(currentValue + "/" + maxValue + " RF" , 5, 3, 0xffa1a1, true);


    }

}
