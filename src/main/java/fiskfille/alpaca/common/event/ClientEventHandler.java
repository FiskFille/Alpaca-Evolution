package fiskfille.alpaca.common.event;

import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
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
import fiskfille.alpaca.client.model.entity.ModelAlpacaBase;
import fiskfille.alpaca.client.render.entity.RenderPlayerHand;
import fiskfille.alpaca.common.color.ColorHelper;
import fiskfille.alpaca.common.data.AlpacaModelManager;
import fiskfille.alpaca.common.proxy.ClientProxy;

public class ClientEventHandler
{
    private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");

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
        EntityClientPlayerMP player = mc.thePlayer;

        RenderPlayer render = (RenderPlayer) RenderManager.instance.entityRenderMap.get(player.getClass());
        ;

        ClientEventHandler clientEventHandler = Alpaca.proxy.clientEventHandler;

        if (render != null && clientEventHandler != null)
        {
            event.setCanceled(true);
            RenderPlayer rend = (RenderPlayer) RenderManager.instance.getEntityRenderObject(player);

            GL11.glPushMatrix();
            GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
            clientEventHandler.renderHandInstance.setParent(rend);
            clientEventHandler.renderHandInstance.replacement = ClientProxy.getModelAlpaca(player).getArm();
            RenderManager.instance.entityRenderMap.put(player.getClass(), clientEventHandler.renderHandInstance);

            AlpacaReflection.renderHand(mc.entityRenderer, clientEventHandler.renderTick, 0);

            RenderManager.instance.entityRenderMap.put(player.getClass(), rend);
            GL11.glPopMatrix();
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onRenderLivingPre(RenderLivingEvent.Pre event)
    {
        if (event.entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.entity;
            RenderPlayer render = (RenderPlayer) event.renderer;
            ModelBiped model = render.modelBipedMain;

            for (ModelRenderer modelrenderer : new ModelRenderer[] { model.bipedHead, model.bipedHeadwear, model.bipedBody, model.bipedRightArm, model.bipedLeftArm, model.bipedRightLeg, model.bipedLeftLeg })
            {
                modelrenderer.offsetY = 256;
            }

            GL11.glPushMatrix();
            float scale = 1.05F;
            GL11.glTranslatef((float) event.x, (float) event.y, (float) event.z);
            GL11.glRotatef(-player.renderYawOffset, 0, 1, 0);
            GL11.glTranslated(0.0D, 0.5D * scale, -scale);
            GL11.glRotatef(90, 1, 0, 0);
            GL11.glColor4f(1, 1, 1, 1);
            GL11.glScalef(scale, scale, scale);

            ModelAlpacaBase modelAlpaca = ClientProxy.getModelAlpaca(player);

            modelAlpaca.onGround = player.swingProgress;
            modelAlpaca.isSneak = player.isSneaking();

            if (!player.isInvisible())
            {
                renderAlpaca(player, 1.0F);
            }
            else if (!player.isInvisibleToPlayer(mc.thePlayer))
            {
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                renderAlpaca(player, 0.35F);
            }

            GL11.glTranslated(0.0D, 1.0D, -1);
            GL11.glColor4f(1, 1, 1, 1);
            GL11.glRotatef(90, 1, 0, 0);

            for (int i = 0; i < 4; ++i)
            {
                renderArmor(player, i);
                ItemStack itemstack = player.getCurrentArmor(i);

                if (itemstack != null && itemstack.isItemEnchanted())
                {
                    float f8 = (float) player.ticksExisted + renderTick;
                    mc.getTextureManager().bindTexture(RES_ITEM_GLINT);
                    GL11.glEnable(GL11.GL_BLEND);
                    float f9 = 0.5F;
                    GL11.glColor4f(f9, f9, f9, 1.0F);
                    GL11.glDepthFunc(GL11.GL_EQUAL);
                    GL11.glDepthMask(false);

                    for (int k = 0; k < 2; ++k)
                    {
                        GL11.glDisable(GL11.GL_LIGHTING);
                        float f10 = 0.76F;
                        GL11.glColor4f(0.5F * f10, 0.25F * f10, 0.8F * f10, 1.0F);
                        GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
                        GL11.glMatrixMode(GL11.GL_TEXTURE);
                        GL11.glLoadIdentity();
                        float f11 = f8 * (0.001F + (float) k * 0.003F) * 20.0F;
                        float f12 = 0.33333334F;
                        GL11.glScalef(f12, f12, f12);
                        GL11.glRotatef(30.0F - (float) k * 60.0F, 0.0F, 0.0F, 1.0F);
                        GL11.glTranslatef(0.0F, f11, 0.0F);
                        GL11.glMatrixMode(GL11.GL_MODELVIEW);
                        renderArmor(player, i);
                    }

                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    GL11.glMatrixMode(GL11.GL_TEXTURE);
                    GL11.glDepthMask(true);
                    GL11.glLoadIdentity();
                    GL11.glMatrixMode(GL11.GL_MODELVIEW);
                    GL11.glEnable(GL11.GL_LIGHTING);
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glDepthFunc(GL11.GL_LEQUAL);
                }
            }

            GL11.glPopMatrix();
        }
    }

    public void renderArmor(EntityPlayer player, int slot)
    {
        float f6 = player.prevLimbSwingAmount + (player.limbSwingAmount - player.prevLimbSwingAmount) * 0.0625F;

        if (f6 > 1.0F)
        {
            f6 = 1.0F;
        }

        ItemStack itemstack = player.getCurrentArmor(slot);

        if (itemstack != null && itemstack.getItem() instanceof ItemArmor)
        {
            ItemArmor itemarmor = (ItemArmor) itemstack.getItem();
            mc.getTextureManager().bindTexture(RenderBiped.getArmorResource(player, itemstack, 3 - slot, null));
            ClientProxy.modelAlpacaArmor.syncArmorAngles((ModelAlpaca) ClientProxy.getModelAlpaca(player));

            int j = itemarmor.getColor(itemstack);

            if (j != -1)
            {
                float f1 = (float) (j >> 16 & 255) / 255.0F;
                float f2 = (float) (j >> 8 & 255) / 255.0F;
                float f3 = (float) (j & 255) / 255.0F;
                GL11.glColor3f(f1, f2, f3);
            }

            if (slot == 3)
            {
                ClientProxy.modelAlpacaArmor.renderHelmet();
            }
            else if (slot == 2)
            {
                ClientProxy.modelAlpacaArmor.renderChestplate();
            }
            else if (slot == 1)
            {
                ClientProxy.modelAlpacaArmor.renderLegs(0.8F);
            }
            else if (slot == 0)
            {
                ClientProxy.modelAlpacaArmor.renderLegs(1.1F);
            }

            GL11.glColor4f(1, 1, 1, 1);
        }
    }

    public void renderAlpaca(EntityPlayer player, float alpha)
    {
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

        TextureManager textureManager = mc.getTextureManager();

        String texture = AlpacaModelManager.getTexture(player);
        
        textureManager.bindTexture(new ResourceLocation(Alpaca.modid, "textures/entity/alpaca/" + texture + ".png"));
        GL11.glColor4f(1, 1, 1, alpha);
        
        ModelAlpacaBase modelAlpaca = ClientProxy.getModelAlpaca(player);
        
        modelAlpaca.render(player, f7, f6, 0, player.rotationYawHead - player.renderYawOffset, player.rotationPitch, 0.0625F);

        textureManager.bindTexture(new ResourceLocation(Alpaca.modid, "textures/entity/alpaca/" + texture + "_overlay.png"));

        ColorHelper.setColorFromInt(ColorHelper.getAlpacaColor(player), alpha);
        modelAlpaca.render(player, f7, f6, 0, player.rotationYawHead - player.renderYawOffset, player.rotationPitch, 0.0625F);
    }

    @SubscribeEvent
    public void onRenderPlayerSpecialsPre(RenderPlayerEvent.Specials.Pre event)
    {
        if (!event.isCanceled())
        {
            event.setCanceled(true);
            AbstractClientPlayer player = (AbstractClientPlayer) event.entityPlayer;
            ModelBiped modelBipedMain = event.renderer.modelBipedMain;
            ModelAlpacaBase model = ClientProxy.getModelAlpaca(player);

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

                    scale = 1.0F;
                    GL11.glScalef(scale, scale, scale);
                    GL11.glTranslatef(0, 0.46F, 0.3F);
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
                        float xRot = model.getNeck().rotateAngleX * (180F / (float) Math.PI) - 20;
                        GL11.glScalef(scale, scale, scale);
                        GL11.glTranslatef(-0.5F, 1F, 0.0F);
                        GL11.glRotatef(xRot, 1, 0, 0);
                        GL11.glTranslatef(0.0F, 0.7F, 0.0F);
                        GL11.glRotatef(220, 1, 0, 0);
                        GL11.glRotatef(-140, 0, 1, 0);

                        if (player.getItemInUseCount() > 0 && action == EnumAction.bow)
                        {
                            player.renderYawOffset = player.rotationYaw;
                        }
                    }
                    // Weapon/Tool
                    else if (heldItem.isFull3D())
                    {
                        if (customRenderer != null)
                        {
                            GL11.glTranslatef(0, -0.1F, 0);
                        }

                        scale = 0.65F;
                        float xRot = model.getNeck().rotateAngleX * (180F / (float) Math.PI) + 180;
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
        for (int j = 0; j < 2; ++j)
        {
            ModelBiped model = new ModelBiped[] { event.renderer.modelArmor, event.renderer.modelArmorChestplate }[j];
            ModelRenderer[] amodel = new ModelRenderer[] { model.bipedHead, model.bipedHeadwear, model.bipedBody, model.bipedRightArm, model.bipedLeftArm, model.bipedRightLeg, model.bipedLeftLeg };

            for (int i = 0; i < amodel.length; ++i)
            {
                amodel[i].isHidden = true;
            }
        }
    }
}