package com.datacraftcoords.event;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;








import com.datacraftcoords.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.Optional.Interface;


@SuppressWarnings("unused")

public class InterfaceHandler {

	public static final ResourceLocation guiTextures;
	public static final int[] COLORS;
	public static final Color radarPlayers = new Color(255, 255, 255);
	public static boolean keepWaypointNames = true;
	public static float waypointsScale = 1.0f;
	
	private static boolean showWaypoints = true;
    private static boolean deathpoints = true;
    private static boolean oldDeathpoints = false;
    public static int serverSettings;
    public static WaypointSet waypoints = null;
    public static File optionsFile;
    public static File waypointsFile;
    private static boolean showIngameWaypoints = true;
    public static boolean compassOverWaypoints = false;
    public static float waypointsDistance = 0.0f;
    public static float waypointsDistanceMin = 0.0f;
    private static String worldID = null;
    public static String customWorldID = null;
    public static final DecimalFormat simpleFormat = new DecimalFormat("0.0");
    public static int distance = 1;
    public static final String[] ENCHANT_COLORS;
    public static final String[] ENCHANT_COLOR_NAMES;
    
    //public static ArrayList<Interface> list = new ArrayList<Interface>();
    
    
	
	public static HashMap<String, WaypointWorld> waypointMap = new HashMap<String, WaypointWorld>();
    
    
	static {
        guiTextures = new ResourceLocation("joelgodofwars.simple.coordinate.mod", "gui/guis.png"); //"joelgodofwars.simple.coordinate.mod",
        optionsFile = new File("./config/jscm_settings.txt");
        waypointsFile = new File("./config/jscm_waypoints.txt");
        COLORS = new int[]{new Color(0, 0, 0, 255).hashCode(), new Color(0, 0, 170, 255).hashCode(), new Color(0, 170, 0, 255).hashCode(), new Color(0, 170, 170, 255).hashCode(), new Color(170, 0, 0, 255).hashCode(), new Color(170, 0, 170, 255).hashCode(), new Color(255, 170, 0, 255).hashCode(), new Color(170, 170, 170, 255).hashCode(), new Color(85, 85, 85, 255).hashCode(), new Color(85, 85, 255, 255).hashCode(), new Color(85, 255, 85, 255).hashCode(), new Color(85, 255, 255, 255).hashCode(), new Color(255, 0, 0, 255).hashCode(), new Color(255, 85, 255, 255).hashCode(), new Color(255, 255, 85, 255).hashCode(), new Color(255, 255, 255, 255).hashCode()};
        ENCHANT_COLORS = new String[]{        "0",          "1",             "2",               "3",               "4",           "5",             "6",        "7",         "8",            "9",        "a",        "b",         "c",       "d",           "e",         "f"};
        ENCHANT_COLOR_NAMES = new String[]{"gui.black", "gui.dark_blue", "gui.dark_green", "gui.dark_aqua", "gui.dark_red", "gui.dark_purple", "gui.gold", "gui.gray", "gui.dark_gray", "gui.blue", "gui.green", "gui.aqua", "gui.red", "gui.purple", "gui.yellow", "gui.white"};
    }
	
	public static boolean getDeathpoints() {
        return deathpoints;// && !deathpointsDisabled();
    }
	
	public static boolean deathpointsDisabled() {
        return (serverSettings & 2097152) == 0;
    }
	
	public static boolean getOldDeathpoints() {
        return oldDeathpoints;
    }
	
	public static int myFloor(double d) {
        if (d < 0.0) {
            d -= 1.0;
        }
        return (int)d;
    }
	
