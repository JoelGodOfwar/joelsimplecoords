package com.datacraftcoords.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;

public class Waypoint {
    public int x;
    public int y;
    public int z;
    public String name;
    public String symbol;
    public int color;
    public boolean disabled = false;
    public int type = 0;
    public boolean rotation = false;
    public int yaw = 0;
    public double lastDistance = 0.0;
    public static int dim = 0;

    public Waypoint(int x, int y, int z, String name, String symbol, int color, int optionalDim) {
        this(x, y, z, name, symbol, color, 0, dim);
    }

    public Waypoint(int x, int y, int z, String name, String symbol, int color, int type, int optionalDim) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.symbol = symbol;
        this.color = color;
        this.type = type;
        this.name = name;
        Waypoint.dim = optionalDim;
    }

    public double getDistanceSq(double x, double y, double z) {
        double d3 = (double)this.x - x;
        double d4 = (double)this.y - y;
        double d5 = (double)this.z - z;
        return d3 * d3 + d4 * d4 + d5 * d5;
    }

    public String getName() {
        return I18n.format((String)this.name, (Object[])new Object[0]);
    }
    
    public static int getDim() {
        return Waypoint.dim;
    }

    public void drawIconInWorld(VertexBuffer vertexBuffer, Tessellator tessellator, FontRenderer fontrenderer, String name, String distance, float textSize, boolean showDistance) {
        GlStateManager.scale((float)InterfaceHandler.waypointsScale, (float)InterfaceHandler.waypointsScale, (float)1.0f);
        if (this.type == 0) {
            int c = InterfaceHandler.COLORS[this.color];
            float l = (float)(c >> 16 & 255) / 255.0f;
            float i1 = (float)(c >> 8 & 255) / 255.0f;
            float j1 = (float)(c & 255) / 255.0f;
            int s = fontrenderer.getStringWidth(this.symbol) / 2;
            GlStateManager.disableTexture2D();
            vertexBuffer.begin(7, DefaultVertexFormats.POSITION);
            GlStateManager.color((float)l, (float)i1, (float)j1, (float)0.47058824f);
            vertexBuffer.pos(-5.0, -9.0, 0.0).endVertex();
            vertexBuffer.pos(-5.0, 0.0, 0.0).endVertex();
            vertexBuffer.pos(4.0, 0.0, 0.0).endVertex();
            vertexBuffer.pos(4.0, -9.0, 0.0).endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();
            fontrenderer.drawString(this.symbol, - s, -8, 553648127);
            fontrenderer.drawString(this.symbol, - s, -8, -1);
        } else if (this.type == 1) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(InterfaceHandler.guiTextures);
            float f = 0.00390625f;
            float f1 = 0.00390625f;
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)0.78431374f);
            vertexBuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            vertexBuffer.pos(-5.0, -9.0, 0.0).tex(0.0, (double)(78.0f * f1)).endVertex();
            vertexBuffer.pos(-5.0, 0.0, 0.0).tex(0.0, (double)(87.0f * f1)).endVertex();
            vertexBuffer.pos(4.0, 0.0, 0.0).tex((double)(9.0f * f), (double)(87.0f * f1)).endVertex();
            vertexBuffer.pos(4.0, -9.0, 0.0).tex((double)(9.0f * f), (double)(78.0f * f1)).endVertex();
            tessellator.draw();
            if (!showDistance) {
                name = this.getName();
                if (!InterfaceHandler.keepWaypointNames) {
                    textSize = 1.0f;
                }
            }
        }
        if (Minecraft.getMinecraft().isUnicode()) {
            textSize *= 1.5f;
        }
        boolean showingName = name.length() > 0;
        GlStateManager.translate((float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.scale((float)(textSize / 2.0f), (float)(textSize / 2.0f), (float)1.0f);
        if (distance.length() > 0) {
            int t = fontrenderer.getStringWidth(distance) / 2;
            GlStateManager.disableTexture2D();
            GlStateManager.color((float)0.0f, (float)0.0f, (float)0.0f, (float)0.27450982f);
            vertexBuffer.begin(7, DefaultVertexFormats.POSITION);
            vertexBuffer.pos((double)(- t) - 1.0, (double)(showingName ? 10 : 0), 0.0).endVertex();
            vertexBuffer.pos((double)(- t) - 1.0, 9.0 + (double)(showingName ? 10 : 0), 0.0).endVertex();
            vertexBuffer.pos((double)t, 9.0 + (double)(showingName ? 10 : 0), 0.0).endVertex();
            vertexBuffer.pos((double)t, (double)(showingName ? 10 : 0), 0.0).endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();
            fontrenderer.drawString(distance, - t, 1 + (showingName ? 10 : 0), 553648127);
            fontrenderer.drawString(distance, - t, 1 + (showingName ? 10 : 0), -1);
        }
        if (showingName) {
            int t = fontrenderer.getStringWidth(name) / 2;
            fontrenderer.drawString(name, - t, 1, 553648127);
            fontrenderer.drawString(name, - t, 1, -1);
        }
    }

    public void drawIconOnGUI(int drawX, int drawY) {
        if (this.type == 0) {
            int rectX2 = drawX + 9;
            int rectY2 = drawY + 9;
            Gui.drawRect((int)drawX, (int)drawY, (int)rectX2, (int)rectY2, (int)InterfaceHandler.COLORS[this.color]);
            int j = Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.symbol) / 2;
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.symbol, (float)(drawX + 5 - j), (float)(drawY + 1), InterfaceHandler.radarPlayers.hashCode());
        } else if (this.type == 1) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(InterfaceHandler.guiTextures);
            Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(drawX, drawY, 0, 78, 9, 9);
        }
    }
}

