package mx.poo.pvzproject.gameProcess.entities.projectiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import mx.poo.pvzproject.ui.utils.Assets;

/**
 * Proyectil específico disparado por la {@link mx.poo.pvzproject.gameProcess.entities.plants.Campanilla}.
 *
 * <p>
 * Representa un pétalo que viaja en línea recta.
 * </p>
 *
 * <p>
 * Características:
 * </p>
 * <ul>
 *     <li>Velocidad: 350f (ligeramente más lento que el maíz)</li>
 *     <li>Textura: Pétalo</li>
 * </ul>
 *
 * @author SmallJunior
 * @version 1.0
 */
public class PetalProjectile extends Projectile {

    /**
     * Constructor del proyectil de pétalo.
     *
     * @param x      posición inicial en X
     * @param y      posición inicial en Y
     * @param damage daño que inflige al impactar
     */
    public PetalProjectile(float x, float y, int damage) {
        super(x, y, damage, 350f); // velocidad pétalo (ajusta)
    }

    @Override
    public void draw(SpriteBatch batch) {
        TextureRegion frame = new TextureRegion(Assets.petaloTexture); // ← tu textura de pétalo
        float width = frame.getRegionWidth() * 0.8f;
        float height = frame.getRegionHeight() * 0.8f;
        batch.draw(frame, x - width / 2f, y - height / 2f, width, height);
    }
}
