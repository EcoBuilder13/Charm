package svenhjol.charm.base;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import svenhjol.charm.Charm;
import svenhjol.charm.base.message.*;
import svenhjol.meson.MesonLoader;
import svenhjol.meson.handler.PacketHandler;
import svenhjol.meson.handler.RegistryHandler;

public class CharmLoader extends MesonLoader
{
    public CharmLoader()
    {
        super(Charm.MOD_ID);
    }

    @Override
    public void setup(FMLCommonSetupEvent event)
    {
        super.setup(event);

        PacketHandler.HANDLER.registerMessage(0, MessageBookshelfInteract.class, MessageBookshelfInteract::encode, MessageBookshelfInteract::decode, MessageBookshelfInteract.Handler::handle);
        PacketHandler.HANDLER.registerMessage(1, MessageCakeImbue.class, MessageCakeImbue::encode, MessageCakeImbue::decode, MessageCakeImbue.Handler::handle);
        PacketHandler.HANDLER.registerMessage(2, MessageCrateInteract.class, MessageCrateInteract::encode, MessageCrateInteract::decode, MessageCrateInteract.Handler::handle);
        PacketHandler.HANDLER.registerMessage(3, MessageGlowing.class, MessageGlowing::encode, MessageGlowing::decode, MessageGlowing.Handler::handle);
        PacketHandler.HANDLER.registerMessage(4, MessageMagneticPickup.class, MessageMagneticPickup::encode, MessageMagneticPickup::decode, MessageMagneticPickup.Handler::handle);

        RegistryHandler.registerSound(CharmSounds.BOOKSHELF_CLOSE);
        RegistryHandler.registerSound(CharmSounds.BOOKSHELF_OPEN);
        RegistryHandler.registerSound(CharmSounds.FUMAROLE_BUBBLING);
        RegistryHandler.registerSound(CharmSounds.FUMAROLE_ERUPT);
        RegistryHandler.registerSound(CharmSounds.HOMING);
        RegistryHandler.registerSound(CharmSounds.WOOD_SMASH);
    }
}
