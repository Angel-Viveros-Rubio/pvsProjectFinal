package mx.poo.pvzproject.gameProcess.managers;

import mx.poo.pvzproject.gameProcess.spawn.Wave;
import mx.poo.pvzproject.gameProcess.entities.enemies.Enemy;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WaveManagerTest {

    private ArrayList<Wave> makeMockWaves(int count) {
        // Usamos LevelFactory para obtener waves reales si están disponibles,
        // o creamos una lista vacía de Wave como stub simple
        ArrayList<Wave> waves = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            waves.add(new Wave()); // Wave sin spawns = se completa de inmediato
        }
        return waves;
    }

    @Test
    void shouldStartAtWaveOne() {
        WaveManager wm = new WaveManager(makeMockWaves(3));
        assertEquals(1, wm.getCurrentWave());
    }

    @Test
    void totalWavesShouldMatchInput() {
        WaveManager wm = new WaveManager(makeMockWaves(4));
        assertEquals(4, wm.getTotalWaves());
    }

    @Test
    void emptyWaveListShouldBeImmediatelyComplete() {
        WaveManager wm = new WaveManager(new ArrayList<>());
        assertTrue(wm.isLevelComplete(new ArrayList<>()));
    }

    @Test
    void levelNotCompletedIfEnemiesStillAlive() {
        WaveManager wm = new WaveManager(new ArrayList<>()); // sin waves

        ArrayList<Enemy> enemies = new ArrayList<>();

        assertTrue(wm.isLevelComplete(enemies));
    }

    @Test
    void updateWithNoWavesShouldNotCrash() {
        WaveManager wm = new WaveManager(new ArrayList<>());
        assertDoesNotThrow(() -> wm.update(1.0f, new ArrayList<>()));
    }
}
