package net.lldv.llamapets.components.data.entities.hostile;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.entity.mob.EntityVex;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import net.lldv.llamapets.components.data.entities.Pet;

public class VexPet extends Pet {

    public VexPet(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
        this.setMovementSpeed(1.0f);
    }

    public int getNetworkId() {
        return EntityVex.NETWORK_ID;
    }

    @Override
    public float getWidth() {
        return .4f;
    }

    @Override
    public float getHeight() {
        return .8f;
    }

    @Override
    public void walkTo(Vector3 target) {
        if (this.isClosed()) {
            return;
        }

        if (this.isImmobile()) {
            this.setImmobile(false);
        }

        final double diffX = target.getX() - this.getX();
        final double diffZ = target.getZ() - this.getZ();
        final double diffY = target.getY() - this.getY();
        final double diff = Math.abs(diffX) + Math.abs(diffZ) + Math.abs(diffY);

        final boolean isJumping = this.isJumping();
        final boolean isFalling = !isJumping && !this.isGrounded();

        this.motionX = 0.0f;
        this.motionZ = 0.0f;

        if (!isFalling) {
            this.motionY = isJumping ? this.motionY : 0;
        } else {
            this.motionY = this.motionY > .01 ? 0 : this.motionY - .32;
        }

        if (!isGrounded() && this.following.getY() + 1 > this.getY()) {
            this.motionY = .32;
        }

        if (diff > 4) {
            if (diff > 40) this.teleport(following.getPosition());
            this.motionX = this.movementSpeed * 0.5 * (diffX / diff);
            this.motionZ = this.movementSpeed * 0.5 * (diffZ / diff);
        }

        this.lookAt(target);
        this.move(this.motionX, this.motionY, this.motionZ);
        this.updateMovement();
    }

    private boolean isGrounded() {
        Block below = this.getLevel().getBlock(this.getFloorX(), this.getFloorY() - 1, this.getFloorZ());
        Block below2 = this.getLevel().getBlock(this.getFloorX(), this.getFloorY() - 2, this.getFloorZ());
        return below.getId() == BlockID.AIR && below2.getId() != BlockID.AIR;
    }

}
