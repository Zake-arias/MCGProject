package moe.gensoukyo.mcgproject.common.entity;

import moe.gensoukyo.mcgproject.common.init.ModItem;
import moe.gensoukyo.mcgproject.common.network.*;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

/**
 * @author drzzm32
 * @date 2020/3/10
 */
public class EntityMCGBoat extends EntityBoat {

    public float threshold = 0.2F;
    public float damage = 10.0F;
    public float jump = 0.4F;

    /**
     * @apiNote 客户端-服务端同步用，用于撞击判定，不需要存入NBT
     * */
    public double vel = 0;

    /**
     * @apiNote 给NPC预留的方法
     * @param boat 船实体
     * @param threshold 撞击阈值速度
     * */
    public static void setThreshold(EntityMCGBoat boat, float threshold) {
        boat.threshold = threshold;
    }

    /**
     * @apiNote 给NPC预留的方法
     * @param boat 船实体
     * @param damage 撞击伤害
     * */
    public static void setMaxDamage(EntityMCGBoat boat, float damage) {
        boat.damage = damage;
    }

    /**
     * @apiNote 给NPC预留的方法
     * @param boat 船实体
     * @param jump 起跳速度
     * */
    public static void setBoatJump(EntityMCGBoat boat, float jump) {
        boat.jump = jump;
    }

    public EntityMCGBoat(World world) {
        super(world);
    }

    public EntityMCGBoat(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Override
    protected void readEntityFromNBT(@Nonnull NBTTagCompound tagCompound) {
        if (tagCompound.hasKey("jump"))
            jump = tagCompound.getFloat("jump");
        if (tagCompound.hasKey("damage"))
            damage = tagCompound.getFloat("damage");
        if (tagCompound.hasKey("threshold"))
            threshold = tagCompound.getFloat("threshold");
    }

    @Override
    protected void writeEntityToNBT(@Nonnull NBTTagCompound tagCompound) {
        tagCompound.setFloat("jump", jump);
        tagCompound.setFloat("damage", damage);
        tagCompound.setFloat("threshold", threshold);
    }

    @Override
    @Nonnull
    public Item getItemBoat() {
        return ModItem.ITEM_MCG_BOAT;
    }

    /**
     * @apiNote 用于检测船下面是否为正常路面方块
     * */
    public boolean isOnSoftSurface() {
        List<Material> hardFace = Arrays.asList(
                Material.GROUND, Material.WOOD, Material.ROCK, Material.IRON, Material.GLASS,
                Material.SAND, Material.SNOW, Material.CRAFTED_SNOW, Material.CLAY
        );
        Material material = this.world.getBlockState(this.getPosition().down()).getMaterial();
        return !hardFace.contains(material);
    }
    
    /**
     * @apiNote 船的接触面光滑度，用于控制在地面上移动的最高速度
     * @apiNote 当值为 0.984 时，最高速度为 2.5m/t (180km/h)
     * @apiNote 当值为 0.976 时，最高速度为 1.6m/t (120km/h)
     * @apiNote v(t+1) = v(t) * glide;
     * @apiNote v(t+1) = v(t) + 0.04;
     * */
    @Override
    public float getBoatGlide() {
        return isOnSoftSurface() ? 0.976F : 0.984F;
    }

    public float getMaxSpeed() {
        return isOnSoftSurface() ? 2.5F : 1.667F;
    }

    public double getBlockHeight(BlockPos pos) {
        IBlockState state = this.world.getBlockState(pos);
        AxisAlignedBB box = state.getBoundingBox(this.world, pos);
        return box.maxY;
    }

    /**
     * @apiNote 把客户端的船速率同步至服务端
     * @apiNote 同时实现船起跳的功能
     * */
    @SideOnly(Side.CLIENT)
    public void doBoatJump() {
        if (this.getControllingPassenger() instanceof EntityPlayerSP) {
            BoatPacket packet = new BoatPacket(this);
            NetworkWrapper.INSTANCE.sendToServer(packet);

            Vec3d vec = this.getLookVec().normalize();
            BlockPos pos = new BlockPos(this.getPositionVector().add(vec));
            if (!this.world.isAirBlock(pos) || !this.world.isAirBlock(pos.up())) {
                double slopeHeight = pos.getY() + getBlockHeight(pos) - this.posY;
                if (!this.world.isAirBlock(pos.up()))
                    slopeHeight += getBlockHeight(pos.up());
                if (slopeHeight > 0 && slopeHeight <= 0.5 && this.vel != 0)
                    this.motionY += (0.5 * this.jump);
            }

            EntityPlayerSP player = (EntityPlayerSP) this.getControllingPassenger();
            player.movementInput.updatePlayerMoveState();
            player.updateEntityActionState();
            player.moveVertical = player.movementInput.jump ? 1.0F : 0.0F;
            if (Math.abs(this.motionY) <= 0.5 * this.jump)
                this.motionY += (player.moveVertical * this.jump);
        }
    }

    @Override
    public void onUpdate() {
        // 使船能够空格起跳
        // TODO: 你能信，船的核心代码是客户端执行
        if (this.world.isRemote)
            doBoatJump();

        super.onUpdate();

        this.vel = Math.sqrt(
                this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);

        // 禁止非人类生物上船
        if (!this.world.isRemote) {
            for (Entity entity : this.getPassengers())
                if (!(entity instanceof EntityPlayer))
                    this.removePassenger(entity);
        }
    }

    /**
     * @apiNote 船与实体碰撞
     * @param entity 目标实体
     * */
    @Override
    public void applyEntityCollision(Entity entity) {
        super.applyEntityCollision(entity);

        // 撞击生物并造成伤害
        if (entity instanceof EntityLivingBase && !this.isPassenger(entity) && this.vel > this.threshold) {
            EntityLivingBase living = (EntityLivingBase) entity;
            living.setHealth(living.getHealth() - (float) (this.damage * this.vel / this.getMaxSpeed()));
            PotionType type;
            type = PotionType.getPotionTypeForName("weakness");
            if (type != null)
                for (PotionEffect effect : type.getEffects())
                    effect.getPotion().affectEntity(null, null, living, effect.getAmplifier(), 1.0F);
            type = PotionType.getPotionTypeForName("harming");
            if (type != null)
                for (PotionEffect effect : type.getEffects())
                    effect.getPotion().affectEntity(null, null, living, effect.getAmplifier(), 1.0F);
            Vec3d tar = entity.getPositionVector();
            Vec3d src = this.getPositionVector();
            Vec3d vec = tar.subtract(src).normalize().scale(this.vel * 2);
            living.addVelocity(vec.x, vec.y, vec.z);
        }
    }

    @Override
    protected void updateFallState(double dist, boolean fall, IBlockState state, BlockPos pos) {
        // 防止船撞坏
        if (!this.isRiding() && fall && this.fallDistance > 3.0F)
            return;

        super.updateFallState(dist, fall, state, pos);
    }

}