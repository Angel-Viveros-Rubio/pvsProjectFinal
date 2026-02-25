package mx.poo.pvzproject.gameProcess.entities.plants;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import mx.poo.pvzproject.gameProcess.entities.enemies.Enemy;
import mx.poo.pvzproject.gameProcess.entities.projectiles.Projectile;
import mx.poo.pvzproject.ui.utils.Assets;
import java.util.ArrayList;
import java.util.List;

/**
 * Planta generadora de recursos (Girasol de agua).
 *
 * <p>
 * La WaterPlant no ataca, pero su presencia en el tablero acelera
 * la regeneración automática de agua (recurso principal).
 * </p>
 *
 * <p>
 * Características:
 * </p>
 * <ul>
 *     <li>Generación pasiva de recursos.</li>
 *     <li>Baja resistencia.</li>
 *     <li>Costo bajo.</li>
 * </ul>
 *
 * @author SmallJunior
 * @version 1.0
 */
public class WaterPlant extends Plant {

    private Animation<TextureRegion> animation;

    /**
     * Constructor de la WaterPlant.
     *
     * @param x posición en X
     * @param y posición en Y
     */
    public WaterPlant(float x, float y) {
        super(x, y);
        this.maxResistanceTime = 5f; // aguanta poco (como Girasol)
        this.cost = 2; // costo bajo
    }

    @Override
    protected void loadAnimation() {
        Array<TextureRegion> frames = new Array<>();
        for (Texture frame : Assets.waterPlantFrames) {
            frames.add(new TextureRegion(frame));
        }
        animation = new Animation<>(0.08f, frames, Animation.PlayMode.LOOP);
    }

    @Override
    public void act(float delta, ArrayList<Projectile> projectiles, List<Enemy> enemies) {
        // No ataca, solo acelera agua (lógica en MainGame)
    }

    @Override
    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        float scale = 0.065f;
        float width = currentFrame.getRegionWidth() * scale;
        float height = currentFrame.getRegionHeight() * scale;
        batch.draw(currentFrame, x - width / 2f, y - height / 2f, width, height);
    }

    @Override
    public Texture getCardTexture() {
        return Assets.cardWaterPlant; // ← retorna su card
    }
    @Override
    public float getCooldownTime() {
        return 3f;
    }
}
