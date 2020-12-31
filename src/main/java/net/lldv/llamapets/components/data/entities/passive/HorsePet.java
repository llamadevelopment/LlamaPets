package net.lldv.llamapets.components.data.entities.passive;

import cn.nukkit.entity.passive.EntityHorse;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import net.lldv.llamapets.components.data.entities.Pet;

public class HorsePet extends Pet {

    public HorsePet(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
        this.movementSpeed = 1.0f;
        this.distance = 5;
    }

    @Override
    public int getNetworkId() {
        return EntityHorse.NETWORK_ID;
    }

    @Override
    public float getWidth() {
        return 1.3965f;
    }

    @Override
    public float getHeight() {
        return 1.6f;
    }

}
