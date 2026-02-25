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
 * Planta explosiva de uso único (Petacereza).
 *
 * <p>
 * La RedBom explota poco después de ser colocada o al entrar en contacto
 * con un enemigo, infligiendo daño masivo en un área circular.
 * </p>
 *
 * <p>
 * Características:
 * </p>
 * <ul>
 *     <li>Daño de área (AoE).</li>
 *     <li>Uso único (se destruye al explotar).</li>
 *     <li>Costo medio-alto.</li>
 *     <li>Cooldown largo.</li>
 * </ul>
 *
 * @author SmallJunior
 * @version 1.0
 */
public class RedBom extends Plant {

    private Animation<TextureRegion> animation;

    /**
     * Constructor de la RedBom.
     *
     * @param x posición en X
     * @param y posición en Y
     */
    public RedBom(float x, float y) {
        super(x, y);
        this.maxResistanceTime = 100f; // no muere por tiempo
        this.cost = 6; // costo
    }

    @Override
    protected void loadAnimation() {
        Array<TextureRegion> frames = new Array<>();
        for (Texture frame : Assets.redBomFrames) {
            frames.add(new TextureRegion(frame));
        }
        animation = new Animation<>(0.1f, frames, Animation.PlayMode.NORMAL);
    }

    @Override
    public void act(float delta, ArrayList<Projectile> projectiles, List<Enemy> enemies) {
        // Avanza animación solo si no ha explotado
        if (!isDead()) {
            stateTime += delta;

            // Detección de contacto (explota al tocar slime)
            for (Enemy e : enemies) {
                if (Math.abs(e.getX() - x) < 100 && Math.abs(e.getY() - y) < 100) {
                    explode(enemies);
                    setDead(true);
                    break;
                }
            }

            // Si termina animación, explota (por si no toca nada)
            if (animation.isAnimationFinished(stateTime)) {
                explode(enemies);
                setDead(true);
            }
        }
    }

    /**
     * Ejecuta la lógica de explosión, dañando a enemigos cercanos.
     *
     * @param enemies lista de enemigos activos
     */
    private void explode(List<Enemy> enemies) {

        float range = 150f;
        for (Enemy e : enemies) {
            if (Math.abs(e.getX() - x) < range && Math.abs(e.getY() - y) < range) {
                e.takeDamage(500);
            }
        }

        // SONIDO DE EXPLOSIÓN (corregido para Music)
        if (Assets.explosionSound != null) {
            Assets.explosionSound.stop();           // para si está sonando
            Assets.explosionSound.setVolume(1.0f);  // volumen máximo
            Assets.explosionSound.setPosition(0f);  // reinicia al inicio
            Assets.explosionSound.play();
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (isDead()) return;

        TextureRegion currentFrame = animation.getKeyFrame(stateTime, false);
        float scale = 2f;
        float width = currentFrame.getRegionWidth() * scale;
        float height = currentFrame.getRegionHeight() * scale;
        batch.draw(currentFrame, x - width / 2f, y - height / 2f, width, height);
    }

    @Override
    public Texture getCardTexture() {
        return Assets.cardRedBom;
    }

    @Override
    public float getCooldownTime() {
        return 15f;
    }
}
