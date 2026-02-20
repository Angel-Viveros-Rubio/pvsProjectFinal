package mx.poo.pvzproject.gameProcess.entities.enemies.slimes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import mx.poo.pvzproject.gameProcess.entities.enemies.Enemy;
import mx.poo.pvzproject.ui.utils.Assets;

public class NormalSlime extends Enemy {
    // vida 100 (sin cambio), velocidad 30 (era 25 → un poco más amenazante)
    public NormalSlime(float x, float y) {
        super(x, y, 100, 30f);
    }

    @Override
    protected void loadAnimation() {
        Array<TextureRegion> frames = new Array<>();
        for (Texture frame : Assets.slimeNormalFrames) {
            frames.add(new TextureRegion(frame));
        }
        animation = new Animation<>(0.15f, frames, Animation.PlayMode.LOOP);
    }

    @Override
    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        float scale = 4f;
        float width = currentFrame.getRegionWidth() * scale;
        float height = currentFrame.getRegionHeight() * scale;
        batch.draw(currentFrame, x - width / 2f, y - height / 2f, width, height);
    }
}
