package org.sillythewanderer.metalmind;

import net.fabricmc.api.ModInitializer;
import org.sillythewanderer.metalmind.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Metalmind implements ModInitializer {
    public static final String MOD_ID = "metalmind";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {

        ModItems.registerModItems();
    }
}
