package org.sillythewanderer.metalmind.item.custom;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.jar.Attributes;

public class BandsOfMourningItem extends Item {
    public BandsOfMourningItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient()) {
            if(user.isSneaking()) {
                user.setMovementSpeed(5);
            }
            else {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 200, 15));
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 400));
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 200, 3));
            }
        }

        return super.use(world, user, hand);
    }
}
