package me.alpho320.fabulous.bosses.skills.revenge;

import me.alpho320.fabulous.bosses.Main;
import me.alpho320.fabulous.bosses.extension.Skill;
import me.alpho320.fabulous.bosses.util.SoundUtil;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Revenge extends Skill {

    Map<LivingEntity, Boolean> timeMap = new HashMap<>();

    public Revenge(Main main) {
        super(main);
    }

    @Override
    public void use(LivingEntity entity, long tick) {
        if(entity.isDead()) {
            timeMap.remove(entity);
            return;
        }

        if(!timeMap.containsKey(entity))
            timeMap.put(entity, false);

        if(entity.getHealth() <= entity.getMaxHealth()/2) {
            if(!timeMap.get(entity)) {
                entity.getNearbyEntities(20, 20, 20).stream().filter(e -> e instanceof Player).map(e -> (Player) e).forEach(p -> SoundUtil.sendSound(p, Sound.BLAZE_DEATH));

                IntStream.range(0, 15).forEach(i -> entity.getWorld().spawnCreature(entity.getLocation().clone().add(i, 0, i), EntityType.ZOMBIE));
                timeMap.put(entity, true);
            }
        }

    }

    @Override
    public String getName() {
        return "revenge";
    }
}