	public static void saveWaypoints() throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(waypointsFile));
        if (!waypointMap.isEmpty()) {
            Set<Map.Entry<String, WaypointWorld>> keyMap = waypointMap.entrySet();
            for (Map.Entry<String, WaypointWorld> entry : keyMap) {
                String name;
                int i;
                String worldID = entry.getKey();
                WaypointWorld world = entry.getValue();
                Object[] keys = world.sets.keySet().toArray();
                if (keys.length > 1) {
                    writer.print("world:" + worldID + ":" + world.current);
                    for (i = 0; i < keys.length; ++i) {
                        name = (String)keys[i];
                        if (name.equals(world.current)) continue;
                        writer.print(":" + (String)keys[i]);
                    }
                    writer.println("");
                }
                for (i = 0; i < keys.length; ++i) {
                    name = (String)keys[i];
                    WaypointSet set = world.sets.get(name);
                    if (set == null) continue;
                    for (Waypoint w : set.list) {
                        writer.println("waypoint:" + worldID + ":" + w.name.replaceAll(":", "\u00a7\u00a7") + ":" + w.symbol.replaceAll(":", "\u00a7\u00a7") + ":" + w.x + ":" + w.y + ":" + w.z + ":" + w.color + ":" + w.disabled + ":" + w.type + ":" + name + ":" + w.rotation + ":" + w.yaw);
                    }
                }
            }
        }
        writer.close();
    }
	public static void loadSettings() throws IOException {
		String s;
		if (!optionsFile.exists()) {
            saveSettings();
            return;
        }
		BufferedReader reader = new BufferedReader(new FileReader(optionsFile));
        boolean saveWaypoints = false;
        block2 : while ((s = reader.readLine()) != null) {
            String[] args = s.split(":");
            try {
            	if (args[0].equalsIgnoreCase("showWaypoints")) {
            		InterfaceHandler.showWaypoints = args[1].equals("true");
                    continue;
                }
            	if (args[0].equalsIgnoreCase("deathpoints")) {
                    InterfaceHandler.deathpoints = args[1].equals("true");
                    continue;
                }
                if (args[0].equalsIgnoreCase("oldDeathpoints")) {
                    InterfaceHandler.oldDeathpoints = args[1].equals("true");
                    continue;
                }
                if (args[0].equalsIgnoreCase("showIngameWaypoints")) {
                	InterfaceHandler.showIngameWaypoints = args[1].equals("true");
                    continue;
                }
                if (args[0].equalsIgnoreCase("waypointsScale")) {
                    InterfaceHandler.waypointsScale = Float.valueOf(args[1]).floatValue();
                    continue;
                }
                if (args[0].equalsIgnoreCase("compassOverWaypoints")) {
                	InterfaceHandler.compassOverWaypoints = args[1].equals("true");
                    continue;
                }
                if (args[0].equalsIgnoreCase("keepWaypointNames")) {
                    InterfaceHandler.keepWaypointNames = args[1].equals("true");
                    continue;
                }
                if (args[0].equalsIgnoreCase("waypointsDistance")) {
                	InterfaceHandler.waypointsDistance = Float.valueOf(args[1]).floatValue();
                    continue;
                }
                if (args[0].equalsIgnoreCase("waypointsDistanceMin")) {
                	InterfaceHandler.waypointsDistanceMin = Float.valueOf(args[1]).floatValue();
                    continue;
                }
                if (args[0].equalsIgnoreCase("distance")) {
                	InterfaceHandler.distance = Integer.parseInt(args[1]);
                    continue;
                }
                if (!InterfaceHandler.checkWaypointsLine(args)) continue;
                saveWaypoints = true;
            }
            catch (Exception e) {
                System.out.println("Skipping setting:" + args[0]);
            }
            }
        reader.close();
	
        InterfaceHandler.loadWaypoints(waypointsFile);
    if (saveWaypoints) {
        saveWaypoints();
        InterfaceHandler.saveSettings();
    }
	}
	
	public static boolean checkWaypointsLine(String[] args) {
        if (args[0].equalsIgnoreCase("world")) {
            if (!args[1].contains("_")) {
                args[1] = args[1] + "_null";
            }
            addWorld(args[1]);
            WaypointWorld map = waypointMap.get(args[1]);
            map.current = args[2];
            for (int i = 2; i < args.length; ++i) {
                if (map.sets.get(args[i]) != null) continue;
                map.sets.put(args[i], new WaypointSet(map));
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("waypoint")) {
            if (!args[1].contains("_")) {
                args[1] = args[1] + "_null";
            }
            addWorld(args[1]);
            String setName = "gui.default";
            if (args.length > 10) {
                setName = args[10];
            }
            WaypointWorld map = waypointMap.get(args[1]);
            WaypointSet waypoints = map.sets.get(setName);
            if (waypoints == null) {
                waypoints = new WaypointSet(map);
                map.sets.put(setName, waypoints);
            }
            Waypoint loadWaypoint = new Waypoint(Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]), args[2].replaceAll("\u00a7\u00a7", ":"), args[3].replaceAll("\u00a7\u00a7", ":"), Integer.parseInt(args[7]), Waypoint.getDim());
            if (args.length > 8) {
                loadWaypoint.disabled = args[8].equals("true");
            }
            if (args.length > 9) {
                loadWaypoint.type = Integer.parseInt(args[9]);
            }
            if (args.length > 11) {
                loadWaypoint.rotation = args[11].equals("true");
            }
            if (args.length > 12) {
                loadWaypoint.yaw = Integer.parseInt(args[12]);
            }
            waypoints.list.add(loadWaypoint);
            return true;
        }
        return false;
    }
	public static void addWorld(String id) {
        if (waypointMap.get(id) == null) {
            waypointMap.put(id, new WaypointWorld());
        }
    }
	public static boolean loadWaypoints(File file) throws IOException {
        String s;
        if (!file.exists()) {
            return false;
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));
        while ((s = reader.readLine()) != null) {
            String[] args = s.split(":");
            try {
            	InterfaceHandler.checkWaypointsLine(args);
            }
            catch (Exception e) {
                System.out.println("Skipping setting:" + args[0]);
            }
        }
        reader.close();
        return true;
    }
	public static void saveSettings() throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(optionsFile));
        writer.println("distance:" + InterfaceHandler.distance);
        writer.println("showWaypoints:" + InterfaceHandler.showWaypoints);
        writer.println("showIngameWaypoints:" + InterfaceHandler.showIngameWaypoints);

        writer.println("deathpoints:" + InterfaceHandler.deathpoints);
        writer.println("oldDeathpoints:" + InterfaceHandler.oldDeathpoints);

        writer.println("waypointsScale:" + InterfaceHandler.waypointsScale);

        writer.println("dotsScale:" + InterfaceHandler.waypointsScale);
        writer.println("compassOverWaypoints:" + InterfaceHandler.compassOverWaypoints);

        writer.println("keepWaypointNames:" + InterfaceHandler.keepWaypointNames);
        writer.println("waypointsDistance:" + InterfaceHandler.waypointsDistance);
        writer.println("waypointsDistanceMin:" + InterfaceHandler.waypointsDistanceMin);

        //for (Interface l : InterfaceHandler.list) {
        //    writer.println("interface:" + l.iname + ":" + l.actualx + ":" + l.actualy + ":" + l.centered + ":" + l.flipped + ":" + l.fromRight);
        //}
       
        //for (int i = 0; i < ControlsHandler.toAdd.length; ++i) {
        //    KeyBinding kb = ControlsHandler.toAdd[i];
        //    writer.println("key_" + kb.func_151464_g() + ":" + kb.func_151463_i());
       // }
        writer.close();
        InterfaceHandler.saveWaypoints();
    }
	public static void updateWaypoints() {
        worldID = "" + Waypoint.getDim();
        addWorld(worldID);
        waypoints = getWaypoints();
    }
	private static String getWorld() {
        if (mc.getIntegratedServer() != null) {
            return mc.getIntegratedServer().getWorldName() + "_" + mc.thePlayer.dimension;
        }
        String serverIP = mc.getCurrentServerData().serverIP;
        if (serverIP.contains(":")) {
            serverIP = serverIP.substring(0, serverIP.indexOf(":"));
        }
        return "Multiplayer_" + serverIP.replaceAll(":", "\u00a7") + "_" + mc.thePlayer.dimension;
    }
	private static WaypointSet getWaypoints() {
        if (customWorldID == null) {
            return waypointMap.get(worldID).getCurrentSet();
        }
        return waypointMap.get(customWorldID).getCurrentSet();
    }
	public static String getCurrentWorldID() {
        if (customWorldID == null) {
            return worldID;
        }
        return customWorldID;
    }
	
	public static boolean getShowWaypoints() {
        return InterfaceHandler.showWaypoints && !InterfaceHandler.showWaypointsDisabled();
    }
	
	public static boolean showWaypointsDisabled() {
        return (serverSettings & 65536) != 65536;
    }
	
	public static boolean getShowIngameWaypoints() {
        return true;
    }
	static Minecraft mc = Minecraft.getMinecraft();
	
	public static int getDimension(){
		String varBiome = mc.thePlayer.worldObj.getBiomeGenForCoords(mc.thePlayer.getPosition()).getBiomeName();
		if(!varBiome.equals("Hell") && !varBiome.equals("The End")){
			return 0;
	    	}
		else if(varBiome.equals("Hell")){
			return -1;
		}
		else if(varBiome.equals("The End")){
			return 1;
		}
		return 2;
	}
}
