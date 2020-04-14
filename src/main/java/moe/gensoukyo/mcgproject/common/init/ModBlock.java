package moe.gensoukyo.mcgproject.common.init;

import moe.gensoukyo.mcgproject.common.backpack.GensoChest;
import moe.gensoukyo.mcgproject.common.block.*;
import moe.gensoukyo.mcgproject.common.block.enums.EnumTileColor;
import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import moe.gensoukyo.mcgproject.common.item.ItemBlockWithMeta;
import moe.gensoukyo.mcgproject.common.item.ItemMCGBlock;
import moe.gensoukyo.mcgproject.common.block.ranstone.*;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Objects;

public class ModBlock {

    private static ModBlock instance;
    public static ModBlock instance() {
        if(instance == null) instance = new ModBlock();
        return instance;
    }

    public static Block TILE = new BlockTile();
    public static Block NAMAKO = new BlockInteger2(Material.CLAY, "namako", MCGTabs.TOUHOU, SoundType.STONE);
    public static Block FLOWER = new BlockMCGFlower("flower");
    public static Block GAP = new BlockMCG(Material.WOOD, "gap", MCGTabs.FANTASY);
    public static Block BLOCK_CHIREIDEN = new BlockInteger8(Material.GLASS, "glass_chireiden", MCGTabs.EUROPEAN, SoundType.GLASS).setLightLevel(0.67F);
    public static Block MARBLE = new BlockInteger2(Material.ROCK, "marble", MCGTabs.EUROPEAN, SoundType.STONE);
    public static Block WOOL_GLOWING = new BlockInteger16(Material.CLOTH, "wool_glowing", MCGTabs.NORMAL, SoundType.CLOTH).setLightLevel(1.0F);

