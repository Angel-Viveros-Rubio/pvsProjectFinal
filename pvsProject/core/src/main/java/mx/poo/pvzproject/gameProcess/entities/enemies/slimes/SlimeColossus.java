package mx.poo.pvzproject.gameProcess.entities.enemies.slimes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import mx.poo.pvzproject.gameProcess.entities.enemies.Enemy;
import mx.poo.pvzproject.ui.utils.Assets;

/**
 * Enemigo tipo jefe (Slime Coloso).
 *
 * <p>
 * El Slime Colossus es una unidad masiva con una cantidad de vida
 * extremadamente alta, pero movimiento muy lento. Actúa como
 * un "tanque" definitivo o mini-jefe.
 * </p>
 *
 * <p>
 * Estadísticas:
 * </p>
 * <ul>
 *     <li>Vida: 600 (Muy alta)</li>
 *     <li>Velocidad: 15 (Muy lenta)</li>
 * </ul>
 *
 * @author SmallJunior
 * @version 1.0
 */
public class SlimeColossus extends Enemy {

    /**
     * Constructor del Slime Coloso.
     *
     * @param x posición inicial en X
     * @param y posición inicial en Y
     */
    public SlimeColossus(float x, float y) {
        super(x, y, 600, 15f);
    }

    @Override
    protected void loadAnimation() {
        Array<TextureRegion> frames = new Array<>();
        for (Texture frame : Assets.colossusStaticFrames) {
            frames.add(new TextureRegion(frame));
        }
        animation = new Animation<>(0.15f, frames, Animation.PlayMode.LOOP);
    }

    @Override
    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        float scale = 0.1f;
        float width = currentFrame.getRegionWidth() * scale;
        float height = currentFrame.getRegionHeight() * scale;
        batch.draw(currentFrame, x - width / 2f, y - height / 2f, width, height);
    }
}
