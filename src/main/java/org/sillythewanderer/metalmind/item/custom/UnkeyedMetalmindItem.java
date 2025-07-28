package org.sillythewanderer.metalmind.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.sillythewanderer.metalmind.Metalmind;

import java.util.List;

import static org.sillythewanderer.metalmind.item.ModItemGroup.METALMIND_ITEMGROUP;

public class UnkeyedMetalmindItem extends Item {
    public UnkeyedMetalmindItem(Settings settings) {
        super(settings.fireproof());
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {

        ItemStack stack = new ItemStack(this);
        NbtCompound TimeIO_NBT = stack.getOrCreateSubNbt("TimeIO_NBT");

        // on every n'th tick will the effects be applied
        TimeIO_NBT.putInt("fillRate", 20);
        TimeIO_NBT.putInt("tapRate", 20);


        stacks.add(stack);

        super.appendStacks(group, stacks);
    }

    // this function causes the ticks to effect the item.
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient) {

            // we add a "living entity" bit here so that we may apply buffs and debuffs to our entity.
            LivingEntity player = (LivingEntity) entity;

            // the main component that permits all this to work.
            NbtCompound TimeIO_NBT = stack.getOrCreateSubNbt("TimeIO_NBT");

            // on every n'th tick will the effects be applied

            int fillRate = TimeIO_NBT.getInt("fillRate");

            int tapRate = TimeIO_NBT.getInt("tapRate");



            if (world.getTime() % fillRate == 0) {

                // depending upon the "use mode" of the metalmind, different things will happen.
                switch (TimeIO_NBT.getInt("useMode")) {

                    // mode 0 means that the metalmind is not active.
                    case 0:
                        if (TimeIO_NBT.getInt("storedTime") < 622080000) {
                            TimeIO_NBT.putInt("storedTime", TimeIO_NBT.getInt("storedTime"));
                        }
                        if (TimeIO_NBT.getInt("storedTime") < 0) {
                            TimeIO_NBT.putInt("storedTime", 0);
                        }
                        break;

                        // mode 1 means "filling" the metalmind.
                    case 1:
                        if (TimeIO_NBT.getInt("storedTime") < 622080000) {
                            TimeIO_NBT.putInt("storedTime", TimeIO_NBT.getInt("storedTime") + 1);
                            entity.damage(DamageSource.GENERIC, 1);
                        }
                        if (TimeIO_NBT.getInt("storedTime") < 0) {
                            TimeIO_NBT.putInt("storedTime", 0);
                        }
                        break;

                        // case 2 means "tapping" the metalmind.
                    case 2:
                        if (!(player.getHealth() == player.getMaxHealth())) {
                            if (TimeIO_NBT.getInt("storedTime") > 0) {
                                player.heal(1);
                            }
                            if (TimeIO_NBT.getInt("storedTime") < 622080000) {
                                TimeIO_NBT.putInt("storedTime", TimeIO_NBT.getInt("storedTime") - 1);
                            }
                            if (TimeIO_NBT.getInt("storedTime") < 0) {
                                TimeIO_NBT.putInt("storedTime", 0);
                            }
                            break;
                        }
                }
            }

            if (world.getTime() % tapRate == 0) {

            }

        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    // displaying information regarding stored time, and use mode
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {

        NbtCompound timeData = stack.getOrCreateSubNbt("TimeIO_NBT");

        int storedTime = timeData.getInt("storedTime");

        int useMode = timeData.getInt("useMode");

        int fillRate = timeData.getInt("fillRate");
        int tapRate = timeData.getInt("tapRate");

        tooltip.add(Text.literal("To fill the metalmind you shift-right-click"));
        tooltip.add(Text.literal("To tap it you right click."));
        tooltip.add(Text.literal("To stop tapping or filling, right click again"));

        tooltip.add(Text.literal("Stored health: " + String.valueOf(storedTime)));

        tooltip.add(Text.literal("mode: " + String.valueOf(useMode)));

        tooltip.add(Text.literal("fill rate (every x ticks): " + String.valueOf(fillRate)));

        tooltip.add(Text.literal("tap rate (every x ticks): " + String.valueOf(tapRate)));

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

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        NbtCompound TimeIO_NBT = stack.getOrCreateSubNbt("TimeIO_NBT");


        TimeIO_NBT.putInt("tapRate", 1);
        TimeIO_NBT.putInt("fillRate", 1);

        return super.onStackClicked(stack, slot, clickType, player);
    }
}
