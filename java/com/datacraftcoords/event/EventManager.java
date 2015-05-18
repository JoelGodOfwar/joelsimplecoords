package com.datacraftcoords.event;



import org.lwjgl.opengl.GL11;

import com.datacraftcoords.Configs;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventManager {
	
	//public static String daConfig;
	/** Enables/Disables this Mod */
	public static boolean Enabled;
	public static boolean chunkEnabled;

    /**
     * Toggles this Mod on or off
     * @return The state the Mod was changed to
     */
    public static void ToggleEnabled()
    {
    		Enabled = !Enabled;
    		Configs.SaveConfigSettings();
    	        	
    }
	
	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent e){
		/** Checks if Enabled, if it is Enabled process code. */
		if(EventManager.Enabled)
        {
			/** Checks if experience bar is displayed or not. If it is start code. */
		if(e.type == ElementType.JUMPBAR || e.type == ElementType.EXPERIENCE){
			//String DefaultChatStringFormat = "[{x}, {y}, {z}]";
			/** Gets Biome, and compares it to nether. Then alters the coords displayed based on results */
			String varBiome = getBiome();
			if(!varBiome.equals("Hell")){
				/** We are NOT in the Nether so display Overworld, and Nether Coords. */
				String coordinateString = "{x}, {y}, {z}";
	        	coordinateString = coordinateString.replace("{x}", Integer.toString(GetXCoordinate()));
	        	coordinateString = coordinateString.replace("{y}", Integer.toString(GetYCoordinate()));
	        	coordinateString = coordinateString.replace("{z}", Integer.toString(GetZCoordinate()));
	        	/** Display Overworld coords */
	        	mc.fontRendererObj.drawString(EnumChatFormatting.YELLOW + "O: " + coordinateString + " " + EnumChatFormatting.RED + yawCalc() + EnumChatFormatting.WHITE + " T:" + CalculateMessageForInfoLine(null), 2, 2, 0xffffff);
	        	
	        	String coordinateString2 = "{x}, {y}, {z}";
	        	coordinateString2 = coordinateString2.replace("{x}", Integer.toString(GetXCoordinate() / 8));
	        	coordinateString2 = coordinateString2.replace("{y}", Integer.toString(GetYCoordinate()));
	        	coordinateString2 = coordinateString2.replace("{z}", Integer.toString(GetZCoordinate() / 8));
	        	/** Display Nether Coords, Overworld Dived by 8*/
	        	mc.fontRendererObj.drawString(EnumChatFormatting.LIGHT_PURPLE + "N: " + coordinateString2 + EnumChatFormatting.GREEN + " XP:" + getXP(), 2, 12, 0xffffff);
	        	//System.out.println("Biome " + varBiome + " - coord1 " + coordinateString + " - coord2 " + coordinateString2);
	        	}
			else if(varBiome.equals("Hell")){
				/** We ARE in the Nether so display Nether, and Overworld Coords. */
				String coordinateString3 = "{x}, {y}, {z}";
	        	coordinateString3 = coordinateString3.replace("{x}", Integer.toString(GetXCoordinate()));
	        	coordinateString3 = coordinateString3.replace("{y}", Integer.toString(GetYCoordinate()));
	        	coordinateString3 = coordinateString3.replace("{z}", Integer.toString(GetZCoordinate()));
	        	/** Display Nether Coords */
	        	mc.fontRendererObj.drawString(EnumChatFormatting.LIGHT_PURPLE + "N: " + coordinateString3 + " " + EnumChatFormatting.RED + yawCalc() + EnumChatFormatting.WHITE + " T:" + CalculateMessageForInfoLine(null), 2, 2, 0xffffff);
	        	
	        	String coordinateString4 = "{x}, {y}, {z}";
	        	coordinateString4 = coordinateString4.replace("{x}", Integer.toString((GetXCoordinate() * 8)));
	        	coordinateString4 = coordinateString4.replace("{y}", Integer.toString(GetYCoordinate()));
	        	coordinateString4 = coordinateString4.replace("{z}", Integer.toString((GetZCoordinate() * 8)));
	        	/** Display Overworld Coords, Nether Multiplied by 8 */
	        	mc.fontRendererObj.drawString(EnumChatFormatting.YELLOW + "O: " + coordinateString4 + EnumChatFormatting.GREEN + " XP:" + getXP(), 2, 12, 0xffffff);
	        	//System.out.println("Biome " + varBiome + " - coord3 " + coordinateString3 + " - coord4 " + coordinateString4);
			}
			
        	//Minecraft.getMinecraft().fontRendererObj.drawString("" + coordFormat + "", 2, 2, 0xffffff);
			/** Display Biome */
			mc.fontRendererObj.drawString("B: " + getBiome() + "", 2, 22, 0xffffff);
			int chunkX = GetXCoordinate() / 16;
			int chunkY = GetYCoordinate() / 16;
			int chunkZ = GetZCoordinate() / 16;
			int inChunkX = GetXCoordinate() % 16;
			int inChunkY = GetYCoordinate() % 16;
			int inChunkZ = GetZCoordinate() % 16;
			String daChunk = ("" + Integer.toString(inChunkX) + " " + Integer.toString(inChunkY) + " " + Integer.toString(inChunkZ) + " in " + Integer.toString(chunkX) + " " + Integer.toString(chunkY) + " " + Integer.toString(chunkZ)); 
			
			if(chunkEnabled){
				mc.fontRendererObj.drawString("C: " + daChunk + "", 2, 32, 0xffffff);
			}
			
		}
		}
	}
	//public static String coordFormat = "" + Integer.toString(GetXCoordinate()) + ", " + Integer.toString(GetYCoordinate()) + ", " + Integer.toString(GetZCoordinate()) + "";
	
	protected static Minecraft mc = Minecraft.getMinecraft();
	//String yawCalc = "" + (MathHelper.floor_double((double) (mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3);
	/** Get Direction we are facing N S E W */
	public static double getYaw(){
		return (double) (MathHelper.floor_double((double) (mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3);
	}
	public static String yawCalc(){
		double var1 = getYaw();
		String var2 = "";
		if(var1 == 0){
			var2 = "S";
		}
		else if(var1 == 1){
			var2 = "W";
		}
		else if(var1 == 2){
			var2 = "N";
		}
		else if(var1 == 3){
			var2 = "E";
		}
		return var2;
	}
	/** Get XP, Current to Next Level */
	public static String getXP(){
		return (String) "" +  String.valueOf((int) Math.ceil(mc.thePlayer.experience * mc.thePlayer.xpBarCap())) + "/" + mc.thePlayer.xpBarCap();
	}
	/** Get Biome */
	public static String getBiome(){
		return (String) "" + mc.thePlayer.worldObj.getBiomeGenForCoords(mc.thePlayer.getPosition()).biomeName;
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
	
	private static long mobSpawningStartTime = 13187;
	//mobs stop spawning at: 22813
	//mobs start to burn at: 23600
	private static long mobSpawningStopTime = 23600;
	/**
     * Calculates time
     * @return time if the Clock is enabled, otherwise "".
     */
    public static String CalculateMessageForInfoLine(String infoLineMessageUpToThisPoint)
    {
        if (Enabled)
        {
        	
            	//0 game time is 6am, so add 6000
                long time = (mc.theWorld.getWorldTime() + 6000) % 24000;
                
                long hours = time / 1000;
                long seconds = (long)((time % 1000) * (60.0/1000.0));

                if(IsNight())
        		{
                    String nighttimeClockString = EnumChatFormatting.GRAY + String.format("%02d", hours) + ":" + String.format("%02d", seconds);
                    return nighttimeClockString;
        		}
                else
        		{
                    String daytimeClockString = EnumChatFormatting.YELLOW + String.format("%02d", hours) + ":" + String.format("%02d", seconds);
                    return daytimeClockString;
        		}
        	
        	
        	
        }

        return "";
    }
    
    public static boolean IsNight()
    {
    	long time = (mc.theWorld.getWorldTime()) % 24000;
    	return time >= mobSpawningStartTime && time < mobSpawningStopTime;
    }
}
