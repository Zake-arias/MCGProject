package moe.gensoukyo.mcgproject.common.item.cart;


import moe.gensoukyo.mcgproject.common.creativetab.MCGTabs;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.item.Item;


public class ItemTestSword extends Item {
    public ItemTestSword() {
        this.setMaxStackSize(1);
        this.setCreativeTab(MCGTabs.FANTASY);
        this.setRegistryName(MCGProject.ID, "test_sword");
        this.setTranslationKey(MCGProject.ID + "." + "test_sword");
    }
}

