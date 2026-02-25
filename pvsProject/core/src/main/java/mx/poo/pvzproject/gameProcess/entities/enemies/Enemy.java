package mx.poo.pvzproject.gameProcess.entities.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Clase base abstracta para todos los enemigos del juego.
 *
 * <p>
 * Define atributos y comportamientos comunes como:
 * </p>
 * <ul>
 *     <li>Posición en el mundo (x, y).</li>
 *     <li>Vida y vida máxima.</li>
 *     <li>Velocidad de movimiento.</li>
 *     <li>Animación y renderizado.</li>
 *     <li>Estado de ataque (comiendo).</li>
 * </ul>
 *
 * <p>
 * Las subclases deben definir su propia animación
 * y pueden personalizar estadísticas como vida y velocidad.
 * </p>
 *
 * @author SmallJunior
 * @version 1.0
 */
public abstract class Enemy {
    protected float x, y;
    protected int health;
    protected int maxHealth;
    protected float speed;

    protected Animation<TextureRegion> animation;
    protected float stateTime = 0f;

    private boolean eating = false;

    /**
     * Constructor base para un enemigo.
     *
     * @param x      posición inicial en X
     * @param y      posición inicial en Y
     * @param health vida inicial
     * @param speed  velocidad de movimiento
     */
    public Enemy(float x, float y, int health, float speed) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.maxHealth = health;
        this.speed = speed;
        loadAnimation();
    }

    /**
     * Carga los recursos gráficos y configura la animación del enemigo.
     * Debe ser implementado por cada subclase.
     */
    protected abstract void loadAnimation();

    /**
     * Actualiza la posición y estado del enemigo.
     *
     * @param delta tiempo transcurrido desde el último frame
     */
    public void update(float delta) {
        if (!eating) {
            x -= speed * delta;
        }
        stateTime += delta;
    }

    /**
     * Dibuja el enemigo en pantalla.
     *
     * @param batch SpriteBatch utilizado para dibujar
     */
    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        float scale = 5f; // slime grande
        float width = currentFrame.getRegionWidth() * scale;
        float height = currentFrame.getRegionHeight() * scale;
        batch.draw(currentFrame, x - width / 2f, y - height / 2f, width, height);
    }

    /**
     * Establece si el enemigo está atacando (comiendo una planta).
     *
     * @param eating true si está comiendo, false si avanza
     */
    public void setEating(boolean eating) {
        this.eating = eating;
    }

    /**
     * Aplica daño al enemigo.
     *
     * @param damage cantidad de daño a restar de la vida
     */
    public void takeDamage(int damage) {
        health -= damage;
    }

    /**
     * Verifica si el enemigo ha muerto.
     *
     * @return true si la vida es menor o igual a 0
     */
    public boolean isDead() {
        return health <= 0;
    }

    /**
     * Verifica si el enemigo ha salido de la pantalla por la izquierda.
     *
     * @return true si x < -100
     */
    public boolean isOffScreen() {
        return x < -100;
    }

    public float getX() { return x; }
    public float getY() { return y; }

    // Getters para barra de vida
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }

}
