package svenhjol.charm.module;

import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import svenhjol.charm.Charm;
import svenhjol.charm.client.BatBucketClient;
import svenhjol.charm.item.BatBucketItem;
import svenhjol.charm.base.CharmModule;
import svenhjol.charm.base.helper.ItemNBTHelper;
import svenhjol.charm.base.helper.PlayerHelper;
import svenhjol.charm.base.iface.Config;
import svenhjol.charm.base.iface.Module;

@Module(mod = Charm.MOD_ID, description = "Right-click a bat with a bucket to capture it. Right-click again to release it and locate entities around you.")
public class BatBuckets extends CharmModule {
    public static BatBucketItem BAT_BUCKET_ITEM;
    public static BatBucketClient client = null;

    public static final ResourceLocation MSG_CLIENT_SET_GLOWING = new ResourceLocation(Charm.MOD_ID, "client_set_glowing");

    @Config(name = "Glowing time", description = "Number of seconds that entities will receive the glowing effect.")
    public static int glowingTime = 10;

    @Config(name = "Viewing range", description = "Range (in blocks) in which entities will glow.")
    public static int glowingRange = 24;

    @Override
    public void register() {
        BAT_BUCKET_ITEM = new BatBucketItem(this);
    }

    @Override
    public void init() {
        UseEntityCallback.EVENT.register(this::tryCapture);
    }

    @Override
    public void clientInit() {
        client = new BatBucketClient(this);
    }

    private ActionResult tryCapture(PlayerEntity player, World world, Hand hand, Entity entity, EntityHitResult hitResult) {
        if (!entity.world.isClient
            && entity instanceof BatEntity
            && ((BatEntity)entity).getHealth() > 0
        ) {
            BatEntity bat = (BatEntity)entity;
            ItemStack held = player.getStackInHand(hand);

            if (held.isEmpty() || held.getItem() != Items.BUCKET)
                return ActionResult.PASS;

            ItemStack batBucket = new ItemStack(BAT_BUCKET_ITEM);
            CompoundNBT tag = new CompoundNBT();
            ItemNBTHelper.setCompound(batBucket, BatBucketItem.STORED_BAT, bat.toTag(tag));

            if (held.getCount() == 1) {
                player.setStackInHand(hand, batBucket);
            } else {
                held.decrement(1);
                PlayerHelper.addOrDropStack(player, batBucket);
            }

            player.swingHand(hand);
            entity.remove();
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }
}
