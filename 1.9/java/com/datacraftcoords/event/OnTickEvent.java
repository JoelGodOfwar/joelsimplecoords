package com.datacraftcoords.event;

import client.gui.EnumGuiState;

import client.gui.GuiItemDurability;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class OnTickEvent {
	@SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (GuiItemDurability.getGuiState() == EnumGuiState.CLOSING) {
            GuiItemDurability.raiseOffset();
            if (GuiItemDurability.getOffset() >= GuiItemDurability.getCloseSize()) {
                GuiItemDurability.setGuiState(EnumGuiState.CLOSED);
            }
        }
        if (GuiItemDurability.getGuiState() == EnumGuiState.CLOSED && GuiItemDurability.getOffset() < GuiItemDurability.getCloseSize()) {
            GuiItemDurability.setGuiState(EnumGuiState.CLOSING);
        }
        if (GuiItemDurability.getGuiState() == EnumGuiState.OPENING) {
            GuiItemDurability.lowerOffset();
            if (GuiItemDurability.getOffset() <= 0) {
                GuiItemDurability.setGuiState(EnumGuiState.OPEN);
            }
        }
        if (GuiItemDurability.getOverrideTime() > 0) {
            GuiItemDurability.decOverrideTime();
        }
    }

}
