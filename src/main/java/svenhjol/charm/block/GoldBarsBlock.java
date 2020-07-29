package svenhjol.charm.block;

import net.minecraft.block.Blocks;
import net.minecraft.block.PaneBlock;
import net.minecraft.item.ItemGroup;
import svenhjol.meson.MesonModule;
import svenhjol.meson.block.IMesonBlock;

public class GoldBarsBlock extends PaneBlock implements IMesonBlock {
    private MesonModule module;

    public GoldBarsBlock(MesonModule module) {
        super(Properties.from(Blocks.IRON_BARS));
        this.module = module;
        this.register(module, "gold_bars");
    }

    @Override
    public ItemGroup getItemGroup() {
        return ItemGroup.DECORATIONS;
    }

    @Override
    public boolean enabled() {
        return module.enabled;
    }
}
