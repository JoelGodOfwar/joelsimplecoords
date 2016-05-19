package com.datacraftcoords;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MyMessage implements IMessage {
	   
	public static boolean plgLLenabled = true;
	public static boolean plgCIenabled = true;
	public static boolean plgPTenabled = true;
	public static boolean plgCCenabled = true;
	public static boolean plgZOOMenabled = true;
	private String text;

    public MyMessage() { }

    public MyMessage(String text) {
        this.text = text;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        text = ByteBufUtils.readUTF8String(buf); // this class is very useful in general for writing more complex objects
    	
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, text);
    	
    }

    public static class Handler implements IMessageHandler<MyMessage, IMessage> {
        @Override
        public IMessage onMessage(final MyMessage message, final MessageContext ctx) {
            //IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj; // or Minecraft.getMinecraft() on the client
        	
        	if (ctx.side != Side.CLIENT) {
        	      System.err.println("TargetEffectMessageToClient received on wrong side:" + ctx.side);
        	      return null;
        	    }else{
        	    	//System.out.println("MyMessage else triggered.................................................................");
        	    	Minecraft minecraft = Minecraft.getMinecraft();
        	minecraft.addScheduledTask(new Runnable() {
                @Override
                public void run() {
                	//System.out.println("Run has been triggered.................................................................");
                	System.out.println(String.format("Received %s from %s", message.text, "Server"));
                	String msg = message.text;
                	msg = msg.replace("0", "");
                	String[] msg2 = msg.split(",");
                	String LL = msg2[0].substring(3);
                	String CI = msg2[1].substring(3);
                	String PT = msg2[2].substring(3);
                	String CC = msg2[3].substring(3);
                	String ZOOM = msg2[4].substring(5);
                	//System.out.println(LL + " " + CI + " " + PT + " " + CC + " " + ZOOM + ".................................................................");
                	//TODO: 
                	if(LL.equalsIgnoreCase("false")){
                		MyMessage.plgLLenabled = false;
                		LL = TextFormatting.RED + "Disabled" + TextFormatting.WHITE;
                		}
                	else if(LL.equalsIgnoreCase("true")){
                			LL = TextFormatting.GREEN + "Enabled" + TextFormatting.WHITE;
                			MyMessage.plgLLenabled = true;
                		}
                	if(CI.equalsIgnoreCase("false")){
                		MyMessage.plgCIenabled = false;
                		CI = TextFormatting.RED + "Disabled" + TextFormatting.WHITE;
                		}
                	else if(CI.equalsIgnoreCase("true")){
                			CI = TextFormatting.GREEN + "Enabled" + TextFormatting.WHITE;
                			MyMessage.plgLLenabled = true;
                		}
                	if(PT.equalsIgnoreCase("false")){
                		MyMessage.plgPTenabled = false;
                		PT = TextFormatting.RED + "Disabled" + TextFormatting.WHITE;
                		}
                	else if(PT.equalsIgnoreCase("true")){
                			PT = TextFormatting.GREEN + "Enabled" + TextFormatting.WHITE;
                			MyMessage.plgLLenabled = true;
                		}
                	if(CC.equalsIgnoreCase("false")){
                		MyMessage.plgCCenabled = false;
                		CC = TextFormatting.RED + "Disabled" + TextFormatting.WHITE;
                		}
                	else if(CC.equalsIgnoreCase("true")){
                			CC = TextFormatting.GREEN + "Enabled" + TextFormatting.WHITE;
                			MyMessage.plgLLenabled = true;
                		}
                	if(ZOOM.equalsIgnoreCase("false")){
                		MyMessage.plgZOOMenabled = false;
                		ZOOM = TextFormatting.RED + "Disabled" + TextFormatting.WHITE;
                		}
                	else if(ZOOM.equalsIgnoreCase("true")){
                			ZOOM = TextFormatting.GREEN + "Enabled" + TextFormatting.WHITE;
                			MyMessage.plgLLenabled = true;
                		}
                	Do.Say(mc.thePlayer, TextFormatting.RED + "This server is using the " + TextFormatting.YELLOW + "JSCM Companion Plugin");
                	Do.Say(mc.thePlayer, "The Following features of JSCM have been given these permissions.");
                	//Do.Say(mc.thePlayer, "True is Enabled, False is Disabled.");
                	Do.Say(mc.thePlayer, "Light Level=" + LL + ", Chunk Information=" + CI + ", Potion Timers=" + PT + ", Send Coords to chat=" + CC + ", Zoom=" + ZOOM + "");
                }
            });
            return null; // no response in this case
        }}


		

		
    }

    protected static Minecraft mc = Minecraft.getMinecraft();
}
