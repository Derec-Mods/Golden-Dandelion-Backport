package com.goldendandelion.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * CommonProxy — "The ability to speak does not make you intelligent."
 *
 * Handles server-side (and shared) initialisation logic.
 * Client-specific concerns are handled by {@link ClientProxy}, which overrides
 * these methods — much like how Dooku replaced Qui-Gon as Palpatine's apprentice.
 */
public class CommonProxy {

    /**
     * preInit — "Your focus determines your reality."
     *
     * Called before the world loads.  Ideal time to register blocks and items.
     *
     * @param quiGonJinnEvent the pre-init event (wise, but brief — just like Qui-Gon)
     */
    public void preInit(FMLPreInitializationEvent quiGonJinnEvent) {
        // Server side: nothing extra to do here
        // "There's always a bigger fish." — and also always a bigger init phase
    }

    /**
     * init — "Now this is podracing!"
     *
     * Core initialisation: register recipes, event handlers, etc.
     *
     * @param anakinPodracingEvent the init event — full of dangerous energy
     */
    public void init(FMLInitializationEvent anakinPodracingEvent) {
        // Server side: nothing extra to do here
        // "Yippeee!" — Anakin, discovering Gradle compiles correctly
    }

    /**
     * postInit — "I have a bad feeling about this."
     *
     * Final pass; useful for inter-mod compatibility.
     * By this point the Galaxy has already signed the Ruusan Reformation.
     *
     * @param itsATrapEvent the post-init event (Admiral Ackbar would be nervous)
     */
    public void postInit(FMLPostInitializationEvent itsATrapEvent) {
        // Server side: nothing extra to do here
    }
}
