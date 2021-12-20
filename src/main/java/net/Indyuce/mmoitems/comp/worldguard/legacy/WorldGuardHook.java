package net.Indyuce.mmoitems.comp.worldguard.legacy;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.comp.flags.FlagPlugin;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class WorldGuardHook implements FlagPlugin {
    private final WorldGuardPlugin worldguard;
    private final Map<CustomFlag, StateFlag> flags = new HashMap<>();

    public WorldGuardHook() {
        worldguard = (WorldGuardPlugin) MMOItems.plugin.getServer().getPluginManager().getPlugin("WorldGuard");

        FlagRegistry registry = worldguard.getFlagRegistry();
        for (CustomFlag customFlag : CustomFlag.values()) {
            StateFlag flag = new StateFlag(customFlag.getPath(), true);
            try {
                registry.register(flag);
                flags.put(customFlag, flag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Collection<StateFlag> getFlags() {
        return flags.values();
    }

    @Override
    public boolean isPvpAllowed(Location loc) {
        ApplicableRegionSet set = worldguard.getRegionContainer().createQuery().getApplicableRegions(loc);
        return set.queryState(null, DefaultFlag.PVP) != StateFlag.State.DENY;
    }

    @Override
    public boolean isFlagAllowed(Location loc, CustomFlag customFlag) {
        ApplicableRegionSet regions = worldguard.getRegionContainer().createQuery().getApplicableRegions(loc);
        return regions.queryValue(null, flags.get(customFlag)) != StateFlag.State.DENY;
    }

    @Override
    public boolean isFlagAllowed(Player player, CustomFlag customFlag) {
        ApplicableRegionSet regions = worldguard.getRegionContainer().createQuery().getApplicableRegions(player.getLocation());
        return regions.queryValue(worldguard.wrapPlayer(player), flags.get(customFlag)) != StateFlag.State.DENY;
    }

}