    /**
     * 实例化方块，并将实例化的方块分配到相应链表
     */
    private ModBlock() {

        MCGProject.logger.info("MCGProject: loading blocks");

        //东方
        blocks2.add(NAMAKO);
        blocks2.add(new BlockTatami());
        blocks4.add(new BlockInteger4(Material.CLAY, "plaster", MCGTabs.TOUHOU, SoundType.STONE));
        blocks16.add(new BlockInteger16(Material.WOOD, "plank", MCGTabs.TOUHOU, SoundType.WOOD));
        blocks4.add(new BlockInteger4(Material.WOOD, "blocks_wood", MCGTabs.TOUHOU, SoundType.WOOD));
        blocks5.add(TILE);
        blocks2.add(new BlockWindow(Material.WOOD, "window", MCGTabs.TOUHOU, SoundType.WOOD));
        blocks2.add(new BlockInteger2(Material.WOOD, "lantern", MCGTabs.TOUHOU, SoundType.WOOD).setLightLevel(1.0F));
        blocks8.add(new BlockDecoration2x8(Material.CLOTH, "door_curtain_1", MCGTabs.TOUHOU, SoundType.CLOTH));
        blocks8.add(new BlockDecoration2x8(Material.CLOTH, "door_curtain_2", MCGTabs.TOUHOU, SoundType.CLOTH));
        blocks3.add(new BlockWoodPane("pane_wood"));
        blocks4.add(new BlockDecoration4x4(Material.WOOD, "slipper", MCGTabs.TOUHOU, SoundType.WOOD));
        Block MCGDIRT = new BlockInteger4(Material.GROUND, "dirt", MCGTabs.NATURE, SoundType.GROUND);
        blocks4.add(MCGDIRT);
        blocks1.add(new BlockMCGStairs(MCGDIRT.getDefaultState(), "stairs_dirt_stone", MCGTabs.NATURE, SoundType.GROUND));
        blocks1.add(new BlockMCGSlab(MCGDIRT, 2, "slab_dirt_stone", MCGTabs.NATURE));
        blocks3.add(new BlockInteger3(Material.SAND, "karesansui", MCGTabs.TOUHOU, SoundType.SAND));

        for (EnumTileColor color : EnumTileColor.values()) {
            blocks1.add(new BlockMCGStairs(TILE.getDefaultState(), String.format("stairs_tile_%s", color.getName()), MCGTabs.TOUHOU, SoundType.STONE));
            blocks1.add(new BlockMCGSlab(TILE, color.getMeta(), String.format("slab_tile_%s", color.getName())));
        }

        //自然与农业
        blocks1.add(new BlockMCGLog("log_sakura"));
        blocks5.add(FLOWER);
        blocks2.add(new BlockMCGMushroom("mushroom"));
        blocks1.add(new BlockTranslucent(Material.CLOTH, "cloud", MCGTabs.NATURE, SoundType.CLOTH));
        blocks1.add(new BlockMCGLeaves("leaves_sakura_glowing").setLightLevel(0.3F));
        blocks8.add(new BlockMCGLeaves8("leaves_mcg"));
        blocks3.add(new BlockBambooOld("bamboo_old"));
        blocks2.add(new BlockInteger2(Material.CAKE, "mooncake", MCGTabs.NATURE, SoundType.SNOW));
        blocks1.add(new BlockMCG(Material.GROUND, "shit", MCGTabs.NATURE, SoundType.GROUND));
        blocks1.add(new BlockMCGLog("straw", MCGTabs.NATURE, SoundType.PLANT));
        blocks1.add(new BlockMCG(Material.LEAVES, "leaves_vine", MCGTabs.NATURE, SoundType.PLANT));

        //幻想
        blocks1.add(GAP);
        blocks1.add(new GensoChest());

        //西方
        blocks8.add(BLOCK_CHIREIDEN);
        blocks16.add(new BlockInteger16(Material.GLASS, "glass_chireiden_a", MCGTabs.EUROPEAN, SoundType.GLASS).setLightLevel(0.67F));
        blocks16.add(new BlockInteger16(Material.GLASS, "glass_chireiden_b", MCGTabs.EUROPEAN, SoundType.GLASS).setLightLevel(0.67F));
        blocks4.add(new BlockRotate4x4(Material.CLOTH, "carpet_red_rotation", MCGTabs.EUROPEAN, SoundType.CLOTH));
        blocks4.add(new BlockRotate4x4(Material.CLOTH, "carpet_blue_rotation", MCGTabs.EUROPEAN, SoundType.CLOTH));
        blocks2.add(new BlockInteger2(Material.CLOTH, "carpet_blank", MCGTabs.EUROPEAN, SoundType.CLOTH));
        blocks4.add(new BlockInteger4(Material.ROCK, "brick_chireiden", MCGTabs.EUROPEAN, SoundType.STONE));
        blocks2.add(new BlockInteger2(Material.CLAY, "wall_marisa", MCGTabs.EUROPEAN, SoundType.STONE));

        //现代
        blocks2.add(MARBLE);
        blocks1.add(new BlockMCGStairs(MARBLE.getDefaultState(), "stairs_marble_black", MCGTabs.EUROPEAN, SoundType.STONE));
        blocks1.add(new BlockMCGStairs(MARBLE.getDefaultState(), "stairs_marble_white", MCGTabs.EUROPEAN, SoundType.STONE));
        blocks1.add(new BlockMCGSlab(MARBLE, 0, "slab_marble_black"));
        blocks1.add(new BlockMCGSlab(MARBLE, 1, "slab_marble_white"));
        blocks1.add(new BlockMCG(Material.ROCK, "road_block", MCGTabs.EUROPEAN, SoundType.STONE));
        blocks1.add(new BlockMCG(Material.CLOTH, "newspaper", MCGTabs.EUROPEAN, SoundType.CLOTH));

        //原版拓展
        blocks16.add(WOOL_GLOWING);
        blocks16.add(new BlockWoolPane("pane_wool"));
        blocks1.add(new BlockTransparentStairs(Blocks.GLASS.getDefaultState(), "stairs_glass", MCGTabs.NORMAL, SoundType.GLASS));
        blocks1.add(new BlockTransparentSlab(Blocks.GLASS, 0, "slab_glass", MCGTabs.NORMAL));
        blocks1.add(new BlockMCGTrapDoor(Material.WOOD, "trapdoor_spruce", MCGTabs.NORMAL));
        blocks1.add(new BlockMCGTrapDoor(Material.WOOD, "trapdoor_dark_oak", MCGTabs.NORMAL));
        blocks1.add(new BlockMCGTrapDoor(Material.WOOD, "trapdoor_jungle", MCGTabs.NORMAL));
        blocks1.add(new BlockMCGTrapDoor(Material.WOOD, "trapdoor_birch", MCGTabs.NORMAL));
        blocks1.add(new BlockMCGTrapDoor(Material.WOOD, "trapdoor_acacia", MCGTabs.NORMAL));
        for (EnumDyeColor color : EnumDyeColor.values()) {
            blocks1.add(new BlockMCGStairs(Blocks.WOOL.getDefaultState(), String.format("stairs_wool_%s", color.getName()), MCGTabs.NORMAL, SoundType.CLOTH));
            blocks1.add(new BlockMCGSlab(Blocks.WOOL, color.getMetadata(), String.format("slab_wool_%s", color.getName()), MCGTabs.NORMAL));
        }

        //old
        blocks16.add(new BlockInteger16(Material.WOOD, "fn_log", MCGTabs.OLD, SoundType.WOOD));
        blocks16.add(new BlockInteger16(Material.WOOD, "fn_plank", MCGTabs.OLD, SoundType.WOOD));
        blocks6.add(new BlockInteger6(Material.WOOD, "fn_plank2", MCGTabs.OLD, SoundType.WOOD));
        blocks8.add(new BlockInteger8(Material.ROCK, "fn_bricks", MCGTabs.OLD, SoundType.STONE));
        blocks9.add(new BlockFNFlower("fn_flower").setCreativeTab(MCGTabs.OLD));
        blocks16.add(new BlockMCGLeaves16("fn_leaves").setCreativeTab(MCGTabs.OLD));
        blocks16.add(new BlockMCGLeaves16("fn_leaves2").setCreativeTab(MCGTabs.OLD));
        blocks16.add(new BlockTransparent16(Material.GLASS, "fn_glass", MCGTabs.OLD, SoundType.GLASS));
        blocks12.add(new BlockInteger12(Material.ROCK, "old_stone", MCGTabs.OLD, SoundType.STONE));
        blocks1.add(new BlockMCG(Material.ROCK, "old_brick_marble", MCGTabs.OLD, SoundType.STONE));
        blocks1.add(new BlockMCGLog("old_rubber_wood", MCGTabs.OLD, SoundType.WOOD));
        blocks16.add(new BlockTranslucent16(Material.GLASS, "old_glass", MCGTabs.OLD, SoundType.GLASS));
        blocks16.add(new BlockTranslucent16(Material.GLASS, "old_glass_bright", MCGTabs.OLD, SoundType.GLASS).setLightLevel(1.0F));
        blocks1.add(new BlockRotate4(Material.ROCK, "old_furnace_on", MCGTabs.OLD, SoundType.STONE));
        blocks1.add(new BlockRotate4(Material.ROCK, "old_furnace_off", MCGTabs.OLD, SoundType.STONE));

        //将所有链表的引用合并到ArrayList
        addArrayList();

    }

