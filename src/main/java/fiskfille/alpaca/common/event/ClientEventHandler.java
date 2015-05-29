package fiskfille.alpaca.common.event;

import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;

import org.lwjgl.opengl.GL11;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import fiskfille.alpaca.Alpaca;
import fiskfille.alpaca.AlpacaReflection;
import fiskfille.alpaca.client.model.entity.ModelAlpaca;
import fiskfille.alpaca.client.render.entity.RenderPlayerHand;
import fiskfille.alpaca.common.proxy.ClientProxy;

public class ClientEventHandler
{
	private Minecraft mc = Minecraft.getMinecraft();
	public RenderPlayerHand renderHandInstance;
	public float renderTick;
	
	public ClientEventHandler()
	{
		renderHandInstance = new RenderPlayerHand();
        renderHandInstance.setRenderManager(RenderManager.instance);
	}
	
	@SubscribeEvent
    public void renderTick(TickEvent.RenderTickEvent event)
    {
        if (mc.theWorld != null)
        {
            if (event.phase == TickEvent.Phase.START)
            {
                this.renderTick = event.renderTickTime;
            }
        }
    }
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onRenderHand(RenderHandEvent event)
	{
		RenderPlayer render = (RenderPlayer)RenderManager.instance.entityRenderMap.get(mc.thePlayer.getClass());;
		
		if (render != null && Alpaca.proxy.clientEventHandler != null)
		{
			event.setCanceled(true);
			RenderPlayer rend = (RenderPlayer)RenderManager.instance.getEntityRenderObject(mc.thePlayer);
			
			GL11.glPushMatrix();
			GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
			Alpaca.proxy.clientEventHandler.renderHandInstance.progress = 1.0F;
            Alpaca.proxy.clientEventHandler.renderHandInstance.setParent(rend);
            Alpaca.proxy.clientEventHandler.renderHandInstance.resourceLoc = getAlpacaTexture(mc.thePlayer);
            Alpaca.proxy.clientEventHandler.renderHandInstance.replacement = ClientProxy.modelAlpaca.frontUpperLeg1;
            RenderManager.instance.entityRenderMap.put(mc.thePlayer.getClass(), Alpaca.proxy.clientEventHandler.renderHandInstance);
            
            
            
            AlpacaReflection.renderHand(mc.entityRenderer, Alpaca.proxy.clientEventHandler.renderTick, 0);
            
            
            
            RenderManager.instance.entityRenderMap.put(mc.thePlayer.getClass(), rend);
            GL11.glPopMatrix();
		}
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onRenderLivingPre(RenderLivingEvent.Pre event)
	{
		if (event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.entity;
			ModelBiped model = ((RenderPlayer)event.renderer).modelBipedMain;
			
			for (ModelRenderer modelrenderer : new ModelRenderer[]{model.bipedHead, model.bipedHeadwear, model.bipedBody, model.bipedRightArm, model.bipedLeftArm, model.bipedRightLeg, model.bipedLeftLeg})
			{
				modelrenderer.offsetY = 256;
			}
			
			GL11.glPushMatrix();
			mc.getTextureManager().bindTexture(getAlpacaTexture(player));
			
			float scale = 1.05F;
	        float f6 = player.prevLimbSwingAmount + (player.limbSwingAmount - player.prevLimbSwingAmount) * 0.0625F;
	        float f7 = player.limbSwing - player.limbSwingAmount * 0.0625F;

	        if (player.isChild())
	        {
	            f7 *= 3.0F;
	        }

	        if (f6 > 1.0F)
	        {
	            f6 = 1.0F;
	        }
	        
	        GL11.glTranslatef((float)event.x, (float)event.y, (float)event.z);
			GL11.glRotatef(-player.renderYawOffset, 0, 1, 0);
			GL11.glTranslated(0.0D, 0.5D * scale, -scale);
			GL11.glRotatef(90, 1, 0, 0);
			GL11.glColor4f(1, 1, 1, 1);
			GL11.glScalef(scale, scale, scale);
			ClientProxy.modelAlpaca.onGround = player.swingProgress;
			ClientProxy.modelAlpaca.isSneak = player.isSneaking();
			ClientProxy.modelAlpaca.render(player, f7, f6, 0, player.rotationYawHead - player.renderYawOffset, player.rotationPitch, 0.0625F);
			GL11.glPopMatrix();
		}
	}
	
	@SubscribeEvent
	public void onRenderPlayerSpecialsPre(RenderPlayerEvent.Specials.Pre event)
	{
		if (!event.isCanceled())
        {
            event.setCanceled(true);
            AbstractClientPlayer player = (AbstractClientPlayer)event.entityPlayer;
            ModelBiped modelBipedMain = event.renderer.modelBipedMain;
            ModelAlpaca model = ClientProxy.modelAlpaca;
            
            if (modelBipedMain != null)
            {
                float partialTicks = event.partialRenderTick;
                RenderManager renderManager = RenderManager.instance;
                
                float scale = 1.0F;
                GL11.glColor3f(1.0F, 1.0F, 1.0F);
                ItemStack helmetStack = player.inventory.armorItemInSlot(3);
                
                if (helmetStack != null && event.renderHelmet)
                {
                    GL11.glPushMatrix();
                    modelBipedMain.bipedHead.postRender(0.0625F);
                    Item helmet = helmetStack.getItem();
                    
                    if (helmet instanceof ItemBlock)
                    {
                        IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(helmetStack, net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED);
                        boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED, helmetStack, net.minecraftforge.client.IItemRenderer.ItemRendererHelper.BLOCK_3D));
                        
                        if (is3D || RenderBlocks.renderItemIn3d(Block.getBlockFromItem(helmet).getRenderType()))
                        {
                            scale = 0.625F;
                            GL11.glTranslatef(0.0F, -0.25F, 0.0F);
                            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                            GL11.glScalef(scale, -scale, -scale);
                        }
                        
                        renderManager.itemRenderer.renderItem(player, helmetStack, 0);
                    }
                    else if (helmet == Items.skull)
                    {
                        scale = 1.0625F;
                        GL11.glScalef(scale, -scale, -scale);
                        GameProfile gameprofile = null;
                        
                        if (helmetStack.hasTagCompound())
                        {
                            NBTTagCompound itemTag = helmetStack.getTagCompound();
                            
                            if (itemTag.hasKey("SkullOwner", 10))
                            {
                                gameprofile = NBTUtil.func_152459_a(itemTag.getCompoundTag("SkullOwner"));
                            }
                            else if (itemTag.hasKey("SkullOwner", 8) && !StringUtils.isNullOrEmpty(itemTag.getString("SkullOwner")))
                            {
                                gameprofile = new GameProfile((UUID) null, itemTag.getString("SkullOwner"));
                            }
                        }
                        
                        TileEntitySkullRenderer.field_147536_b.func_152674_a(-0.5F, 0.0F, -0.5F, 1, 180.0F, helmetStack.getItemDamage(), gameprofile);
                    }
                    
                    GL11.glPopMatrix();
                }
                
                boolean hasSkin = player.func_152123_o();
                
                if (player.getCommandSenderName().equals("deadmau5") && hasSkin)
                {
                    renderManager.renderEngine.bindTexture(player.getLocationSkin());
                    
                    for (int j = 0; j < 2; ++j)
                    {
                        float f9 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * partialTicks - (player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * partialTicks);
                        float f10 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * partialTicks;
                        GL11.glPushMatrix();
                        GL11.glRotatef(f9, 0.0F, 1.0F, 0.0F);
                        GL11.glRotatef(f10, 1.0F, 0.0F, 0.0F);
                        GL11.glTranslatef(0.375F * (float) (j * 2 - 1), 0.0F, 0.0F);
                        GL11.glTranslatef(0.0F, -0.375F, 0.0F);
                        GL11.glRotatef(-f10, 1.0F, 0.0F, 0.0F);
                        GL11.glRotatef(-f9, 0.0F, 1.0F, 0.0F);
                        scale = 1.3333334F;
                        GL11.glScalef(scale, scale, scale);
                        modelBipedMain.renderEars(0.0625F);
                        GL11.glPopMatrix();
                    }
                }
                
                boolean hasCape = player.func_152122_n();
                hasCape = event.renderCape && hasCape;
                float f4;
                
                if (hasCape && !player.isInvisible() && !player.getHideCape())
                {
                    renderManager.renderEngine.bindTexture(player.getLocationSkin());
                    GL11.glPushMatrix();
                    modelBipedMain.bipedBody.postRender(0.0625F);
                    
                    GL11.glTranslatef(0.0F, 0.0F, 0.125F);
                    double d3 = player.field_71091_bM + (player.field_71094_bP - player.field_71091_bM) * (double) partialTicks - (player.prevPosX + (player.posX - player.prevPosX) * (double) partialTicks);
                    double d4 = player.field_71096_bN + (player.field_71095_bQ - player.field_71096_bN) * (double) partialTicks - (player.prevPosY + (player.posY - player.prevPosY) * (double) partialTicks);
                    double d0 = player.field_71097_bO + (player.field_71085_bR - player.field_71097_bO) * (double) partialTicks - (player.prevPosZ + (player.posZ - player.prevPosZ) * (double) partialTicks);
                    f4 = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * partialTicks;
                    double d1 = (double) MathHelper.sin(f4 * (float) Math.PI / 180.0F);
                    double d2 = (double) (-MathHelper.cos(f4 * (float) Math.PI / 180.0F));
                    float f5 = (float) d4 * 10.0F;
                    
                    if (f5 < -6.0F)
                    {
                        f5 = -6.0F;
                    }
                    
                    if (f5 > 32.0F)
                    {
                        f5 = 32.0F;
                    }
                    
                    float f6 = (float) (d3 * d1 + d0 * d2) * 100.0F;
                    float f7 = (float) (d3 * d2 - d0 * d1) * 100.0F;
                    
                    if (f6 < 0.0F)
                    {
                        f6 = 0.0F;
                    }
                    
                    float f8 = player.prevCameraYaw + (player.cameraYaw - player.prevCameraYaw) * partialTicks;
                    f5 += MathHelper.sin((player.prevDistanceWalkedModified + (player.distanceWalkedModified - player.prevDistanceWalkedModified) * partialTicks) * 6.0F) * 32.0F * f8;
                    
                    GL11.glRotatef(6.0F + f6 / 2.0F + f5, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef(f7 / 2.0F, 0.0F, 0.0F, 1.0F);
                    GL11.glRotatef(-f7 / 2.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
                    modelBipedMain.renderCloak(0.0625F);
                    GL11.glPopMatrix();
                }
                
                ItemStack heldItemStack = player.inventory.getCurrentItem();
                
                if (heldItemStack != null && event.renderItem)
                {
                    GL11.glPushMatrix();
                    
                    if (player.fishEntity != null)
                    {
                        heldItemStack = new ItemStack(Items.stick);
                    }
                    
                    EnumAction action = null;
                    
                    if (player.getItemInUseCount() > 0)
                    {
                        action = heldItemStack.getItemUseAction();
                    }
                    
                    IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(heldItemStack, net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED);
                    boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED, heldItemStack, net.minecraftforge.client.IItemRenderer.ItemRendererHelper.BLOCK_3D));
                    
                    Item heldItem = heldItemStack.getItem();
                    
                    // Block
                    if (is3D || heldItem instanceof ItemBlock && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(heldItem).getRenderType()))
                    {
                    	scale = 0.5F;
                    	GL11.glTranslatef(-0.2F, 0.9F, -0.5F);
                    	scale *= 0.75F;
                    	GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
                    	GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                    	GL11.glScalef(-scale, -scale, scale);
                    }
                    // Bow
                    else if (heldItem == Items.bow)
                    {
                    	scale = 0.65F;
                    	float xRot = model.neck.rotateAngleX * (180F / (float)Math.PI) - 20;
                        GL11.glScalef(scale, scale, scale);
                        GL11.glTranslatef(-0.5F, 1F, 0.0F);
                        GL11.glRotatef(xRot, 1, 0, 0);
                        GL11.glTranslatef(0.0F, 0.7F, 0.0F);
                        GL11.glRotatef(220, 1, 0, 0);
                        GL11.glRotatef(-140, 0, 1, 0);
                    }
                    // Weapon/Tool
                    else if (heldItem.isFull3D())
                    {
                    	if (customRenderer != null)
                    	{
                    		GL11.glTranslatef(0, -0.1F, 0);
                    	}
                    	
                    	scale = 0.65F;
                    	float xRot = model.neck.rotateAngleX * (180F / (float)Math.PI) + 180;
                    	xRot = player.swingProgress > 0 ? Math.max(xRot, 220) : 220;
                        GL11.glScalef(scale, scale, scale);
                        GL11.glTranslatef(-0.5F, 1.25F, -0.25F);
                        
                        if (heldItem.shouldRotateAroundWhenRendering())
                    	{
                    		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                    		GL11.glRotatef(140.0F, 1.0F, 0.0F, 0.0F);
                    		GL11.glTranslatef(-0.1F, 0.1F, -0.35F);
                    	}
                        
                        GL11.glRotatef(xRot, 1, 0, 0);
                        GL11.glRotatef(-140, 0, 1, 0);
                        
                    	// Blocking
                    	if (player.getItemInUseCount() > 0 && action == EnumAction.block)
                    	{
                    		GL11.glTranslatef(0.1F, 0.0F, -0.6F);
                    		GL11.glRotatef(-50, 1, 0, 0);
                    		GL11.glRotatef(15, 0, 0, 1);
                    		GL11.glRotatef(70, 0, 1, 0);
                    		GL11.glRotatef(-20, 1, 0, 0);
                    	}
                    }
                    // Normal
                    else
                    {
                    	scale = 0.375F;
                        GL11.glScalef(scale, scale, scale);
                        GL11.glTranslatef(0.0F, 2.5F, -1.5F);
                        GL11.glRotatef(-30, 0, 0, 1);
                        GL11.glRotatef(240, 1, 0, 0);
                        GL11.glRotatef(-90, 0, 1, 0);
                        GL11.glRotatef(30, 0, 0, 1);
                        GL11.glRotatef(-10, 0, 1, 0);
                    }
                    
                    float f3;
                    int k;
                    float f12;
                    
                    if (heldItem.requiresMultipleRenderPasses())
                    {
                        for (k = 0; k < heldItem.getRenderPasses(heldItemStack.getItemDamage()); ++k)
                        {
                            int i = heldItem.getColorFromItemStack(heldItemStack, k);
                            f12 = (float) (i >> 16 & 255) / 255.0F;
                            f3 = (float) (i >> 8 & 255) / 255.0F;
                            f4 = (float) (i & 255) / 255.0F;
                            GL11.glColor4f(f12, f3, f4, 1.0F);
                            renderManager.itemRenderer.renderItem(player, heldItemStack, k);
                        }
                    }
                    else
                    {
                        k = heldItem.getColorFromItemStack(heldItemStack, 0);
                        float f11 = (float) (k >> 16 & 255) / 255.0F;
                        f12 = (float) (k >> 8 & 255) / 255.0F;
                        f3 = (float) (k & 255) / 255.0F;
                        GL11.glColor4f(f11, f12, f3, 1.0F);
                        renderManager.itemRenderer.renderItem(player, heldItemStack, 0);
                    }
                    
                    GL11.glPopMatrix();
                }
                net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderPlayerEvent.Specials.Post(player, event.renderer, event.partialRenderTick));
            }
        }
	}
	
	@SubscribeEvent
	public void onRenderPlayerArmor(RenderPlayerEvent.SetArmorModel event)
	{
		EntityPlayer player = (EntityPlayer)event.entity;
		ModelBiped model = event.renderer.modelBipedMain;
		
//		for (ModelRenderer modelrenderer : new ModelRenderer[]{model.bipedHead, model.bipedHeadwear, model.bipedBody, model.bipedRightArm, model.bipedLeftArm, model.bipedRightLeg, model.bipedLeftLeg})
//		{
//			modelrenderer.isHidden = false;
//		}
		
		event.result = 0;
	}
	
	public ResourceLocation getAlpacaTexture(EntityPlayer player)
	{
		int textureId = (player.getCommandSenderName().hashCode() - 1) % 5;
		textureId = textureId < 0 ? -textureId : textureId;
		textureId += 1;
		return new ResourceLocation(Alpaca.modid, "textures/entity/alpaca/alpaca" + textureId + ".png");
	}
}