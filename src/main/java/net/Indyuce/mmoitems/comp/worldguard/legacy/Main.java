package net.Indyuce.mmoitems.comp.worldguard.legacy;

import net.Indyuce.mmoitems.MMOItems;
import net.mmogroup.mmolib.MMOLib;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class Main extends JavaPlugin {
    private WorldGuardHook hook;

    public void onLoad() {
        if (MMOLib.plugin.getVersion().isStrictlyHigher(1, 12)) {
            getLogger().log(Level.WARNING, "MMOItems LegacyWG shall not be used on >1.12");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        hook = new WorldGuardHook();
        getLogger().log(Level.INFO, "Successfully registered " + hook.getFlags().size() + " flags");
    }

    public void onEnable() {
        MMOItems.plugin.setFlags(hook);
        getLogger().log(Level.INFO, "Successfully hooked onto MMOItems");
    }
}
