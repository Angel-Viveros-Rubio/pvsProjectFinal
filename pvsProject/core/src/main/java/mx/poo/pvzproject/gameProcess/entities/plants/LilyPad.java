package mx.poo.pvzproject.gameProcess.entities.plants;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import mx.poo.pvzproject.gameProcess.entities.enemies.Enemy;
import mx.poo.pvzproject.gameProcess.entities.plants.properties.SupportsPlant;
import mx.poo.pvzproject.gameProcess.entities.plants.properties.WaterPlaceable;
import mx.poo.pvzproject.gameProcess.entities.projectiles.Projectile;
import mx.poo.pvzproject.ui.utils.Assets;

import java.util.ArrayList;
import java.util.List;

public class LilyPad extends Plant implements WaterPlaceable, SupportsPlant {
    public LilyPad(float x, float y) {
        super(x, y);
        this.maxResistanceTime = 2f; // aguanta como otras (ajusta si quieres inmortal o menos)
        this.cost = 1;
    }

    @Override
    protected void loadAnimation() {

        TextureRegion frame = new TextureRegion(Assets.lilyPadTexture);
        animation = new Animation<>(1f, frame); // frame único
    }


    @Override
    public void act(float delta, ArrayList<Projectile> projectiles, List<Enemy> enemies) {

    }

    @Override
    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, false);
        float scale = 0.09f;
        float width = currentFrame.getRegionWidth() * scale;
        float height = currentFrame.getRegionHeight() * scale;
        batch.draw(currentFrame, x - width / 2f, y - height / 1.50f, width, height);
    }

    @Override
    public Texture getCardTexture() {
        return Assets.cardLilyPad;
    }

    @Override
    public float getCooldownTime() {
        return 3f; // cooldown nenúfar
    }
}
