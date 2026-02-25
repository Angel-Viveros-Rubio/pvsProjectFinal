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
 * Planta de uso único que aplasta a los enemigos cercanos.
 *
 * <p>
 * El Champi actúa como una mina de proximidad. Cuando un enemigo
 * entra en su rango de ataque, se activa y realiza un ataque
 * devastador que inflige gran daño, destruyéndose a sí mismo
 * en el proceso.
 * </p>
 *
 * <p>
 * Estados:
 * </p>
 * <ul>
 *     <li>IDLE: Esperando enemigo.</li>
 *     <li>ALERT: Enemigo detectado (preparación).</li>
 *     <li>SMASH: Ataque y autodestrucción.</li>
 * </ul>
 *
 * @author SmallJunior
 * @version 1.0
 */
public class Champi extends Plant {

    private enum State {
        IDLE,
        ALERT,
        SMASH
    }

    private State currentState = State.IDLE;

    private Enemy targetEnemy;

    private Animation<TextureRegion> idleAnimation;   // 16 frames
    private Animation<TextureRegion> smashAnimation;  // 4 frames
    private TextureRegion alertFrame;                 // 1 frame

    private float alertTimer = 0f;
    private final float alertDuration = 0.3f; // tiempo mostrando ceja

    /**
     * Constructor del Champi.
     *
     * @param x posición en X
     * @param y posición en Y
     */
    public Champi(float x, float y) {
        super(x, y);
        this.cost = 5;
        this.maxResistanceTime = Float.MAX_VALUE; // no muere por tiempo
    }

    @Override
    protected void loadAnimation() {

        // IDLE (16 frames)
        Array<TextureRegion> idleFrames = new Array<>();
        for (Texture t : Assets.champiNeutralFrames) {
            idleFrames.add(new TextureRegion(t));
        }
        idleAnimation = new Animation<>(0.1f, idleFrames, Animation.PlayMode.LOOP);

        // ALERT (1 frame)
        alertFrame = new TextureRegion(Assets.champiDetectedTexture);

        // SMASH (4 frames)
        Array<TextureRegion> smashFrames = new Array<>();
        for (Texture t : Assets.champiAttackFrames) {
            smashFrames.add(new TextureRegion(t));
        }
        smashAnimation = new Animation<>(0.08f, smashFrames, Animation.PlayMode.NORMAL);
    }

    @Override
    public void act(float delta, ArrayList<Projectile> projectiles, List<Enemy> enemies) {

        if (isDead()) return;

        switch (currentState) {

            case IDLE:
                for (Enemy e : enemies) {
                    float attackRange = 140f;

                    if (!e.isDead() &&
                        Math.abs(e.getY() - y) < 60 &&
                        Math.abs(e.getX() - x) <= attackRange) {

                        targetEnemy = e;
                        currentState = State.ALERT;
                        alertTimer = 0f;
                        break;
                    }
                }
                break;

            case ALERT:
                alertTimer += delta;

                if (alertTimer >= alertDuration) {
                    currentState = State.SMASH;
                    stateTime = 0f; // reinicia animación de ataque
                }
                break;

            case SMASH:

                // Si el enemigo muere antes, cancelar
                if (targetEnemy != null && targetEnemy.isDead()) {
                    targetEnemy = null;
                }

                if (smashAnimation.isAnimationFinished(stateTime)) {

                    if (targetEnemy != null) {
                        targetEnemy.takeDamage(500);
                    }

                    setDead(true); // Champi muere como Apisonaflor
                }
                break;
        }
    }

    @Override
    public void draw(SpriteBatch batch) {

        if (isDead()) return;

        TextureRegion currentFrame = null;

        switch (currentState) {

            case IDLE:
                currentFrame = idleAnimation.getKeyFrame(stateTime, true);
                break;

            case ALERT:
                currentFrame = alertFrame;
                break;

            case SMASH:
                currentFrame = smashAnimation.getKeyFrame(stateTime, false);
                break;
        }

        float scale = 0.7f;
        float width = currentFrame.getRegionWidth() * scale;
        float height = currentFrame.getRegionHeight() * scale;

        float drawX = x;
        float drawY = y;

        //Si está atacando y hay enemigo, dibujar sobre el enemigo
        if (currentState == State.SMASH && targetEnemy != null) {
            drawX = targetEnemy.getX();
            drawY = targetEnemy.getY() + 10f;
        }

        batch.draw(currentFrame,
            drawX - width / 2f,
            drawY + 30 - height / 2f,
            width,
            height);
    }

        @Override
    public Texture getCardTexture() {
        return Assets.cardChampi;
    }

    @Override
    public float getCooldownTime() {
        return 10f;
    }
}
