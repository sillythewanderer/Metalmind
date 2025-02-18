package org.sillythewanderer.metalmind.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class GainFeruchemyItem extends Item {
    public GainFeruchemyItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if(!world.isClient()) {
            world.createExplosion(user,user.getX(),user.getY(),user.getZ(), 16, Explosion.DestructionType.NONE);
        }
        return super.finishUsing(stack, world, user);
    }

}
