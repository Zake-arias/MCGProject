/*
 * @file ModResources.java
 * @author Stefan Wilhelm (wile)
 * @copyright (C) 2018 Stefan Wilhelm
 * @license MIT (see https://opensource.org/licenses/MIT)
 *
 * Common extended functionality dealing with resource
 * files and corresponding settings/usage options.
 */
package moe.gensoukyo.mcgproject.common.feature.rsgauges.detail;

import moe.gensoukyo.mcgproject.common.feature.rsgauges.ModRsGauges;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

import java.util.LinkedList;

public class ModResources
{
  @ObjectHolder("rsgauges:alarm_siren_sound")
  public static final SoundEvent ALARM_SIREN_SOUND = SoundRegistry.createSoundEvent("alarm_siren_sound");

  /**
   * Registry event handling for the sounds listed above.
   */
  @Mod.EventBusSubscriber(modid=ModRsGauges.MODID)
  public static final class SoundRegistry
  {
    private static LinkedList<SoundEvent> created_sounds_ = new LinkedList<>();

    public static SoundEvent createSoundEvent(String name)
    {
      final ResourceLocation rl = new ResourceLocation(ModRsGauges.RESID, name);
      SoundEvent se = new SoundEvent(rl).setRegistryName(rl);
      created_sounds_.push(se);
      return se;
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onRegistryEvent(RegistryEvent.Register<SoundEvent> event)
    {
      for(SoundEvent se:created_sounds_) {event.getRegistry().register(se);}
      created_sounds_.clear();
    }
  }

  /**
   * Block sound player class used in the code, additionally specifying
   * playback parameters for the sound.
   */
  @SuppressWarnings("unused")
  public static final class BlockSoundEvent
  {
    final SoundEvent se_;
    final float volume_, pitch_;
    public BlockSoundEvent(SoundEvent se, float volume, float pitch) { se_=se; volume_=volume; pitch_=pitch; }
    public BlockSoundEvent(SoundEvent se, float volume) { this(se, volume, 1f); }
    public BlockSoundEvent(SoundEvent se) { this(se, 1f, 1f); }
    public SoundEvent sound() { return se_; }
    public float volume() { return volume_; }
    public float pitch() { return pitch_; }
    public void play(World world, BlockPos pos) { world.playSound(null, pos, se_, SoundCategory.BLOCKS, volume_, pitch_); }
  }

  public static final class BlockSoundEvents
  {
    // Switch default sounds
    public static final BlockSoundEvent DEFAULT_SWITCH_MUTE          = new BlockSoundEvent(SoundEvents.BLOCK_LEVER_CLICK, 0f, 1f);
    public static final BlockSoundEvent DEFAULT_SWITCH_ACTIVATION    = new BlockSoundEvent(SoundEvents.BLOCK_LEVER_CLICK, 0.3f, 0.92f);
    public static final BlockSoundEvent DEFAULT_SWITCH_DEACTIVATION  = new BlockSoundEvent(SoundEvents.BLOCK_LEVER_CLICK, 0.3f, 0.82f);
    public static final BlockSoundEvent DEFAULT_SWITCH_CONFIGCLICK   = new BlockSoundEvent(SoundEvents.BLOCK_LEVER_CLICK, 0.01f, 1.9f);
    // Switch link sounds
    public static final BlockSoundEvent SWITCHLINK_CANNOT_LINK_THAT      = new BlockSoundEvent(SoundEvents.ENTITY_ENDERMEN_SCREAM, 0.2f, 2.5f);
    public static final BlockSoundEvent SWITCHLINK_LINK_TARGET_SELECTED  = new BlockSoundEvent(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 0.2f, 2.0f);
    public static final BlockSoundEvent SWITCHLINK_LINK_SOURCE_SELECTED  = new BlockSoundEvent(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 0.2f, 2.0f);
    public static final BlockSoundEvent SWITCHLINK_LINK_SOURCE_FAILED    = SWITCHLINK_CANNOT_LINK_THAT;
    public static final BlockSoundEvent SWITCHLINK_LINK_PEAL_USE_SUCCESS = new BlockSoundEvent(SoundEvents.ENTITY_ENDERMEN_AMBIENT, 0.1f, 4f);
    public static final BlockSoundEvent SWITCHLINK_LINK_PEAL_USE_FAILED  = new BlockSoundEvent(SoundEvents.ENTITY_ENDERMEN_HURT, 0.1f, 2.0f);
  }
}
