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
 * Define el comportamiento y atributos comunes como:
 * - Posición en el grid.
 * - Sistema de resistencia (vida).
 * - Costo de colocación.
 * - Animación.
 *
 * Las subclases deben implementar la lógica específica
 * de ataque, animación y propiedades particulares.
 */
public abstract class Plant {
    protected float x, y;
    protected float resistanceTimer = 0f;
    protected float maxResistanceTime = 5f; // tiempo resistencia base
    protected boolean dead = false;
    protected int cost = 2; // ← costo base

    protected Animation<TextureRegion> animation;
    protected float stateTime = 0f;

    public Plant(float x, float y) {
        this.x = x;
        this.y = y;
        loadAnimation();
    }

    protected abstract void loadAnimation();

    public abstract void act(float delta, ArrayList<Projectile> projectiles, List<Enemy> enemies);

    public void update(float delta) {
        stateTime += delta;
    }

    public void increaseResistanceTimer(float delta) {
        resistanceTimer += delta;
    }

    public boolean isDead() {
        return dead || resistanceTimer >= maxResistanceTime;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }
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
    public abstract Texture getCardTexture();
    public abstract float getCooldownTime();
}
