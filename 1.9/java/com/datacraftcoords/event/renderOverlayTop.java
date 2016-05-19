package com.datacraftcoords.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.Chunk;

import com.datacraftcoords.MyMessage;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
@SuppressWarnings("unused")
public class renderOverlayTop {
	
	//public static String daConfig;
		/** Enables/Disables this Mod */
		//public static boolean Enabled;
		//public static boolean chunkEnabled;
		//public static boolean LightEnabled;
		private static int width;
		private static int height;
		private static int varY;
		private static int varLen;
		private static String owCoord0;
		private static String owCoord1;
		private static String owCoord2;
		private static String owCoord3;
		private static String owCoord4;
		private static String owCoord5;
		private static String owCoord6;
		private static String owCoord7;
		private static String owCoord8;
		
		public static void renderItTop(boolean Enabled, boolean chunkEnabled, boolean LightEnabled) {
		/** Gets Biome, and compares it to nether. Then alters the coords displayed based on results */
		int varX = 2;
		// ScaledResolution
		ScaledResolution res = new ScaledResolution(mc);//, mc.displayWidth, mc.displayHeight);
		FontRenderer fontRenderer = mc.fontRendererObj;
		width = res.getScaledWidth();
		height = res.getScaledHeight();
		int fHeight = fontRenderer.FONT_HEIGHT;
        int sWidth = fontRenderer.getStringWidth("stringVar");
        int sWidth2 = res.getScaledWidth() - 2 - sWidth;
        
        //mc.fontRendererObj.drawStringWithShadow("Width=" + width + " Height=" + height, 2, 72, 0xffffff);
        
		String varBiome = getBiome();
		if(!varBiome.equals("Hell") && !varBiome.equals("The End")){
			/** We are NOT in the Nether so display Overworld, and Nether Coords. */
			String coordinateString = "{x}, {y}, {z}";
	    	coordinateString = coordinateString.replace("{x}", Integer.toString(GetXCoordinate()));
	    	coordinateString = coordinateString.replace("{y}", Integer.toString(GetYCoordinate()));
	    	coordinateString = coordinateString.replace("{z}", Integer.toString(GetZCoordinate()));
	    	/** Display Overworld coords */
	    	String owCoord1 = TextFormatting.YELLOW + "O: " + coordinateString + " " + TextFormatting.RED + yawCalc() + TextFormatting.WHITE + " T:" + CalculateMessageForInfoLine(null);
	    	mc.fontRendererObj.drawStringWithShadow(owCoord1, varX, 2, 0xffffff);
	    	
	    	String coordinateString2 = "{x}, {y}, {z}";
	    	coordinateString2 = coordinateString2.replace("{x}", Integer.toString(GetXCoordinate() / 8));
	    	coordinateString2 = coordinateString2.replace("{y}", Integer.toString(GetYCoordinate()));
	    	coordinateString2 = coordinateString2.replace("{z}", Integer.toString(GetZCoordinate() / 8));
	    	/** Display Nether Coords, Overworld Dived by 8*/
	    	String owCoord2 = TextFormatting.LIGHT_PURPLE + "N: " + coordinateString2 + TextFormatting.GREEN + " XP: " + getXP();
	    	sWidth = fontRenderer.getStringWidth(owCoord1);
	    	sWidth2 = fontRenderer.getStringWidth(owCoord2);
	    	varX = 4 + sWidth;
	    	varLen = varX + sWidth2;
	    	mc.fontRendererObj.drawStringWithShadow(owCoord2, varX, 2, 0xffffff); //138
	    	//System.out.println("Biome " + varBiome + " - coord1 " + coordinateString + " - coord2 " + coordinateString2);
	    	}
		else if(varBiome.equals("Hell")){
			/** We ARE in the Nether so display Nether, and Overworld Coords. */
			String coordinateString3 = "{x}, {y}, {z}";
	    	coordinateString3 = coordinateString3.replace("{x}", Integer.toString(GetXCoordinate()));
	    	coordinateString3 = coordinateString3.replace("{y}", Integer.toString(GetYCoordinate()));
	    	coordinateString3 = coordinateString3.replace("{z}", Integer.toString(GetZCoordinate()));
	    	/** Display Nether Coords */
	    	String owCoord1 = TextFormatting.LIGHT_PURPLE + "N: " + coordinateString3 + " " + TextFormatting.RED + yawCalc() + TextFormatting.WHITE + " T:" + CalculateMessageForInfoLine(null);
	    	mc.fontRendererObj.drawStringWithShadow(owCoord1, varX, 2, 0xffffff);
	    	
	    	String coordinateString4 = "{x}, {y}, {z}";
	    	coordinateString4 = coordinateString4.replace("{x}", Integer.toString((GetXCoordinate() * 8)));
	    	coordinateString4 = coordinateString4.replace("{y}", Integer.toString(GetYCoordinate()));
	    	coordinateString4 = coordinateString4.replace("{z}", Integer.toString((GetZCoordinate() * 8)));
	    	/** Display Overworld Coords, Nether Multiplied by 8 */
	    	String owCoord2 = TextFormatting.YELLOW + "O: " + coordinateString4 + TextFormatting.GREEN + " XP: " + getXP();
	    	sWidth = fontRenderer.getStringWidth(owCoord1);
	    	sWidth2 = fontRenderer.getStringWidth(owCoord2);
	    	varX = 4 + sWidth;
	    	varLen = varX + sWidth2;
	    	mc.fontRendererObj.drawStringWithShadow(owCoord2, varX, 2, 0xffffff);
	    	//System.out.println("Biome " + varBiome + " - coord3 " + coordinateString3 + " - coord4 " + coordinateString4);
		}
		else if(varBiome.equals("The End")){
			/** We ARE in the End so display End, and Overworld Coords. */
			String coordinateString3 = "{x}, {y}, {z}";
	    	coordinateString3 = coordinateString3.replace("{x}", Integer.toString(GetXCoordinate()));
	    	coordinateString3 = coordinateString3.replace("{y}", Integer.toString(GetYCoordinate()));
	    	coordinateString3 = coordinateString3.replace("{z}", Integer.toString(GetZCoordinate()));
	    	/** Display Nether Coords */
	    	String owCoord1 = TextFormatting.LIGHT_PURPLE + "E: " + coordinateString3 + " " + TextFormatting.RED + yawCalc() + TextFormatting.WHITE + " T:" + CalculateMessageForInfoLine(null);
	    	mc.fontRendererObj.drawStringWithShadow(owCoord1, varX, 2, 0xffffff);
	    	String owCoord2 = "" + TextFormatting.GREEN + " XP: " + getXP();
	    	sWidth = fontRenderer.getStringWidth(owCoord1);
	    	sWidth2 = fontRenderer.getStringWidth(owCoord2);
	    	varX = 4 + sWidth;
	    	varLen = varX + sWidth2;
	    	mc.fontRendererObj.drawStringWithShadow(owCoord2, varX, 2, 0xffffff);
	    	
	    	
		}
		
		//Minecraft.getMinecraft().fontRendererObj.drawString("" + coordFormat + "", 2, 2, 0xffffff);
		/** 
		 * 
		 * Display Biome 
		 * 
		 */
    	String owCoord3 = "B: " + getBiome() + "";
    	sWidth = fontRenderer.getStringWidth(owCoord3);
    	varX = 4 + varLen;
    	//mc.fontRendererObj.drawStringWithShadow("Varlen before reset=" + (varX + sWidth), 2, 82, 0xffffff);
    	varLen = 0;
		mc.fontRendererObj.drawStringWithShadow(owCoord3, varX, 2, 0xffffff);
		
		/** 
		 * 
		 *	Get Chunk coordinates 
		 * 
		 */
		int chunkX = GetXCoordinate() / 16;
		int chunkY = GetYCoordinate() / 16;
		int chunkZ = GetZCoordinate() / 16;
		int inChunkX = GetXCoordinate() % 16;
		int inChunkY = GetYCoordinate() % 16;
		int inChunkZ = GetZCoordinate() % 16;
		String daChunk = ("" + Integer.toString(inChunkX) + " " + Integer.toString(inChunkY) + " " + Integer.toString(inChunkZ) + " in " + Integer.toString(chunkX) + " " + Integer.toString(chunkY) + " " + Integer.toString(chunkZ)); 
		
		if(chunkEnabled){
			/**
			*  Check if enabled by Server Plugin. TODO:
			*/
			if(MyMessage.plgCIenabled){
				String owCoord4 = "C: " + daChunk + "";
		    	sWidth = fontRenderer.getStringWidth(owCoord4);
		    	varX = 2 + varLen;
		    	varLen = varX + sWidth;
				mc.fontRendererObj.drawStringWithShadow(owCoord4, varX, 12, 0xffffff);
			}
		}

		/** 
		 * 
		 * Get Light Level
		 * 
		 */
		if (LightEnabled){
			/**
			 *  Check if Enabled by Server Plugin TODO:
			 */
			if(MyMessage.plgLLenabled){
				BlockPos blockpos = new BlockPos(mc.getRenderViewEntity().posX, mc.getRenderViewEntity().getEntityBoundingBox().minY, mc.getRenderViewEntity().posZ);
		        //arraylist.add("Light: " + chunk.getLightSubtracted(blockpos, 0) + " (" + chunk.getLightFor(EnumSkyBlock.SKY, blockpos) + " sky, " + chunk.getLightFor(EnumSkyBlock.BLOCK, blockpos) + " block)");
	
				Chunk chunk = mc.theWorld.getChunkFromBlockCoords(blockpos);
				String daLight = "L: " + chunk.getLightSubtracted(blockpos, 0);
				daLight = daLight + " (S: " + chunk.getLightFor(EnumSkyBlock.SKY, blockpos) ;
				int blockLight = chunk.getLightFor(EnumSkyBlock.BLOCK, blockpos);
				if (blockLight < 8){
					daLight = daLight + ", " + TextFormatting.RED + "B: " + blockLight + TextFormatting.WHITE + ")";
				}
				else {
					daLight = daLight + ", B: " + blockLight + ")";
				}
							
				if(chunkEnabled){
					String owCoord5 = "" + daLight;
			    	sWidth = fontRenderer.getStringWidth(owCoord5);
			    	varX = 4 + varLen;
			    	varLen = varX + sWidth;
					mc.fontRendererObj.drawStringWithShadow(owCoord5, varX, 12, 0xffffff);
				}
				else {
					String owCoord6 = "" + daLight;
			    	sWidth = fontRenderer.getStringWidth(owCoord6);
			    	varX = 4 + varLen;
			    	varLen = varX + sWidth;
					mc.fontRendererObj.drawStringWithShadow(owCoord6, varX, 12, 0xffffff);
				}
			}
		}
			
			if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK && mc.objectMouseOver.getBlockPos() != null)
		    {
		        BlockPos blockpos1 = mc.objectMouseOver.getBlockPos();
		        String daLook = String.format("L @: %d %d %d", new Object[] {Integer.valueOf(blockpos1.getX()), Integer.valueOf(blockpos1.getY()), Integer.valueOf(blockpos1.getZ())});
		        String owCoord0 = "" + daLook;
		    	sWidth = fontRenderer.getStringWidth(owCoord0);
		    	varX = 4 + varLen;
		    	varLen = varX + sWidth;
		        mc.fontRendererObj.drawStringWithShadow(owCoord0, varX, 12, 0xffffff);
		    }
		
