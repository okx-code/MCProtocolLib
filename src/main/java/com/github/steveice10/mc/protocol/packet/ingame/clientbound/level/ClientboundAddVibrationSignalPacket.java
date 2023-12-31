package com.github.steveice10.mc.protocol.packet.ingame.clientbound.level;

import com.github.steveice10.mc.protocol.data.game.Identifier;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position;
import com.github.steveice10.mc.protocol.data.game.level.vibration.BlockVibrationSource;
import com.github.steveice10.mc.protocol.data.game.level.vibration.EntityVibrationSource;
import com.github.steveice10.mc.protocol.data.game.level.vibration.VibrationSource;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import com.github.steveice10.packetlib.packet.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.With;

import java.io.IOException;

@Data
@With
@AllArgsConstructor
public class ClientboundAddVibrationSignalPacket implements Packet {
    private final @NonNull Position origin;
    private final @NonNull VibrationSource destination;
    private final int arrivalInTicks;

    public ClientboundAddVibrationSignalPacket(NetInput in) throws IOException {
        this.origin = Position.read(in);
        String identifier = Identifier.formalize(in.readString());
        switch (identifier) {
            case "minecraft:block":
                this.destination = BlockVibrationSource.read(in);
                break;
            case "minecraft:entity":
                this.destination = EntityVibrationSource.read(in);
                break;
            default:
                throw new IllegalStateException();
        }
        this.arrivalInTicks = in.readVarInt();
    }

    @Override
    public void write(NetOutput out) throws IOException {
        Position.write(out, this.origin);
        if (this.destination instanceof BlockVibrationSource) {
            out.writeString("minecraft:block");
            BlockVibrationSource.write(out, (BlockVibrationSource) this.destination);
        } else if (this.destination instanceof EntityVibrationSource) {
            out.writeString("minecraft:entity");
            EntityVibrationSource.write(out, (EntityVibrationSource) this.destination);
        }
        out.writeVarInt(this.arrivalInTicks);
    }
}
