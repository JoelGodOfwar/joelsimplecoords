package com.datacraftcoords.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;

public abstract class HUDModBase {

	protected static final Minecraft mc = Minecraft.getMinecraft();
	protected static final RenderItem itemRenderer = mc.getRenderItem();
	
}
