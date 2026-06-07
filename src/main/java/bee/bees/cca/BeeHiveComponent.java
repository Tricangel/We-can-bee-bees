package bee.bees.cca;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v8.component.CardinalComponent;

public class BeeHiveComponent implements CardinalComponent {
    private final Player player;
    private BlockPos pos;

    public BeeHiveComponent(Player player) {
        this.player = player;
    }

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }

    public BlockPos getPos() {
        return pos;
    }

    public boolean hasPos() {
        return pos != null;
    }

    @Override
    public void readData(ValueInput readView) {
        if (readView.read("pos", BlockPos.CODEC).isPresent()) {
            pos = readView.read("pos", BlockPos.CODEC).get();
        }
    }

    @Override
    public void writeData(ValueOutput writeView) {
        if (hasPos()) {
            writeView.store("pos", BlockPos.CODEC, pos);
        }
    }
}
