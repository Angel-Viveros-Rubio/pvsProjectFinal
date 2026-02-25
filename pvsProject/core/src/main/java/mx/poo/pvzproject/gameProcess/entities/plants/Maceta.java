package mx.poo.pvzproject.gameProcess.entities.plants;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import mx.poo.pvzproject.gameProcess.entities.enemies.Enemy;
import mx.poo.pvzproject.gameProcess.entities.plants.properties.StonePlaceable;
import mx.poo.pvzproject.gameProcess.entities.plants.properties.SupportsPlant;
import mx.poo.pvzproject.gameProcess.entities.projectiles.Projectile;
import mx.poo.pvzproject.ui.utils.Assets;

import java.util.ArrayList;
import java.util.List;

/**
 * Planta de soporte para superficies de piedra (Maceta).
 *
 * <p>
 * La Maceta permite colocar plantas sobre casillas de terreno rocoso
 * o pavimentado donde normalmente no crecerían.
 * </p>
 *
 * <p>
 * Características:
 * </p>
 * <ul>
 *     <li>Solo se puede colocar en terreno de piedra.</li>
 *     <li>Permite colocar otra planta encima (SupportsPlant).</li>
 *     <li>Costo bajo.</li>
 * </ul>
 *
 * @author SmallJunior
 * @version 1.0
 */
public class Maceta extends Plant implements StonePlaceable, SupportsPlant {

    /**
     * Constructor de la Maceta.
     *
     * @param x posición en X
     * @param y posición en Y
     */
    public Maceta(float x, float y) {
        super(x, y);
        this.maxResistanceTime = 5f; // aguanta como otras (ajusta si quieres inmortal o menos)
        this.cost = 2; // costo bajo (ajusta)
    }

    @Override
    protected void loadAnimation() {
        Array<TextureRegion> frames = new Array<>();
        for (Texture frame : Assets.macetaFrames) {
            frames.add(new TextureRegion(frame));
        }
        animation = new Animation<>(0.08f, frames, Animation.PlayMode.LOOP);
    }


    @Override
    public void act(float delta, ArrayList<Projectile> projectiles, List<Enemy> enemies) {
        // No dispara, solo existe para soporte
    }

    @Override
    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, false);
        float scale = 0.07f;
        float width = currentFrame.getRegionWidth() * scale;
        float height = currentFrame.getRegionHeight() * scale;
        batch.draw(currentFrame, x - width / 2f, y - height / 1f, width, height);
    }

    @Override
    public Texture getCardTexture() {
        return Assets.cardMaceta;
    }

    @Override
    public float getCooldownTime() {
        return 3f; // cooldown nenúfar
    }
}
