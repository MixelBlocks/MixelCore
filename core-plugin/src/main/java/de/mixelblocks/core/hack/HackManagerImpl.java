package de.mixelblocks.core.hack;

import de.mixelblocks.core.MixelCorePlugin;

import java.util.HashMap;

public class HackManagerImpl implements HackManager {

    private final HashMap<Class<? extends Hack>, Hack> activeHacks = new HashMap<>();

    @Override
    public boolean register(Class<? extends Hack> registration, Hack hack) {
        if(activeHacks.get(registration) != null) return false;
        boolean hackyAnnotated = registration.isAnnotationPresent(Hacky.class);
        if(!hackyAnnotated) return false;
        Hacky hacky = registration.getAnnotation(Hacky.class);
        if(!hacky.use()) return false;

        String reason = hacky.reason();
        String since = hacky.since();

        StringBuilder log = new StringBuilder();
        log.append("Registering Hacky solution " + registration.getName() + "\n");
        if(reason != null && reason != "") {
            log.append("Reason: " + reason + "\n");
        }
        if(since != null && since != "") {
            log.append("Since: " + since + "\n");
        }

        try {
            hack.activate();
            activeHacks.put(registration, hack);
            MixelCorePlugin.getInstance().getLogger().warning("\n" + log);
        } catch(Exception x) {
            MixelCorePlugin.getInstance().getLogger().warning(x.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public Hack getRegistered(Class<? extends Hack> registration) {
        return activeHacks.get(registration);
    }

    @Override
    public Hack unregister(Class<? extends Hack> registration) {
        Hack hack = activeHacks.get(registration) ;
        try { hack.unregister(); } catch(Exception e) { return null; }
        return hack;
    }
}
