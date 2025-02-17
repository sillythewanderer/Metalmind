package org.sillythewanderer.metalmind.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.sillythewanderer.metalmind.Metalmind;
import org.sillythewanderer.metalmind.item.custom.ExplodingFoodItem;

public class ModItems {

    public static final Item HARMONIUM = registerItem("harmonium",
            new ExplodingFoodItem(new FabricItemSettings().group(ModItemGroup.METALMIND_ITEMGROUP).food(new FoodComponent.Builder().alwaysEdible().build())));



    public static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Metalmind.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Metalmind.LOGGER.debug("Registering Mod Items for " + Metalmind.MOD_ID);
    }

}
