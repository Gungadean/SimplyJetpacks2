package tomson124.simplyjetpacks.sound;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tomson124.simplyjetpacks.handlers.ClientJetpackHandler;
import tomson124.simplyjetpacks.handlers.RegistryHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class JetpackSound extends AbstractTickableSoundInstance {

    private static final Map<Integer, JetpackSound> PLAYING_FOR = Collections.synchronizedMap(new HashMap<>());
    private final Player player;
    private int fadeOut = -1;

    public JetpackSound(Player player) {
//        super(SJSounds.JETPACK, SoundSource.PLAYERS, RandomSource.create());
        // TODO: test this
        super(RegistryHandler.JETPACK_SOUND.get(), SoundSource.PLAYERS, RandomSource.create());
        this.player = player;
        this.looping = true;
        PLAYING_FOR.put(player.getId(), this);
    }

    public static boolean playing(int entityId) {
        return PLAYING_FOR.containsKey(entityId) && PLAYING_FOR.get(entityId) != null && !PLAYING_FOR.get(entityId).isStopped();
    }

    @Override
    public void tick() {
        if (this.player.isSpectator() || this.player.getAbilities().flying) {
            this.stop();
        }
        Vec3 pos = this.player.position();
        this.x = (float) pos.x();
        this.y = (float) pos.y();// - 10;
        this.z = (float) pos.z();
        if (this.fadeOut < 0 && !ClientJetpackHandler.isFlying(this.player)) {
            this.fadeOut = 0;
            synchronized (PLAYING_FOR) {
                PLAYING_FOR.remove(this.player.getId());
            }
        } else
        if (this.fadeOut >= 5) {
            this.stop();
        } else
        if (this.fadeOut >= 0) {
            this.volume = 1.0F - this.fadeOut / 5F;
            this.fadeOut++;
        }
    }
}
