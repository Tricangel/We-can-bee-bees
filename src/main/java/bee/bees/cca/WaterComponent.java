package bee.bees.cca;

import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;
import org.ladysnake.cca.api.v8.component.CardinalComponent;

public class WaterComponent implements CommonTickingComponent {
    public int water = 0;

    @Override
    public void readData(ValueInput readView) {
        water = readView.getIntOr("water", 0);
    }

    @Override
    public void writeData(ValueOutput writeView) {
        writeView.putInt("water", water);
    }

    @Override
    public void tick() {
        water--;
    }
}
