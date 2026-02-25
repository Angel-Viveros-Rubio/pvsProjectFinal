package mx.poo.pvzproject.gameProcess.entities.enemies.slimes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import mx.poo.pvzproject.gameProcess.entities.enemies.Enemy;
import mx.poo.pvzproject.ui.utils.Assets;

/**
 * Enemigo de movimiento rápido (Slime Rápido).
 *
 * <p>
 * El Slime Rápido sacrifica resistencia a cambio de una mayor
 * velocidad de desplazamiento, lo que lo convierte en una amenaza
 * para defensas lentas o mal posicionadas.
 * </p>
 *
 * <p>
 * Estadísticas:
 * </p>
 * <ul>
 *     <li>Vida: 120 (Media)</li>
 *     <li>Velocidad: 40 (Alta)</li>
 * </ul>
 *
 * @author SmallJunior
 * @version 1.0
 */
public class SlimeRapido extends Enemy {

    /**
     * Constructor del Slime Rápido.
     *
     * @param x posición inicial en X
     * @param y posición inicial en Y
     */
    public SlimeRapido(float x, float y) {
        super(x, y, 120, 40f);
    }

    @Override
    protected void loadAnimation() {
        Array<TextureRegion> frames = new Array<>();
        for (Texture frame : Assets.slimeDivisorFrames) {
            frames.add(new TextureRegion(frame));
        }
        animation = new Animation<>(0.15f, frames, Animation.PlayMode.LOOP);
    }

    @Override
    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        float scale = 5f;
        float width = currentFrame.getRegionWidth() * scale;
        float height = currentFrame.getRegionHeight() * scale;
        batch.draw(currentFrame, x - width / 2f, y - height / 2f, width, height);
    }
}
