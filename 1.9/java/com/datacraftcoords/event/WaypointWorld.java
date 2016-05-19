package com.datacraftcoords.event;

import java.util.HashMap;

public class WaypointWorld {
    public HashMap<String, WaypointSet> sets = new HashMap<String, WaypointSet>();
    public String current = "gui.default";

    public WaypointWorld() {
        this.sets.put("gui.default", new WaypointSet(this));
    }

    public WaypointSet getCurrentSet() {
        return this.sets.get(this.current);
    }
}
