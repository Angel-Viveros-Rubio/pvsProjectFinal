package mx.poo.pvzproject.gameProcess.spawn;

import mx.poo.pvzproject.gameProcess.entities.enemies.Enemy;
import mx.poo.pvzproject.ui.utils.Constants;

import java.util.ArrayList;
import java.util.Random;

/**
 * Representa una oleada de enemigos dentro de un nivel.
 *
 * Permite definir múltiples grupos de enemigos con:
 * - Tipo de enemigo.
 * - Cantidad a generar.
 * - Intervalo de aparición.
 *
 * La clase gestiona automáticamente el temporizador
 * y la creación de enemigos durante la ejecución del juego.
 */
public class Wave {

    private static class SpawnInfo {
        Class<? extends Enemy> type;
        int count;
        float interval;
        int spawned = 0;
        float timer = 0f;

        SpawnInfo(Class<? extends Enemy> type, int count, float interval) {
            this.type = type;
            this.count = count;
            this.interval = interval;
        }
    }

    private final ArrayList<SpawnInfo> spawns = new ArrayList<>();
    private final Random random = new Random();

    /**
     * Agrega enemigos a la oleada
     */
    public Wave add(Class<? extends Enemy> enemy, int count, float interval) {
        spawns.add(new SpawnInfo(enemy, count, interval));
        return this;
    }

    /**
     * Actualiza la oleada y spawnea enemigos
     */
    public void update(float delta, ArrayList<Enemy> enemies) {
        for (SpawnInfo s : spawns) {
            if (s.spawned >= s.count) continue;

            s.timer += delta;
            if (s.timer >= s.interval) {
                s.timer -= s.interval;

                int lane = random.nextInt(Constants.LANES);
                float x = 1280f + 100f;
                float y = Constants.getPlantY(lane);

                try {
                    enemies.add(
                        s.type
                            .getConstructor(float.class, float.class)
                            .newInstance(x, y)
                    );
                    s.spawned++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Indica si la oleada ya terminó de spawnear enemigos
     */
    public boolean isComplete() {
        for (SpawnInfo s : spawns) {
            if (s.spawned < s.count) {
                return false;
            }
        }
        return true;
    }
}
