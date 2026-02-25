package mx.poo.pvzproject.gameProcess.entities.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import mx.poo.pvzproject.ui.utils.Assets;

/**
 * Clase base para todos los proyectiles del juego.
 *
 * <p>
 * Define el comportamiento general de cualquier proyectil:
 * </p>
 * <ul>
 *     <li>Posición en pantalla (x, y).</li>
 *     <li>Velocidad de desplazamiento.</li>
 *     <li>Daño que inflige al impactar.</li>
 *     <li>Detección de salida de pantalla.</li>
 * </ul>
 *
 * <p>
 * Está diseñada para ser extendida por subclases que pueden
 * modificar la velocidad, textura o comportamiento específico.
 * </p>
 *
 * @author SmallJunior
 * @version 1.0
 */
public class Projectile {
    protected float x, y;
    protected float speed;
    protected final int damage;

    /**
     * Constructor base para un proyectil.
     *
     * @param startX posición inicial en X
     * @param startY posición inicial en Y
     * @param damage daño que inflige al impactar
     * @param speed  velocidad de desplazamiento
     */
    public Projectile(float startX, float startY, int damage, float speed) {
        this.x = startX;
        this.y = startY;
        this.damage = damage;
        this.speed = speed;
    }

    /**
     * Actualiza la posición del proyectil.
     *
     * @param delta tiempo transcurrido desde el último frame
     */
    public void update(float delta) {
        x += speed * delta;
    }

    /**
     * Dibuja el proyectil en pantalla.
     *
     * @param batch SpriteBatch utilizado para dibujar
     */
    public void draw(SpriteBatch batch) {
        Assets.drawProjectile(batch, x, y);
    }

    /**
     * Verifica si el proyectil ha salido de la pantalla por la derecha.
     *
     * @return true si x > ancho de pantalla + margen
     */
    public boolean isOffScreen() {
        return x > Gdx.graphics.getWidth() + 600;
    }

    public int getDamage() {
        return damage;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
