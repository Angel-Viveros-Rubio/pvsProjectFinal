package mx.poo.pvzproject.gameProcess.managers;

import mx.poo.pvzproject.gameProcess.spawn.LevelFactory;
import mx.poo.pvzproject.gameProcess.spawn.Wave;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SubBossManagerTest {

    // Verifica que los componentes que SubBossManager construye funcionan bien

    @Test
    void waveManagerFromLevel1ShouldHaveWaves() {
        ArrayList<Wave> waves = LevelFactory.getLevel(1);
        WaveManager wm = new WaveManager(waves);
        assertTrue(wm.getTotalWaves() > 0);
    }

    @Test
    void waterManagerInitialWaterShouldBePositive() {
        WaterManager wm = new WaterManager();
        assertTrue(wm.getCurrentWater() > 0);
    }

    @Test
    void waterManagerShouldAffordCostOne() {
        WaterManager wm = new WaterManager();
        assertTrue(wm.canAfford(1));
    }

    @Test
    void waveManagerFromInvalidLevelShouldBeComplete() {
        ArrayList<Wave> waves = LevelFactory.getLevel(999);
        WaveManager wm = new WaveManager(waves);
        assertTrue(wm.isLevelComplete(new ArrayList<>()));
    }
}
