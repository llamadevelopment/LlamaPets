package net.lldv.llamapets.components.data.entities.hostile;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.entity.mob.EntityGhast;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import net.lldv.llamapets.components.data.entities.Pet;

public class GhastPet extends Pet {

    public GhastPet(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
        this.setMovementSpeed(1.0f);
        this.distance = 11;
    }

    public int getNetworkId() {
        return EntityGhast.NETWORK_ID;
    }

    @Override
    public float getWidth() {
        return 4.0f;
    }

    @Override
    public float getHeight() {
        return 4.0f;
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

        if (!isGrounded() && this.following.getY() + 5 > this.getY()) {
            this.motionY = .32;
        }

        if (diff > this.distance) {
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
        Block below3 = this.getLevel().getBlock(this.getFloorX(), this.getFloorY() - 3, this.getFloorZ());
        Block below4 = this.getLevel().getBlock(this.getFloorX(), this.getFloorY() - 4, this.getFloorZ());
        Block below5 = this.getLevel().getBlock(this.getFloorX(), this.getFloorY() - 5, this.getFloorZ());
        return below.getId() == BlockID.AIR && below2.getId() == BlockID.AIR && below3.getId() == BlockID.AIR && below4.getId() == BlockID.AIR && below5.getId() != BlockID.AIR;
    }

}
