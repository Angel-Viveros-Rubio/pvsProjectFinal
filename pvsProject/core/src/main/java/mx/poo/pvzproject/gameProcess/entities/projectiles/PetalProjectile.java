package mx.poo.pvzproject.gameProcess.entities.projectiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import mx.poo.pvzproject.ui.utils.Assets;

public class PetalProjectile extends Projectile {

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
