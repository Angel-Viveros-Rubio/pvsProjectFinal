package mx.poo.pvzproject.gameProcess.entities.plants;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import mx.poo.pvzproject.gameProcess.entities.enemies.Enemy;
import mx.poo.pvzproject.gameProcess.entities.plants.properties.Disparador;
import mx.poo.pvzproject.gameProcess.entities.projectiles.Projectile;
import mx.poo.pvzproject.ui.utils.Assets;

import java.util.ArrayList;
import java.util.List;

public class CornShooter extends Plant implements Disparador {

    private float shootTimer = 0f;
    private final float SHOOT_INTERVAL = 1.8f;
    private final int DAMAGE = 15; // daño del CornShooter

    public CornShooter(float x, float y) {
        super(x, y);
        this.maxResistanceTime = 3f; // ← aguanta 3 segundos de contacto
        this.cost = 4; // ← CornShooter cuesta 4 agua
    }

    @Override
    protected void loadAnimation() {
        Array<TextureRegion> frames = new Array<>();
        for (Texture frame : Assets.cornShooterFrames) {
            frames.add(new TextureRegion(frame));
        }
        animation = new Animation<>(0.08f, frames, Animation.PlayMode.LOOP);
    }

    @Override
    public void act(float delta, ArrayList<Projectile> projectiles, List<Enemy> enemies) {
        shootTimer += delta;

        if (shootTimer >= SHOOT_INTERVAL) {
            shootTimer = 0f;

            boolean hasEnemyAhead = false;
            for (Enemy e : enemies) {
                if (Math.abs(e.getY() - y) < 50 && (e.getX() > x || Math.abs(e.getX() - x) < 80)) {
                    hasEnemyAhead = true;
                    break;
                }
            }

            if (hasEnemyAhead) {
                shoot(projectiles, enemies);
            }
        }
    }

    @Override
    public void shoot(ArrayList<Projectile> projectiles, List<Enemy> enemies) {
        // Crea el proyectil
        projectiles.add(new Projectile(x + 40, y, DAMAGE, 300f)); // ← velocidad maíz (ajusta)

        Assets.cornShootSound.setVolume(0.8f);  // volumen
        Assets.cornShootSound.play();

    }

    @Override
    public Texture getCardTexture() {
        return Assets.cardCornShooter;
    }

    @Override
    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        float scale = 0.06f;
        float width = currentFrame.getRegionWidth() * scale;
        float height = currentFrame.getRegionHeight() * scale;
        batch.draw(currentFrame, x - width / 2.2f, y - height / 2f, width, height);
    }

    @Override
    public float getCooldownTime() {
        return 4f;
    }
}
