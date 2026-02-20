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
public class Papa extends Plant {

    // 3 animaciones según tiempo restante
    private Animation<TextureRegion> fullAnimation;
    private Animation<TextureRegion> comida1Animation;
    private Animation<TextureRegion> comida2Animation;

    public Papa(float x, float y) {
        super(x, y);
        this.maxResistanceTime = 40f; // ← aguanta 40 segundos
        this.cost = 3; // ← CornShooter cuesta 3 agua
    }

    @Override
    protected void loadAnimation() {
        // Full vida
        Array<TextureRegion> fullFrames = new Array<>();
        for (Texture frame : Assets.papaCompletaFrames) {
            fullFrames.add(new TextureRegion(frame));
        }
        fullAnimation = new Animation<>(0.1f, fullFrames, Animation.PlayMode.LOOP);

        // Media vida
        Array<TextureRegion> comida1Frames = new Array<>();
        for (Texture frame : Assets.papaComida1Frames) {
            comida1Frames.add(new TextureRegion(frame));
        }
        comida1Animation = new Animation<>(0.1f, comida1Frames, Animation.PlayMode.LOOP);

        // Baja vida
        Array<TextureRegion> comida2Frames = new Array<>();
        for (Texture frame : Assets.papaComida2Frames) {
            comida2Frames.add(new TextureRegion(frame));
        }
        comida2Animation = new Animation<>(0.1f, comida2Frames, Animation.PlayMode.LOOP);
    }

    @Override
    public void act(float delta, ArrayList<Projectile> projectiles, List<Enemy> enemies) {
        // Papa no ataca, solo bloquea
    }

    @Override
    public void draw(SpriteBatch batch) {
        Animation<TextureRegion> currentAnim;
        float timeLeft = maxResistanceTime - resistanceTimer;

        if (timeLeft > 20f) {
            currentAnim = fullAnimation; // full vida
        } else if (timeLeft > 10f) {
            currentAnim = comida1Animation; // media vida
        } else {
            currentAnim = comida2Animation; // baja vida
        }

        TextureRegion currentFrame = currentAnim.getKeyFrame(stateTime, true);
        float scale = 0.9f;
        float width = currentFrame.getRegionWidth() * scale;
        float height = currentFrame.getRegionHeight() * scale;
        batch.draw(currentFrame, x - width / 2f, y - height / 2f, width, height);

    }

    @Override
    public Texture getCardTexture() {
        return Assets.cardPapa; // ← retorna su card
    }
    @Override
    public float getCooldownTime() {
        return 12f;
    }
}
