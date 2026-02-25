package mx.poo.pvzproject.gameProcess.entities.plants;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import mx.poo.pvzproject.gameProcess.entities.enemies.Enemy;
import mx.poo.pvzproject.gameProcess.entities.projectiles.Projectile;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase base abstracta para todas las plantas del juego.
 *
 * <p>
 * Define el comportamiento y atributos comunes como:
 * </p>
 * <ul>
 *     <li>Posición en el grid (x, y).</li>
 *     <li>Sistema de resistencia (vida/tiempo).</li>
 *     <li>Costo de colocación (recurso agua).</li>
 *     <li>Animación y renderizado.</li>
 * </ul>
 *
 * <p>
 * Las subclases deben implementar la lógica específica
 * de ataque, animación y propiedades particulares.
 * </p>
 *
 * @author SmallJunior
 * @version 1.0
 */
public abstract class Plant {
    protected float x, y;
    protected float resistanceTimer = 0f;
    protected float maxResistanceTime = 5f; // tiempo resistencia base
    protected boolean dead = false;
    protected int cost = 2; // ← costo base

    protected Animation<TextureRegion> animation;
    protected float stateTime = 0f;

    /**
     * Constructor base para una planta.
     *
     * @param x posición en X
     * @param y posición en Y
     */
    public Plant(float x, float y) {
        this.x = x;
        this.y = y;
        loadAnimation();
    }

    /**
     * Carga los recursos gráficos y configura la animación de la planta.
     * Debe ser implementado por cada subclase.
     */
    protected abstract void loadAnimation();

    /**
     * Ejecuta la lógica de comportamiento de la planta en cada frame.
     *
     * @param delta       tiempo transcurrido desde el último frame
     * @param projectiles lista de proyectiles activos (para disparar)
     * @param enemies     lista de enemigos activos (para detectar objetivos)
     */
    public abstract void act(float delta, ArrayList<Projectile> projectiles, List<Enemy> enemies);

    /**
     * Actualiza el estado interno de la planta (animación, temporizadores).
     *
     * @param delta tiempo transcurrido desde el último frame
     */
    public void update(float delta) {
        stateTime += delta;
    }

    /**
     * Incrementa el temporizador de resistencia (daño recibido).
     *
     * @param delta cantidad de tiempo/daño a incrementar
     */
    public void increaseResistanceTimer(float delta) {
        resistanceTimer += delta;
    }

    /**
     * Verifica si la planta ha sido destruida.
     *
     * @return true si la planta está muerta o su resistencia se agotó
     */
    public boolean isDead() {
        return dead || resistanceTimer >= maxResistanceTime;
    }

    /**
     * Establece manualmente el estado de vida de la planta.
     *
     * @param dead true para destruir la planta
     */
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    /**
     * Dibuja la planta en pantalla.
     *
     * @param batch SpriteBatch utilizado para dibujar
     */
    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        float scale = 1f;
        float width = currentFrame.getRegionWidth() * scale;
        float height = currentFrame.getRegionHeight() * scale;
        batch.draw(currentFrame, x - width / 2f, y - height / 2f, width, height);
    }


    public float getX() { return x; }
    public float getY() { return y; }

    public float getResistanceTimer() { return resistanceTimer; }
    public float getMaxResistanceTime() { return maxResistanceTime; }

    public int getCost() { return cost; } // ← getter para costo

    /**
     * Obtiene la textura de la carta asociada a esta planta.
     *
     * @return textura para la UI de selección
     */
    public abstract Texture getCardTexture();

    /**
     * Obtiene el tiempo de recarga (cooldown) de esta planta.
     *
     * @return tiempo en segundos
     */
    public abstract float getCooldownTime();
}
