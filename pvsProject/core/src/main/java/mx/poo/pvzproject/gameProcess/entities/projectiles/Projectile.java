package mx.poo.pvzproject.gameProcess.entities.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import mx.poo.pvzproject.ui.utils.Assets;
/**
 * Representa un proyectil básico del juego.
 *
 * Esta clase define el comportamiento general de cualquier proyectil:
 * - Posición en pantalla.
 * - Velocidad de desplazamiento.
 * - Daño que inflige al impactar.
 *
 * Está diseñada para ser extendida por subclases que pueden
 * modificar la velocidad, textura o comportamiento.
 */
public class Projectile {
    protected float x, y;
    protected float speed;
    protected final int damage;

    public Projectile(float startX, float startY, int damage, float speed) {
        this.x = startX;
        this.y = startY;
        this.damage = damage;
        this.speed = speed;
    }

    public void update(float delta) {
        x += speed * delta;
    }

    public void draw(SpriteBatch batch) {
        Assets.drawProjectile(batch, x, y);
    }

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
