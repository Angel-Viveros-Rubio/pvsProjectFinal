package mx.poo.pvzproject.gameProcess.entities.plants.properties;

import java.util.ArrayList;
import java.util.List;
import mx.poo.pvzproject.gameProcess.entities.enemies.Enemy;
import mx.poo.pvzproject.gameProcess.entities.projectiles.Projectile;

/**
 * Interfaz para plantas que tienen capacidad de disparo.
 *
 * <p>
 * Define el contrato para cualquier planta que genere proyectiles
 * como mecanismo de ataque.
 * </p>
 *
 * @author SmallJunior
 * @version 1.0
 */
public interface Disparador {

    /**
     * Ejecuta la acción de disparo.
     *
     * @param projectiles lista global de proyectiles donde se añadirá el nuevo
     * @param enemies     lista de enemigos para calcular dirección o objetivo
     */
    void shoot(ArrayList<Projectile> projectiles, List<Enemy> enemies);
}
