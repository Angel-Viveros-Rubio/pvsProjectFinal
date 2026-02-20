package mx.poo.pvzproject.gameProcess.entities.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Clase base abstracta para todos los enemigos del juego.
 *
 * Define atributos y comportamientos comunes como:
 * - Posición en el mundo.
 * - Vida y vida máxima.
 * - Velocidad de movimiento.
 * - Animación.
 * - Estado de ataque (comiendo).
 *
 * Las subclases deben definir su propia animación
 * y pueden personalizar estadísticas como vida y velocidad.
 */
public abstract class Enemy {
    protected float x, y;
    protected int health;
    protected int maxHealth;
    protected float speed;

    protected Animation<TextureRegion> animation;
    protected float stateTime = 0f;

    private boolean eating = false;

    public Enemy(float x, float y, int health, float speed) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.maxHealth = health;
        this.speed = speed;
        loadAnimation();
    }

    protected abstract void loadAnimation();

    public void update(float delta) {
        if (!eating) {
            x -= speed * delta;
        }
        stateTime += delta;
    }

    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        float scale = 5f; // slime grande
        float width = currentFrame.getRegionWidth() * scale;
        float height = currentFrame.getRegionHeight() * scale;
        batch.draw(currentFrame, x - width / 2f, y - height / 2f, width, height);
    }

    public void setEating(boolean eating) {
        this.eating = eating;
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public boolean isOffScreen() {
        return x < -100;
    }

    public float getX() { return x; }
    public float getY() { return y; }

    // Getters para barra de vida
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }

}
