package org.sillythewanderer.metalmind.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.sillythewanderer.metalmind.Metalmind;

public class ModItemGroup {
    public static final ItemGroup METALMIND_ITEMGROUP = FabricItemGroupBuilder.build(
            new Identifier(Metalmind.MOD_ID, "metalmind_itemgroup"), () -> new ItemStack(ModItems.HARMONIUM));
}
