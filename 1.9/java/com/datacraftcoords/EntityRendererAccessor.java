package com.datacraftcoords;


import net.minecraft.client.renderer.EntityRenderer;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityRendererAccessor {

private static final Logger L = LogManager.getLogger();

private static final String[] FIELD_CAMERA_ZOOM = new String[] {"cameraZoom", "field_78503_V"};

private EntityRendererAccessor() {
}

public static void setCameraZoom(EntityRenderer renderer, double zoom) {
try {
ReflectionHelper.setPrivateValue(EntityRenderer.class, renderer, zoom, FIELD_CAMERA_ZOOM);
} catch (Exception ex) {
L.error("setCameraZoom() failed", ex);
}
}

public static double getCameraZoom(EntityRenderer renderer) {
try {
return ReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, FIELD_CAMERA_ZOOM);
} catch (Exception ex) {
L.error("getCameraZoom() failed", ex);
return 0;
}
}
}