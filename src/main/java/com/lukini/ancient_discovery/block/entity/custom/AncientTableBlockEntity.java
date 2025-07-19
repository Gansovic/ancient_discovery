package com.lukini.ancient_discovery.block.entity.custom;

import com.lukini.ancient_discovery.block.entity.ModBlockEntities;
import com.lukini.ancient_discovery.knowledge.PlayerKnowledgeCapability;
import com.lukini.ancient_discovery.screen.custom.AncientTableMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class AncientTableBlockEntity extends BlockEntity implements MenuProvider {

    public final ItemStackHandler inventory = new ItemStackHandler(4) {
        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            assert level != null;
            if (!level.isClientSide()) {
                if (slot == 0) {
                    tryGainKnowledge();
                }
                if (slot == 1) {
                    tryTriggerMilestoneIfApplicable();
                }
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };
    private float rotation;


    private void tryTriggerMilestoneIfApplicable() {
        if (!(level instanceof ServerLevel serverLevel)) return;
        Player nearest = serverLevel.getNearestPlayer(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), 5, false);
        if (!(nearest instanceof ServerPlayer serverPlayer)) return;

        ItemStack relicStack = inventory.getStackInSlot(1); // slot de reliquias
        if (relicStack.isEmpty()) return;

        ResourceLocation id = ForgeRegistries.ITEMS.getKey(relicStack.getItem());
        if (id == null) return;

        Level world = serverLevel.getLevel();


        PlayerKnowledgeCapability cap = PlayerKnowledgeCapability.get(serverPlayer);
        cap.tryTriggerMilestone(serverPlayer, world, id.toString());
    }

    private void tryGainKnowledge() {
        if (!(level instanceof ServerLevel serverLevel)) return;
        Player nearest = serverLevel.getNearestPlayer(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), 5, false);
        if (nearest == null) return;

        PlayerKnowledgeCapability knowledgeCap = PlayerKnowledgeCapability.get(nearest);
        ItemStack input = inventory.getStackInSlot(0);

        if (input.isEmpty()) return;

        // Por ahora, cada papiro da 5 de conocimiento
        if (input.is(ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse("ancient_discovery:papyrus")))) {
            int gained = 5;
            knowledgeCap.addKnowledge(gained);
            inventory.setStackInSlot(0, ItemStack.EMPTY);
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);

            nearest.displayClientMessage(Component.literal(
                    "§6[Ancient Table]§r Ganaste §b" + gained + "§r de conocimiento por entregar §e" +
                            input.getHoverName().getString() + "§r. Total: §a" + knowledgeCap.getKnowledge() +
                            "§r. Milestone: §d" + knowledgeCap.getCurrentMilestone()
            ), false);
        }
    }


    public AncientTableBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ANCIENT_TABLE_BE.get(), pPos, pBlockState);
    }


    public float getRenderingRotation() {
        rotation += 0.5f;
        if(rotation >= 360) {
            rotation = 0;
        }
        return rotation;
    }

    public void clearContents() {
        inventory.setStackInSlot(0, ItemStack.EMPTY);
    }

    public void drops() {
        SimpleContainer inv = new SimpleContainer(inventory.getSlots());
        for(int i = 0; i < inventory.getSlots(); i++) {
            inv.setItem(i, inventory.getStackInSlot(i));
        }

        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag pTag, HolderLookup.@NotNull Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        pTag.put("inventory", inventory.serializeNBT(pRegistries));
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag pTag, HolderLookup.@NotNull Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        inventory.deserializeNBT(pRegistries, pTag.getCompound("inventory"));
    }



    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }


    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal("Ancient Table");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pPlayerInventory, @NotNull Player pPlayer) {
        return new AncientTableMenu(pContainerId, pPlayerInventory, this);
    }
}
