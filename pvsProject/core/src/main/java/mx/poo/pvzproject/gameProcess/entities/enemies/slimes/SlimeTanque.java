package mx.poo.pvzproject.gameProcess.entities.enemies.slimes;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import mx.poo.pvzproject.gameProcess.entities.enemies.Enemy;
import mx.poo.pvzproject.ui.utils.Assets;

/**
 * Enemigo de alta resistencia (Slime Tanque).
 *
 * <p>
 * El Slime Tanque es una unidad lenta pero muy resistente, diseñada
 * para absorber grandes cantidades de daño y proteger a otras unidades
 * que vengan detrás.
 * </p>
 *
 * <p>
 * Estadísticas:
 * </p>
 * <ul>
 *     <li>Vida: 300 (Alta)</li>
 *     <li>Velocidad: 8 (Muy lenta)</li>
 * </ul>
 *
 * @author SmallJunior
 * @version 1.0
 */
public class SlimeTanque extends Enemy {

    /**
     * Constructor del Slime Tanque.
     *
     * @param x posición inicial en X
     * @param y posición inicial en Y
     */
    public SlimeTanque(float x, float y) {
        super(x, y, 300, 8f);
    }

    @Override
    protected void loadAnimation() {
        Array<TextureRegion> frames = new Array<>();
        for (Texture frame : Assets.slimeTanqueFrames) {
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
