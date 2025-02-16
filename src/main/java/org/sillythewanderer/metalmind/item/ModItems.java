package org.sillythewanderer.metalmind;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final Item HARMONIUM = registerItem("harmonium",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));



    public static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Metalmind.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Metalmind.LOGGER.debug("Registering Mod Items for " + Metalmind.MOD_ID);
    }

}
