package mx.poo.pvzproject.gameProcess.entities.plants.properties;

import java.util.ArrayList;
import java.util.List;
import mx.poo.pvzproject.gameProcess.entities.enemies.Enemy;
import mx.poo.pvzproject.gameProcess.entities.projectiles.Projectile;

public interface Disparador {
    void shoot(ArrayList<Projectile> projectiles, List<Enemy> enemies);
}
