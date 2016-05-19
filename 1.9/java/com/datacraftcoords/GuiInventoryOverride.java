package com.datacraftcoords;


import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class GuiInventoryOverride extends GuiContainer {
	
	public GuiInventoryOverride(Container inventorySlotsIn)
    {
        super(inventorySlotsIn);
    }

	//@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		EntityPlayerSP effectivePlayer = Minecraft.getMinecraft().thePlayer;
		//ContainerPlayer anvil = HUDUtil.GetFieldByReflection(GuiContainer.class, (GuiContainer)this, "anvil", "field_147092_v");
    	//IInventory inventorySlots = HUDUtil.GetFieldByReflection(ContainerPlayer.class, anvil, "inventorySlots", "field_82853_g");
    	
    	InventoryPlayer inventory = effectivePlayer.inventory;
    	ItemStack leftItemStack = inventory.getStackInSlot(0);
        ItemStack rightItemStack = inventory.getStackInSlot(1);
        ItemStack topItemStack = inventory.getStackInSlot(2);
        ItemStack finalItemStack = inventory.getStackInSlot(3);
        
        if(leftItemStack != null)
        {
        	String leftItemRepairCost = null;
        	leftItemRepairCost = "" + leftItemStack.getItemDamage();
        	fontRendererObj.drawString(leftItemRepairCost, 26, 37, 0xffffff);
            //System.out.println(leftItemRepairCost);
        }
    }

	//@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks,
			int mouseX, int mouseY) {
		ResourceLocation texture = new ResourceLocation("Minecraft", "textures/gui/container/inventory.png");
		ScaledResolution res = new ScaledResolution(mc);
		int screenWidth = 176;//194;
		int screenHeight = 166;//168;
		
		
		int screenX = (width - screenWidth ) / 2;
		int screenY = (height - screenHeight) / 2;
		
		GL11.glColor4f(1, 1, 1, 1);
		drawDefaultBackground();
		
		mc.renderEngine.bindTexture(texture);
		drawTexturedModalRect(screenX, screenY, 0, 0, screenWidth, screenHeight);
		
	}


}
