package mx.poo.pvzproject.gameProcess.spawn;

import mx.poo.pvzproject.gameProcess.entities.enemies.Enemy;
import mx.poo.pvzproject.gameProcess.managers.WaveManager;

import java.util.ArrayList;
/**
 * Encapsula la lógica de generación de enemigos tipo slime.
 *
 * Actúa como intermediario entre el sistema principal del juego
 * y el {@link WaveManager}, delegando en él la gestión completa
 * de las oleadas.
 *
 * Su responsabilidad es:
 * - Actualizar las oleadas activas.
 * - Consultar si el nivel ha terminado.
 */
public class SlimeSpawner {

    private WaveManager waveManager;

    public SlimeSpawner(WaveManager waveManager) {
        this.waveManager = waveManager;
    }

    public void update(float delta, ArrayList<Enemy> enemies) {
        waveManager.update(delta, enemies);
    }

    public boolean isLevelComplete(ArrayList<Enemy> enemies) {
        return waveManager.isLevelComplete(enemies);
    }
}
