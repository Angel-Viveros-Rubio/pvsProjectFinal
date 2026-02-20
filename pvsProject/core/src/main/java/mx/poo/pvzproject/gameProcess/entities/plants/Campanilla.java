package mx.poo.pvzproject.gameProcess.entities.plants;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import mx.poo.pvzproject.gameProcess.entities.enemies.Enemy;
import mx.poo.pvzproject.gameProcess.entities.plants.properties.Disparador;
import mx.poo.pvzproject.gameProcess.entities.projectiles.PetalProjectile;
import mx.poo.pvzproject.gameProcess.entities.projectiles.Projectile;
import mx.poo.pvzproject.ui.utils.Assets;

import java.util.ArrayList;
import java.util.List;

public class Campanilla extends Plant implements Disparador {

    private float shootTimer = 0f;
    private final float SHOOT_INTERVAL = 2f; // un poco más lento que CornShooter (ajusta)
    private final int DAMAGE = 20; // daño por pétalo (ajusta si quieres)

    public Campanilla(float x, float y) {
        super(x, y);
        this.maxResistanceTime = 6f; // aguanta un poco más (opcional)
        this.cost = 12;
    }

    @Override
    protected void loadAnimation() {
        Array<TextureRegion> frames = new Array<>();
        for (Texture frame : Assets.campanillaFrames) { // ← crea Assets.campanillaFrames como con corn
            frames.add(new TextureRegion(frame));
        }
        animation = new Animation<>(0.08f, frames, Animation.PlayMode.LOOP);
    }

    @Override
    public void act(float delta, ArrayList<Projectile> projectiles, List<Enemy> enemies) {
        shootTimer += delta;

        if (shootTimer >= SHOOT_INTERVAL) {
            shootTimer = 0f;

            // Verifica si hay enemigo en ALGUNA de las 3 líneas
            boolean hasEnemyInRange = false;
            for (Enemy e : enemies) {
                float dy = Math.abs(e.getY() - y);
                if (dy < 150 && (e.getX() > x || Math.abs(e.getX() - x) < 80)) {
                    hasEnemyInRange = true;
                    break;
                }
            }


            if (hasEnemyInRange) {
                shoot(projectiles, enemies);
            }
        }
    }

    @Override
    public void shoot(ArrayList<Projectile> projectiles, List<Enemy> enemies) {
        // Dispara en 3 direcciones: misma línea, arriba y abajo
        float offset = 110f; // distancia entre líneas (ajusta según tu grid, CELL_HEIGHT = 110f)

        // Pétalo central (misma lane)
        projectiles.add(new PetalProjectile(x + 40, y, DAMAGE));

        // Pétalo arriba
        projectiles.add(new PetalProjectile(x + 40, y + offset, DAMAGE));

        // Pétalo abajo
        projectiles.add(new PetalProjectile(x + 40, y - offset, DAMAGE));

        // Sonido (opcional: puedes crear uno específico o reutilizar)
        if (Assets.petaloShootSound != null) {
            Assets.petaloShootSound.setVolume(0.7f);
            Assets.petaloShootSound.play();
        }
    }

    @Override
    public Texture getCardTexture() {
        return Assets.cardCampanilla; // ← tu tarjeta en Assets
    }

    @Override
    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        float scale = 1f;
        float width = currentFrame.getRegionWidth() * scale;
        float height = currentFrame.getRegionHeight() * scale;
        batch.draw(currentFrame, x - width / 2.2f, y - height / 2f, width, height);
    }

    @Override
    public float getCooldownTime() {
        return 7f;
    }
}