		// TODO test code
		/** 
		 * 
		 *	Get and Display block name, and variant if applicable
		 * 
		 */
		String list = getDebugInfoRight();
		String delims = "[?]+";
		String[] tokens = list.split(delims);
		
		int i = 0;
		String daBlockType = tokens[0];
		String daBlockVar = "";
		
		if (1 < tokens.length){
			
			daBlockVar = tokens[1];
		}
		
		//daBlockVar = String.valueOf(list.size());
		String owCoord7 = "" + daBlockType + " " + daBlockVar;
    	sWidth = fontRenderer.getStringWidth(owCoord7);
    	varX = 4 + varLen;
    	varLen = varX + sWidth;
    	varY = 12;
    	if (varLen > width){
    		varX = 2;
    		varY = 22;
    	}
	    mc.fontRendererObj.drawStringWithShadow(owCoord7, varX, varY, 0xffffff);
	    //mc.fontRendererObj.drawStringWithShadow("Varlen after reset=" + varLen, 2, 92, 0xffffff);
	}
	    
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
				var2 = "S +Z";
			}
			else if(var1 == 1){
				var2 = "W -X";
			}
			else if(var1 == 2){
				var2 = "N -Z";
			}
			else if(var1 == 3){
				var2 = "E +X";
			}
			return var2;
		}
		/** Get XP, Current to Next Level */
		public static String getXP(){
			return (String) "" +  String.valueOf((int) Math.ceil(mc.thePlayer.experience * mc.thePlayer.xpBarCap())) + "/" + mc.thePlayer.xpBarCap();
		}
		/** Get Biome */
		public static String getBiome()
		{
			return (String) "" + mc.thePlayer.worldObj.getBiomeGenForCoords(mc.thePlayer.getPosition()).getBiomeName();
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
	        if (true)
	        {
	        	
	            	//0 game time is 6am, so add 6000
	                long time = (mc.theWorld.getWorldTime() + 6000) % 24000;
	                
	                long hours = time / 1000;
	                long seconds = (long)((time % 1000) * (60.0/1000.0));

	                if(IsNight())
	        		{
	                    String nighttimeClockString = TextFormatting.GRAY + String.format("%02d", hours) + ":" + String.format("%02d", seconds);
	                    return nighttimeClockString;
	        		}
	                else
	        		{
	                    String daytimeClockString = TextFormatting.YELLOW + String.format("%02d", hours) + ":" + String.format("%02d", seconds);
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

	    @SuppressWarnings({ "rawtypes", "unchecked" })
		public static String getDebugInfoRight()
	    {
	    	ArrayList arraylist = Lists.newArrayList(new String[] {String.format("")});
	    	String daBlockInfo = "";
	    	//MovingObjectPosition objectMouseOver = mc.thePlayer.rayTrace(300, 1.0f);
	    	if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK && mc.objectMouseOver.getBlockPos() != null)
			{
	    		//double blockX = objectMouseOver.hitVec.xCoord;
                //double blockY = objectMouseOver.hitVec.yCoord;
                //double blockZ = objectMouseOver.hitVec.zCoord;
	    		BlockPos blockpos = mc.objectMouseOver.getBlockPos();
				
	            IBlockState iblockstate = mc.theWorld.getBlockState(blockpos);
	            
	            if (mc.theWorld.getWorldType() != WorldType.DEBUG_WORLD)
	            {
	                iblockstate = iblockstate.getBlock().getActualState(iblockstate, mc.theWorld, blockpos);
	            }
	            Entry entry;
	            String s;
	            //String datest = String.valueOf(Block.blockRegistry.getNameForObject(iblockstate.getBlock()));
	            arraylist.add(String.valueOf(Block.blockRegistry.getNameForObject(iblockstate.getBlock())));
	            
	            
	            for (Iterator iterator = iblockstate.getProperties().entrySet().iterator(); iterator.hasNext(); arraylist.add(((IProperty)entry.getKey()).getName() + ": " + s))
	            {
	                entry = (Entry)iterator.next();
	                s = ((Comparable)entry.getValue()).toString();

	                if (entry.getValue() == Boolean.TRUE)
	                {
	                    s = TextFormatting.GREEN + s;
	                }
	                else if (entry.getValue() == Boolean.FALSE)
	                {
	                    s = TextFormatting.RED + s;
	                }
	            }
	            
	            List list = arraylist;
	            String s1 = "";//(String)list.get(0);
	            String s11 = (String)list.get(1);
	            String daBlockType = "";
	            String daBlockVar = "";

	            for (int i = 0; i < list.size(); ++i)
	            {
	                String s2 = (String)list.get(i);

	                if (!Strings.isNullOrEmpty(s2))
	                {
	                	if(s2.contains("minecraft")){
	                		daBlockType = s2.replace("minecraft:", "");
	                	}
	                	else if(s2.contains("variant")){
	                		daBlockVar = s2.replace("variant:", "");
	                		daBlockVar = daBlockVar.replace(" ", "");
	                	}
	                	
	                	 //s1 =  s1 + s2;
	                }
	            }
	            if(daBlockVar.equals(daBlockType)){
	        		daBlockVar = "";
	        		daBlockType = "mc: " + daBlockType;
	        	}
	        	else if(!daBlockVar.equals(daBlockType)  && !daBlockVar.equals("")){
	        		daBlockVar = "v: " + daBlockVar;
	        		daBlockType = "mc: " + daBlockType;
	        	}
	        	else {
	        		daBlockType = "mc: " + daBlockType;
	        	}
	            
	            daBlockInfo = daBlockType;
	            daBlockInfo = daBlockInfo + "?" + daBlockVar;
	            
			}    
	    	
	    	return daBlockInfo;
	    }
	}


