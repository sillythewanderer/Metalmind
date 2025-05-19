package org.sillythewanderer.metalmind.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class UnkeyedMetalmindItem extends Item {
    public UnkeyedMetalmindItem(Settings settings) {
        super(settings.fireproof());
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient) {

            LivingEntity player = (LivingEntity) entity;

            NbtCompound TimeIO_NBT = stack.getOrCreateSubNbt("TimeIO_NBT");

            TimeIO_NBT.putInt("rate", 20);

            int rate = TimeIO_NBT.getInt("rate");

            if (world.getTime() % rate == 0) {

                switch (TimeIO_NBT.getInt("useMode")) {
                    case 0:
                        if (TimeIO_NBT.getInt("storedTime") < 622080000) {
                            TimeIO_NBT.putInt("storedTime", TimeIO_NBT.getInt("storedTime"));
                        }
                        if (TimeIO_NBT.getInt("storedTime") < 0) {
                            TimeIO_NBT.putInt("storedTime", 0);
                        }
                        break;
                    case 1:
                        if (TimeIO_NBT.getInt("storedTime") < 622080000) {
                            TimeIO_NBT.putInt("storedTime", TimeIO_NBT.getInt("storedTime") + 20);
                            entity.damage(DamageSource.GENERIC, 1);
                        }
                        if (TimeIO_NBT.getInt("storedTime") < 0) {
                            TimeIO_NBT.putInt("storedTime", 0);
                        }
                        break;

                    case 2:
                        if (!(player.getHealth() == player.getMaxHealth())) {
                            if (TimeIO_NBT.getInt("storedTime") < 622080000) {
                                TimeIO_NBT.putInt("storedTime", TimeIO_NBT.getInt("storedTime") - 20);
                            }
                            if (TimeIO_NBT.getInt("storedTime") < 0) {
                                TimeIO_NBT.putInt("storedTime", 0);
                            }
                            if (TimeIO_NBT.getInt("storedTime") > 0) {
                                player.heal(1);
                            }
                            break;
                        }
                }
            }
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {

        NbtCompound timeData = stack.getOrCreateSubNbt("TimeIO_NBT");

        int storedTime = timeData.getInt("storedTime");

        int storedSeconds = storedTime / 20;

        int useMode = timeData.getInt("useMode");


        tooltip.add(Text.literal(String.valueOf(storedSeconds)));

        tooltip.add(Text.literal(String.valueOf(useMode)));

        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            ItemStack itemStack = user.getStackInHand(hand);

            NbtCompound TimeIO_NBT = itemStack.getOrCreateSubNbt("TimeIO_NBT");

            if (!user.isSneaking()) {

                switch (TimeIO_NBT.getInt("useMode")) {
                    case 0:
                        TimeIO_NBT.putInt("useMode", 2);
                        break;
                    case 2:
                        TimeIO_NBT.putInt("useMode", 0);
                        break;
                    case 1:
                        TimeIO_NBT.putInt("useMode", 2);
                    default:
                        TimeIO_NBT.putInt("useMode", 0);
                }

            }
            else {

                switch (TimeIO_NBT.getInt("useMode")) {
                    case 0:
                        TimeIO_NBT.putInt("useMode", 1);
                        break;
                    case 1:
                        TimeIO_NBT.putInt("useMode", 0);
                        break;
                    case 2:
                        TimeIO_NBT.putInt("useMode", 1);
                    default:
                        TimeIO_NBT.putInt("useMode", 0);
                }

            }

        }
        return super.use(world, user, hand);
    }
}
