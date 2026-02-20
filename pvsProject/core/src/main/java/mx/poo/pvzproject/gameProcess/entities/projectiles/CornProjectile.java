package mx.poo.pvzproject.gameProcess.entities.projectiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import mx.poo.pvzproject.ui.utils.Assets;

public class CornProjectile extends Projectile {

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
