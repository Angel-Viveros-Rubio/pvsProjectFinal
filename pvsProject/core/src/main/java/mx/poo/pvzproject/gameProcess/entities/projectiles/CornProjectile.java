package mx.poo.pvzproject.gameProcess.entities.projectiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import mx.poo.pvzproject.ui.utils.Assets;

/**
 * Proyectil específico disparado por el {@link mx.poo.pvzproject.gameProcess.entities.plants.CornShooter}.
 *
 * <p>
 * Representa un grano de maíz que viaja en línea recta.
 * </p>
 *
 * <p>
 * Características:
 * </p>
 * <ul>
 *     <li>Velocidad: 400f</li>
 *     <li>Textura: Grano de maíz</li>
 * </ul>
 *
 * @author SmallJunior
 * @version 1.0
 */
public class CornProjectile extends Projectile {

    /**
     * Constructor del proyectil de maíz.
     *
     * @param x      posición inicial en X
     * @param y      posición inicial en Y
     * @param damage daño que inflige al impactar
     */
    public CornProjectile(float x, float y, int damage) {
        super(x, y, damage, 400f);
    }

    @Override
    public void draw(SpriteBatch batch) {
        TextureRegion frame = new TextureRegion(Assets.cornProjectileTexture);
        float width = frame.getRegionWidth() * 0.8f;
        float height = frame.getRegionHeight() * 0.8f;
        batch.draw(frame, x - width / 2f, y - height / 2f, width, height);
    }
}
