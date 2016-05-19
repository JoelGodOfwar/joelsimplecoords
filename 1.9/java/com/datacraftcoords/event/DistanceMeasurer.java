package com.datacraftcoords.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;

public class DistanceMeasurer 
{
	/** Enables/Disables this Mod */
	public static boolean Enabled;
	
	public static Minecraft mc = Minecraft.getMinecraft();

    /**
     * Toggles this Mod on or off
     * @return The state the Mod was changed to
     */
    public static boolean ToggleEnabled()
    {
    	return Enabled = !Enabled;
    }
    
	/** The current mode for this mod */
	public static Modes Mode;
	
	/** The enum for the different types of Modes this mod can have */
    public static enum Modes
    {
        OFF(Localization.get("distancemeasurer.mode.off")),
        SIMPLE(Localization.get("distancemeasurer.mode.simple")),
        COORDINATE(Localization.get("distancemeasurer.mode.complex"));
        
        private String friendlyName;
        
        private Modes(String friendlyName)
        {
        	this.friendlyName = friendlyName;
        }

        /**
         * Sets the next availble mode for this mod
         */
        public static Modes ToggleMode()
        {
        	return Mode = Mode.ordinal() < Modes.values().length - 1 ? Modes.values()[Mode.ordinal() + 1] : Modes.values()[0];
        }
        
        /**
         * Gets the mode based on its internal name as written in the enum declaration
         * @param modeName
         * @return
         */
        public static Modes GetMode(String modeName)
        {
        	try {return Modes.valueOf(modeName);}
        	catch (IllegalArgumentException e) {return values()[0];}
        }
        
        public String GetFriendlyName()
        {
        	return friendlyName;
        }
    }
    
    public static void RenderOntoHUD()
    {
        //if the player is in the world
        //and not looking at a menu
        //and F3 not pressed
        if (DistanceMeasurer.Enabled && Mode != Modes.OFF &&
                (mc.inGameHasFocus || (mc.currentScreen != null && (mc.currentScreen instanceof GuiChat))))
        {
        	String distanceString = CalculateDistanceString();
        	
            ScaledResolution res = new ScaledResolution(mc);//, mc.displayWidth, mc.displayHeight);
            int width = res.getScaledWidth();
            int height = res.getScaledHeight();
            int distanceStringWidth = mc.fontRendererObj.getStringWidth(distanceString);
            //System.out.println("before render");
            width = width/2 - distanceStringWidth/2;
            height = height/2  - 10;
            mc.fontRendererObj.drawStringWithShadow(distanceString, width, height, 0xffffff);
            
            //System.out.println("after render");
            //System.out.println("-----------------");
            //System.out.println("string " + distanceString);
            
        }
    }
    
    
    

    /**
     * Calculates the distance of the block the player is pointing at
     * @return the distance to a block if Distance Measurer is enabled, otherwise "".
     */
    protected static String CalculateDistanceString()
    {
        //MovingObjectPosition objectMouseOver = mc.thePlayer.rayTrace(300, 1);
    	RayTraceResult objectMouseOver = mc.thePlayer.rayTrace(300, 1.0F);	//friendly name is probably rayTrace()
        
        if (objectMouseOver != null && objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK)
        {
            if (Mode == Modes.SIMPLE)
            {
            	int playerX = GetXCoordinate();
                int playerY = GetYCoordinate() + GetEyeCoordinate();
                int playerZ = GetZCoordinate();
                
                double blockX = Math.floor(objectMouseOver.hitVec.xCoord);
                double blockY = Math.floor(objectMouseOver.hitVec.yCoord);
                double blockZ = Math.floor(objectMouseOver.hitVec.zCoord);
                
                double deltaX;
                double deltaY;
                double deltaZ;

                //System.out.println("-----------------");
                //System.out.println("player="+playerX+", "+ playerY+", "+ playerZ);
                //System.out.println("block ="+blockX+", "+ blockY+", "+ blockZ);
                
                ScaledResolution res = new ScaledResolution(mc);//, mc.displayWidth, mc.displayHeight);
                int width = res.getScaledWidth();
                int height = res.getScaledHeight();

                if(Integer.toString(playerX).equals(blockX))
                	deltaX = 1;
                else if(playerX < blockX)
                	deltaX = blockX - playerX + 1;
                else if(playerX > blockX + 0.5 + 1)
                	deltaX = playerX - blockX;
                else
                	deltaX = playerX - blockX + 1;
                mc.fontRendererObj.drawStringWithShadow("playerX " + playerX + " blockX " + blockX + " deltaX " + deltaX, 2, height - 30, 0xffffff);	
                                
                if(Integer.toString(playerY).equals(blockY + 1))
                	deltaY = 1;
                else if(playerY < blockY)
                	deltaY = blockY - playerY + 1;
                else if(playerY > blockY)
                	deltaY = playerY - blockY + 1;
                else
                	deltaY = playerY - blockY + 1;
                mc.fontRendererObj.drawStringWithShadow("playerY " + playerY + " blockY " + blockY + " deltaY " + deltaY, 2, height - 20, 0xffffff);	
                                                
                if(Integer.toString(playerZ).equals(blockZ))
                	deltaZ = 1;
                else if(playerZ < blockZ)
                	deltaZ = blockZ - playerZ + 1;
                else if(playerZ > blockZ)
                	deltaZ = playerZ - blockZ + 1;
                else
                	deltaZ = playerZ - blockZ + 1;
                mc.fontRendererObj.drawStringWithShadow("playerZ " + playerZ + " blockZ " + blockZ + " deltaZ " + deltaZ, 2, height - 10, 0xffffff);	
                                
            	double farthestHorizontalDistance = Math.max(Math.abs(deltaX), Math.abs(deltaZ));
                double farthestDistance = Math.max(Math.abs(deltaY), farthestHorizontalDistance);
                return TextFormatting.GOLD + "[" + String.format("%1$,.0f", farthestDistance) + "]";
            }
            else if (Mode == Modes.COORDINATE)
            {
            	BlockPos pos = objectMouseOver.getBlockPos(); //friendly name is probably getBlockPos()
                return TextFormatting.GOLD + "[" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + "]";
            }
            else
            {
            	return TextFormatting.GOLD + "[???]";
            }
        }
        else
        {
        	return TextFormatting.GOLD + "["+"far"+"]";
        }
    }
    /** Get Player Coordinates */
	public static int GetXCoordinate()
	{
		return (int) Math.floor(mc.thePlayer.posX);
	}
	public static int GetYCoordinate()
	{
		return (int) Math.floor(mc.thePlayer.posY);
	}
	public static int GetZCoordinate()
	{
		return (int) Math.floor(mc.thePlayer.posZ);
	}
	public static int GetEyeCoordinate()
	{
		return (int) Math.floor(mc.thePlayer.eyeHeight);//mc.thePlayer.posY
	}
}