    /**
     * 注册方块
     */
    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event) {
        MCGProject.logger.info("MCGProject: registering blocks");
        for (LinkedList<Block> i : arrayList) {
            event.getRegistry().registerAll(i.toArray(new Block[0]));
        }

        RanstoneBlock.initBlock();
        RanstoneComparator.initBlock();
        RanstoneRepeater.initBlock();
        RanstoneTorch.initBlock();
        RanstoneWire.initBlock();
        RanstonePiston.Base.initBlock();
        RanstonePiston.Extension.initBlock();
        RanstonePiston.Moving.initBlock();
        event.getRegistry().registerAll(
                RanstoneBlock.BLOCK,
                RanstoneComparator.BLOCK, RanstoneComparator.BLOCK_N,
                RanstoneRepeater.BLOCK, RanstoneRepeater.BLOCK_N,
                RanstoneTorch.BLOCK, RanstoneTorch.BLOCK_N,
                RanstoneWire.BLOCK,
                RanstonePiston.Base.BLOCK, RanstonePiston.Extension.BLOCK, RanstonePiston.Moving.BLOCK);

        BlockSticker.initBlock();
        event.getRegistry().registerAll(BlockSticker.BLOCK, BlockSticker.BLOCK_LIT);
    }

    /**
     * 注册ItemBlock
     */
    @SubscribeEvent
    public void registerItemBlocks(RegistryEvent.Register<Item> event) {
        MCGProject.logger.info("MCGProject: registering ItemBlocks");
        //遍历所有存储Block的链表，实例化ItemBlock并存入LinkedHashMap
        for (int maxMeta = 0; maxMeta < 16; maxMeta++) {
            if (maxMeta == 0) for (Block b : blocks1) {itemBlocks1.put(b, new ItemMCGBlock(b));}
            else for (Block b : arrayList.get(maxMeta)) {arrayList2.get(maxMeta).put(b, new ItemBlockWithMeta(b));}
        }
        //注册ItemBlock
        for (LinkedHashMap<Block, Item> i : arrayList2) {
            event.getRegistry().registerAll(i.values().toArray(new Item[0]));
        }

        RanstoneBlock.initItem();
        RanstoneComparator.initItem();
        RanstoneRepeater.initItem();
        RanstoneTorch.initItem();
        RanstoneWire.initItem();
        RanstonePiston.Base.initItem();
        event.getRegistry().registerAll(
                RanstoneBlock.ITEM, RanstoneComparator.ITEM,
                RanstoneRepeater.ITEM, RanstoneTorch.ITEM,
                RanstoneWire.ITEM, RanstonePiston.Base.ITEM);

        BlockSticker.initItem();
        event.getRegistry().registerAll(BlockSticker.ITEM, BlockSticker.ITEM_LIT);
    }

    /**
     * 注册ItemBlock对应的模型
     */
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void registerItemBlockModels(ModelRegistryEvent event) {
        MCGProject.logger.info("MCGProject: registering ItemBlock Models");
        //遍历所有LinkedHashMap, 注册ItemBlock对应模型
        for (int maxMeta = 0; maxMeta < 16; maxMeta++) {
            for (Item i : arrayList2.get(maxMeta).values()) {
                setLocation(i, maxMeta);
            }
        }
    }

    //模型注册的执行
    private void setLocation(Item i, int maxMeta) {
        if(maxMeta == 0) {
            ModelLoader.setCustomModelResourceLocation(i, 0,
                    new ModelResourceLocation(Objects.requireNonNull(i.getRegistryName()), "inventory")
            );
        }
        else for (int r = 0; r < maxMeta+1; r++) {
                String blockState = Block.getBlockFromItem(i).getStateFromMeta(r).toString();
                String variantIn = blockState.substring(blockState.indexOf("[")+1, blockState.indexOf("]"));
                ModelLoader.setCustomModelResourceLocation(i, r,
                        new ModelResourceLocation(Objects.requireNonNull(i.getRegistryName()), variantIn));
        }
    }

    private static LinkedList<Block> blocks1 = new LinkedList<>();
    private static LinkedList<Block> blocks2 = new LinkedList<>();
    private static LinkedList<Block> blocks3 = new LinkedList<>();
    private static LinkedList<Block> blocks4 = new LinkedList<>();
    private static LinkedList<Block> blocks5 = new LinkedList<>();
    private static LinkedList<Block> blocks6 = new LinkedList<>();
    private static LinkedList<Block> blocks7 = new LinkedList<>();
    private static LinkedList<Block> blocks8 = new LinkedList<>();
    private static LinkedList<Block> blocks9 = new LinkedList<>();
    private static LinkedList<Block> blocks10 = new LinkedList<>();
    private static LinkedList<Block> blocks11 = new LinkedList<>();
    private static LinkedList<Block> blocks12 = new LinkedList<>();
    private static LinkedList<Block> blocks13 = new LinkedList<>();
    private static LinkedList<Block> blocks14 = new LinkedList<>();
    private static LinkedList<Block> blocks15 = new LinkedList<>();
    private static LinkedList<Block> blocks16 = new LinkedList<>();
    private static LinkedHashMap<Block, Item> itemBlocks1 = new LinkedHashMap<>();
    private static LinkedHashMap<Block, Item> itemBlocks2 = new LinkedHashMap<>();
    private static LinkedHashMap<Block, Item> itemBlocks3 = new LinkedHashMap<>();
    private static LinkedHashMap<Block, Item> itemBlocks4 = new LinkedHashMap<>();
    private static LinkedHashMap<Block, Item> itemBlocks5 = new LinkedHashMap<>();
    private static LinkedHashMap<Block, Item> itemBlocks6 = new LinkedHashMap<>();
    private static LinkedHashMap<Block, Item> itemBlocks7 = new LinkedHashMap<>();
    private static LinkedHashMap<Block, Item> itemBlocks8 = new LinkedHashMap<>();
    private static LinkedHashMap<Block, Item> itemBlocks9 = new LinkedHashMap<>();
    private static LinkedHashMap<Block, Item> itemBlocks10 = new LinkedHashMap<>();
    private static LinkedHashMap<Block, Item> itemBlocks11 = new LinkedHashMap<>();
    private static LinkedHashMap<Block, Item> itemBlocks12 = new LinkedHashMap<>();
    private static LinkedHashMap<Block, Item> itemBlocks13 = new LinkedHashMap<>();
    private static LinkedHashMap<Block, Item> itemBlocks14 = new LinkedHashMap<>();
    private static LinkedHashMap<Block, Item> itemBlocks15 = new LinkedHashMap<>();
    private static LinkedHashMap<Block, Item> itemBlocks16 = new LinkedHashMap<>();
    private static ArrayList<LinkedList<Block>> arrayList = new ArrayList<>();
    private static ArrayList<LinkedHashMap<Block, Item>> arrayList2 = new ArrayList<>();

    private void addArrayList() {
        arrayList.add(blocks1);
        arrayList.add(blocks2);
        arrayList.add(blocks3);
        arrayList.add(blocks4);
        arrayList.add(blocks5);
        arrayList.add(blocks6);
        arrayList.add(blocks7);
        arrayList.add(blocks8);
        arrayList.add(blocks9);
        arrayList.add(blocks10);
        arrayList.add(blocks11);
        arrayList.add(blocks12);
        arrayList.add(blocks13);
        arrayList.add(blocks14);
        arrayList.add(blocks15);
        arrayList.add(blocks16);
        arrayList2.add(itemBlocks1);
        arrayList2.add(itemBlocks2);
        arrayList2.add(itemBlocks3);
        arrayList2.add(itemBlocks4);
        arrayList2.add(itemBlocks5);
        arrayList2.add(itemBlocks6);
        arrayList2.add(itemBlocks7);
        arrayList2.add(itemBlocks8);
        arrayList2.add(itemBlocks9);
        arrayList2.add(itemBlocks10);
        arrayList2.add(itemBlocks11);
        arrayList2.add(itemBlocks12);
        arrayList2.add(itemBlocks13);
        arrayList2.add(itemBlocks14);
        arrayList2.add(itemBlocks15);
        arrayList2.add(itemBlocks16);
    }

}