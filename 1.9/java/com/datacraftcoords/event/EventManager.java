package com.datacraftcoords.event;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldType;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;









import net.minecraftforge.fml.relauncher.Side;












//import com.datacraftcoords.Configs;
import com.datacraftcoords.Do;
import com.datacraftcoords.ForgeMod;
import com.datacraftcoords.GuiInventoryOverride;
import com.datacraftcoords.GuiRepairOverride;
import com.datacraftcoords.MyMessage;
import com.datacraftcoords.config.ConfigHandler;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

@SuppressWarnings("unused")
public class EventManager {
	
	//public static String daConfig;
	/** Enables/Disables this Mod */
	public static boolean Enabled;
	public static boolean chunkEnabled;
	public static boolean LightEnabled;
	public static boolean alignLeft = false;
	public static boolean alignRight = false;
	public static boolean alignTop = true;
	public static boolean FirstRun;
	public int deathCounter;
    /**
     * Toggles this Mod on or off
     * @return The state the Mod was changed to
     */
    public static void ToggleEnabled()
    {
    		Enabled = !Enabled;
    		ConfigHandler.configFile.save();
    	        	
    }

	private int width;
	private int height;
	
	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent.Post e){
		if (FirstRun){
	    	 EntityPlayer player = (EntityPlayer) Minecraft.getMinecraft().thePlayer;
	    	 Do.Say(player, "Single Player Commands"); 
	    	 Do.Say(player, "/td -- Toggles Rain");
	    	 Do.Say(player, "/gm # -- Sets Gamemode(0=Survival, 1=Creative, 3=Spectator)");
	    	 Do.Say(player, "/day -- Sets time to day");
	    	 Do.Say(player, "/night -- Sets time to night");
	    	 Do.Say(player, "/test -- Do not use for testing only.");
	    	 FirstRun = false;
	    	 ConfigHandler.saveConfig();
	     }
		/** Checks if Enabled, if it is Enabled process code. */
		if(EventManager.Enabled)
        {
			/** Checks if experience bar is displayed or not. If it is start code. */
		if(e.getType() == RenderGameOverlayEvent.ElementType.ALL){//if(e.type == ElementType.JUMPBAR || e.type == ElementType.EXPERIENCE){
			//String DefaultChatStringFormat = "[{x}, {y}, {z}]";
			
			ScaledResolution res = new ScaledResolution(mc);//, mc.displayWidth, mc.displayHeight);
			FontRenderer fontRenderer = mc.fontRendererObj;
			this.width = res.getScaledWidth();
			this.height = res.getScaledHeight();
			
			int varX = 2;
			int varY = 2;
			if (alignLeft) // On alignRight varX will stay the same, and varY will change.
			{
				renderOverlayLeft.renderItLeft(EventManager.Enabled,chunkEnabled,LightEnabled);
			}
			else if (alignRight) // On alignRight varX will stay the same, and varY will change.
			{
				renderOverlayRight.renderItRight(EventManager.Enabled,chunkEnabled,LightEnabled);
			}
			else if (alignTop) // On alignTop varY will stay the same, and varX will change.
			{
				renderOverlayTop.renderItTop(EventManager.Enabled,chunkEnabled,LightEnabled);
			}
			
			DistanceMeasurer.RenderOntoHUD();
			/**
			// Gets Biome, and compares it to nether. Then alters the coords displayed based on results /
			String varBiome = getBiome();
			if(!varBiome.equals("Hell")){
				/// We are NOT in the Nether so display Overworld, and Nether Coords. /
				String coordinateString = "{x}, {y}, {z}";
	        	coordinateString = coordinateString.replace("{x}", Integer.toString(GetXCoordinate()));
	        	coordinateString = coordinateString.replace("{y}", Integer.toString(GetYCoordinate()));
	        	coordinateString = coordinateString.replace("{z}", Integer.toString(GetZCoordinate()));
	        	/// Display Overworld coords /
	        	mc.fontRendererObj.drawStringWithShadow(EnumChatFormatting.YELLOW + "O: " + coordinateString + " " + EnumChatFormatting.RED + yawCalc() + EnumChatFormatting.WHITE + " T:" + CalculateMessageForInfoLine(null), varX, 2, 0xffffff);
	        	
	        	String coordinateString2 = "{x}, {y}, {z}";
	        	coordinateString2 = coordinateString2.replace("{x}", Integer.toString(GetXCoordinate() / 8));
	        	coordinateString2 = coordinateString2.replace("{y}", Integer.toString(GetYCoordinate()));
	        	coordinateString2 = coordinateString2.replace("{z}", Integer.toString(GetZCoordinate() / 8));
	        	/// Display Nether Coords, Overworld Dived by 8/
	        	mc.fontRendererObj.drawStringWithShadow(EnumChatFormatting.LIGHT_PURPLE + "N: " + coordinateString2 + EnumChatFormatting.GREEN + " XP: " + getXP(), varX, 12, 0xffffff);
	        	//System.out.println("Biome " + varBiome + " - coord1 " + coordinateString + " - coord2 " + coordinateString2);
	        	}
			else if(varBiome.equals("Hell")){
				/// We ARE in the Nether so display Nether, and Overworld Coords. /
				String coordinateString3 = "{x}, {y}, {z}";
	        	coordinateString3 = coordinateString3.replace("{x}", Integer.toString(GetXCoordinate()));
	        	coordinateString3 = coordinateString3.replace("{y}", Integer.toString(GetYCoordinate()));
	        	coordinateString3 = coordinateString3.replace("{z}", Integer.toString(GetZCoordinate()));
	        	/// Display Nether Coords /
	        	mc.fontRendererObj.drawStringWithShadow(EnumChatFormatting.LIGHT_PURPLE + "N: " + coordinateString3 + " " + EnumChatFormatting.RED + yawCalc() + EnumChatFormatting.WHITE + " T:" + CalculateMessageForInfoLine(null), varX, 2, 0xffffff);
	        	
	        	String coordinateString4 = "{x}, {y}, {z}";
	        	coordinateString4 = coordinateString4.replace("{x}", Integer.toString((GetXCoordinate() * 8)));
	        	coordinateString4 = coordinateString4.replace("{y}", Integer.toString(GetYCoordinate()));
	        	coordinateString4 = coordinateString4.replace("{z}", Integer.toString((GetZCoordinate() * 8)));
	        	/// Display Overworld Coords, Nether Multiplied by 8 /
	        	mc.fontRendererObj.drawStringWithShadow(EnumChatFormatting.YELLOW + "O: " + coordinateString4 + EnumChatFormatting.GREEN + " XP: " + getXP(), 2, 12, 0xffffff);
	        	//System.out.println("Biome " + varBiome + " - coord3 " + coordinateString3 + " - coord4 " + coordinateString4);
			}
			else if(varBiome.equals("End")){
				/// We ARE in the End so display End, and Overworld Coords. /
				String coordinateString3 = "{x}, {y}, {z}";
	        	coordinateString3 = coordinateString3.replace("{x}", Integer.toString(GetXCoordinate()));
	        	coordinateString3 = coordinateString3.replace("{y}", Integer.toString(GetYCoordinate()));
	        	coordinateString3 = coordinateString3.replace("{z}", Integer.toString(GetZCoordinate()));
	        	/// Display Nether Coords /
	        	mc.fontRendererObj.drawStringWithShadow(EnumChatFormatting.LIGHT_PURPLE + "E: " + coordinateString3 + " " + EnumChatFormatting.RED + yawCalc() + EnumChatFormatting.WHITE + " T:" + CalculateMessageForInfoLine(null), varX, 2, 0xffffff);
	        	mc.fontRendererObj.drawStringWithShadow("" + EnumChatFormatting.GREEN + " XP: " + getXP(), 2, 12, 0xffffff);
			}
			
        	//Minecraft.getMinecraft().fontRendererObj.drawString("" + coordFormat + "", 2, 2, 0xffffff);
			
			 //* Display Biome 
			
			mc.fontRendererObj.drawStringWithShadow("B: " + getBiome() + "", varX, 22, 0xffffff);
			
			//	Get Chunk coordinates 
			
			int chunkX = GetXCoordinate() / 16;
			int chunkY = GetYCoordinate() / 16;
			int chunkZ = GetZCoordinate() / 16;
			int inChunkX = GetXCoordinate() % 16;
			int inChunkY = GetYCoordinate() % 16;
			int inChunkZ = GetZCoordinate() % 16;
			String daChunk = ("" + Integer.toString(inChunkX) + " " + Integer.toString(inChunkY) + " " + Integer.toString(inChunkZ) + " in " + Integer.toString(chunkX) + " " + Integer.toString(chunkY) + " " + Integer.toString(chunkZ)); 
			
			if(chunkEnabled){
				mc.fontRendererObj.drawStringWithShadow("C: " + daChunk + "", varX, 32, 0xffffff);
			}

			// Get Light Level
			 
			if (LightEnabled){
				BlockPos blockpos = new BlockPos(mc.getRenderViewEntity().posX, mc.getRenderViewEntity().getEntityBoundingBox().minY, mc.getRenderViewEntity().posZ);
                //arraylist.add("Light: " + chunk.getLightSubtracted(blockpos, 0) + " (" + chunk.getLightFor(EnumSkyBlock.SKY, blockpos) + " sky, " + chunk.getLightFor(EnumSkyBlock.BLOCK, blockpos) + " block)");

				Chunk chunk = mc.theWorld.getChunkFromBlockCoords(blockpos);
				String daLight = "L: " + chunk.getLightSubtracted(blockpos, 0);
				daLight = daLight + " (S: " + chunk.getLightFor(EnumSkyBlock.SKY, blockpos) ;
				int blockLight = chunk.getLightFor(EnumSkyBlock.BLOCK, blockpos);
				if (blockLight < 8){
					daLight = daLight + ", " + EnumChatFormatting.RED + "B: " + blockLight + EnumChatFormatting.WHITE + ")";
				}
				else {
					daLight = daLight + ", B: " + blockLight + ")";
				}
							
				if(chunkEnabled){
					mc.fontRendererObj.drawStringWithShadow("" + daLight, varX, 42, 0xffffff);
				}
				else {
					mc.fontRendererObj.drawStringWithShadow("" + daLight, varX, 32, 0xffffff);
				}
			}
			
			if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && mc.objectMouseOver.getBlockPos() != null)
            {
                BlockPos blockpos1 = mc.objectMouseOver.getBlockPos();
                String daLook = String.format("L @: %d %d %d", new Object[] {Integer.valueOf(blockpos1.getX()), Integer.valueOf(blockpos1.getY()), Integer.valueOf(blockpos1.getZ())});
                mc.fontRendererObj.drawStringWithShadow("" + daLook, varX, 62, 0xffffff);
            }
			
			// TODO test code
			//	Get and Display block name, and variant if applicable
			 
			String list = this.getDebugInfoRight();
			String delims = "[?]+";
			String[] tokens = list.split(delims);
			
			int i = 0;
			String daBlockType = tokens[0];
			String daBlockVar = "";
			
			if (1 < tokens.length){
				
				daBlockVar = tokens[1];
			}
			
			//daBlockVar = String.valueOf(list.size());
			
            mc.fontRendererObj.drawStringWithShadow("" + daBlockType + " " + daBlockVar, varX, 52, 0xffffff);
			*/
			
			
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
	public static String getBiome(){
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
        if (Enabled)
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
    @SubscribeEvent
    public void GuiOpenEvent(GuiOpenEvent event)
	{
    	//if(UseQuickPlaceSign && event.gui instanceof GuiEditSign && mc.thePlayer.isSneaking())
    	//{
    	//	event.setCanceled(true);
    	//}
    	if(true && event.getGui() instanceof GuiRepair)
    	{
    		event.setGui(new GuiRepairOverride(mc.thePlayer.inventory, mc.theWorld));
    	}
    	
    	/**if(true && event.gui instanceof GuiContainer)
    	{
    		event.gui = new GuiInventoryOverride(mc.thePlayer.inventoryContainer);
    	}*/
    	// Part of Death Markers
    	if (event.getGui() instanceof GuiGameOver) {
    		// TODO: Add Death Marker code
    		++this.deathCounter;
            if ((this.deathCounter & 1) == 0) {
                this.createDeathpoint((EntityPlayer)mc.thePlayer);
                Do.Trace("Deathpointer ..............................................");
            }
    	}
	}
    
    @SuppressWarnings({ "rawtypes", "static-access", "unchecked" })
	protected String getDebugInfoRight()
    {
    	ArrayList arraylist = Lists.newArrayList(new String[] {String.format("")});
    	String daBlockInfo = "";
    	if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK && mc.objectMouseOver.getBlockPos() != null)
		{
			BlockPos blockpos = mc.objectMouseOver.getBlockPos();
            IBlockState iblockstate = mc.theWorld.getBlockState(blockpos);
            
            if (this.mc.theWorld.getWorldType() != WorldType.DEBUG_WORLD)
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
    
    @SubscribeEvent
	public void preventDeath(LivingDeathEvent event) {
    	if(event.getEntityLiving() instanceof EntityPlayer){
	    	double DeadX = GetXCoordinate();
	    	double DeadY = GetYCoordinate();
	    	double DeadZ = GetZCoordinate();
	    	Do.Say(mc.thePlayer, "You died at X:" + TextFormatting.GREEN + DeadX + TextFormatting.WHITE + " Y:" + TextFormatting.GREEN + DeadY + TextFormatting.WHITE + " Z:" + TextFormatting.GREEN + DeadZ);
    	}
    	//Do.Trace("Entity Living " + event.entityLiving.getDisplayName());
    	//Do.Trace("The Player " + mc.thePlayer.getDisplayNameString());
    	
    }
    
    // TODO Implement Clipboard Copy
    /** Copy to clipboard 
    Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
    StringSelection testData;

    //  Add some test data
   testData = new StringSelection( datest );
   c.setContents(testData, testData);
    End copy clipboard */
    
    public void createDeathpoint(EntityPlayer p) {
        boolean disabled = false;
        if (InterfaceHandler.waypoints == null) {
            Do.Trace("waypoints = null .................................");
        	return;
        }
        for (Waypoint w : InterfaceHandler.waypoints.list) {
            if (w.type != 1) continue;
            if (!InterfaceHandler.getDeathpoints() || !InterfaceHandler.getOldDeathpoints()) {
            	InterfaceHandler.waypoints.list.remove(w);
                break;
            }
            //Do.Trace("!getDeathpoints" + !InterfaceHandler.getDeathpoints() + " ..................................................");
            //Do.Trace("!getOldDeathpoints" + !InterfaceHandler.getOldDeathpoints() + " ..................................................");
            disabled = false;//w.disabled;
            w.type = 0;
            w.name = "gui.deathpoint_old";
            break;
        }
        if (InterfaceHandler.getDeathpoints()) {
            Waypoint deathpoint = new Waypoint(InterfaceHandler.myFloor(p.posX), InterfaceHandler.myFloor(p.posY), InterfaceHandler.myFloor(p.posZ), "Latest Death", "D", 0, 1, InterfaceHandler.getDimension());
            deathpoint.disabled = disabled;
            InterfaceHandler.waypoints.list.add(deathpoint);
            Do.Trace("getDeathpoints ................................................");
            Do.Trace("" + InterfaceHandler.waypoints.list.toString());
        }
        try {
        	InterfaceHandler.saveWaypoints();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == Side.CLIENT && event.phase == TickEvent.Phase.START) {
        	if (true && (InterfaceHandler.getDeathpoints() || InterfaceHandler.getShowWaypoints() || InterfaceHandler.getShowIngameWaypoints())) {
        		InterfaceHandler.updateWaypoints();
            } else if (InterfaceHandler.waypoints != null) {
            	InterfaceHandler.waypoints = null;
            }
        }
    }
    
    @SuppressWarnings("static-access")
	@SubscribeEvent
    public void renderLast(RenderWorldLastEvent event) {
        if (InterfaceHandler.getShowIngameWaypoints() && InterfaceHandler.waypoints != null) {
        	//Do.Trace("Both True .....................................................");
            Entity entity = mc.getRenderViewEntity();
            double d3 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)event.getPartialTicks();
            double d4 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)event.getPartialTicks();
            double d5 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)event.getPartialTicks();
            Tessellator tessellator = Tessellator.getInstance();
            VertexBuffer vertexBuffer = tessellator.getBuffer();
            for (Waypoint w : InterfaceHandler.waypoints.list) {
                if (w.disabled || w.type == 1 && !InterfaceHandler.getDeathpoints()) continue;
                	if(w.getDim() == InterfaceHandler.getDimension()){
                		Do.Trace("Waypoint Name = " + Waypoint.getDim() + ".....................................");
                		this.renderIngameWaypoint(w, 12.0, d3, d4, d5, entity, vertexBuffer, tessellator);
                	}
            }
            RenderHelper.disableStandardItemLighting();
            GlStateManager.enableDepth();
            GlStateManager.depthMask((boolean)true);
        }else{
        	//Do.Trace(InterfaceHandler.getShowIngameWaypoints() + " = false ..................................................................");
        	
        }
    }

    protected void renderIngameWaypoint(Waypoint w, double radius, double d3, double d4, double d5, Entity entity, VertexBuffer vertexBuffer, Tessellator tessellator) {
        double distance;
        float offX = (float)w.x - (float)d3 + 0.5f;
        float offY = (float)w.y - (float)d4 + 1.0f;
        float offZ = (float)w.z - (float)d5 + 0.5f;
        w.lastDistance = distance = Math.sqrt(offX * offX + offY * offY + offZ * offZ);
        if (InterfaceHandler.waypointsDistance != 0.0f && distance > (double)InterfaceHandler.waypointsDistance || InterfaceHandler.waypointsDistanceMin != 0.0f && distance < (double)InterfaceHandler.waypointsDistanceMin) {
            return;
        }
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        FontRenderer fontrenderer = renderManager.getFontRenderer();
        if (fontrenderer == null) {
            return;
        }
        float f = 1.6f;
        float f1 = 0.016666668f * f;
        GlStateManager.pushMatrix();
        float textSize = 1.0f;
        String name = w.getName();
        String distanceText = "";
        boolean showDistance = false;
        float zoomer2 = 1.0f;
        if (InterfaceHandler.keepWaypointNames) {
            textSize = 1.6f;
        }
        if (distance > radius) {
            double maxDistance = (double)Minecraft.getMinecraft().gameSettings.renderDistanceChunks * 16.0;
            if (distance > maxDistance) {
                zoomer2 = (float)(maxDistance / radius);
                float zoomer = (float)(maxDistance / distance);
                offX *= zoomer;
                offY *= zoomer;
                offY += entity.getEyeHeight() * (1.0f - zoomer);
                offZ *= zoomer;
            } else {
                zoomer2 = (float)(distance / radius);
            }
            if (distance > 20.0) {
                textSize = 1.6f;
                if (InterfaceHandler.distance == 1) {
                    float cameraAngle;
                    float offset;
                    float Z = (float)(offZ == 0.0f ? 0.001 : (double)offZ);
                    float angle = (float)Math.toDegrees(Math.atan((- offX) / Z));
                    if (offZ < 0.0f) {
                        angle = offX < 0.0f ? (angle += 180.0f) : (angle -= 180.0f);
                    }
                    showDistance = (offset = MathHelper.wrapAngleTo180_float((float)(angle - (cameraAngle = MathHelper.wrapAngleTo180_float((float)entity.rotationYaw))))) > -20.0f && offset < 20.0f;
                } else if (InterfaceHandler.distance == 2) {
                    showDistance = true;
                }
                if (showDistance) {
                    distanceText = InterfaceHandler.simpleFormat.format(distance) + "m";
                    if (!InterfaceHandler.keepWaypointNames) {
                        name = "";
                    }
                } else {
                    name = "";
                }
            }
        }
        GlStateManager.translate((float)offX, (float)offY, (float)offZ);
        GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)(- renderManager.playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)renderManager.playerViewX, (float)1.0f, (float)0.0f, (float)0.0f);
        GlStateManager.scale((float)(- f1), (float)(- f1), (float)f1);
        GlStateManager.scale((float)zoomer2, (float)zoomer2, (float)1.0f);
        GlStateManager.disableLighting();
        GlStateManager.depthMask((boolean)false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.scale((float)2.0f, (float)2.0f, (float)2.0f);
        w.drawIconInWorld(vertexBuffer, tessellator, fontrenderer, name, distanceText, textSize, showDistance);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.popMatrix();
    }
}
