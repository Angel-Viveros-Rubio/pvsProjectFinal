package mx.poo.pvzproject.gameProcess.managers;

import mx.poo.pvzproject.gameProcess.entities.enemies.Enemy;
import mx.poo.pvzproject.gameProcess.spawn.Wave;

import java.util.ArrayList;
/**
 * Gestiona las oleadas (waves) de enemigos de un nivel.
 *
 * Se encarga de:
 * - Mantener la lista de oleadas.
 * - Actualizar la oleada actual.
 * - Avanzar automáticamente a la siguiente cuando la actual termina.
 * - Determinar cuándo el nivel ha sido completado.
 */
public class
WaveManager {

    private ArrayList<Wave> waves;
    private int currentWaveIndex = 0;
    private Wave currentWave;

    public WaveManager(ArrayList<Wave> waves) {
        this.waves = waves;
        if (!waves.isEmpty()) {
            currentWave = waves.get(0);
        }
    }

    public void update(float delta, ArrayList<Enemy> enemies) {

        if (currentWave == null) return;

        currentWave.update(delta, enemies);

        if (currentWave != null && currentWave.isComplete() && enemies.isEmpty()) {
            currentWaveIndex++;

            if (currentWaveIndex < waves.size()) {
                currentWave = waves.get(currentWaveIndex);
            } else {
                currentWave = null; // nivel terminado
            }
        }
    }

        public boolean isLevelComplete(ArrayList<Enemy> enemies) {
        return currentWave == null && enemies.isEmpty();
    }

    public int getCurrentWave() {
        return currentWaveIndex + 1;
    }

    public int getTotalWaves() {
        return waves.size();
    }
}
