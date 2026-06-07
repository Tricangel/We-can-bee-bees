package bee.bees.registry;

import bee.bees.Bees;
import bee.bees.cca.BeeHiveComponent;
import bee.bees.cca.WaterComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;

public class ModEntityComponents implements EntityComponentInitializer {
    public static ComponentKey<BeeHiveComponent> BEEHIVE =
            ComponentRegistry.getOrCreate(Identifier.fromNamespaceAndPath(Bees.MOD_ID, "beehive"), BeeHiveComponent.class);

    public static ComponentKey<WaterComponent> WATER =
            ComponentRegistry.getOrCreate(Identifier.fromNamespaceAndPath(Bees.MOD_ID, "water"), WaterComponent.class);


    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry entityComponentFactoryRegistry) {

        entityComponentFactoryRegistry.registerFor(Player.class, BEEHIVE, BeeHiveComponent::new);

        entityComponentFactoryRegistry.registerFor(Player.class, WATER, _ -> new WaterComponent());


    }
}
