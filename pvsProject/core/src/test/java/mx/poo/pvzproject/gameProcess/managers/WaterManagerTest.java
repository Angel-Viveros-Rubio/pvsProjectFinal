package mx.poo.pvzproject.gameProcess.managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WaterManagerTest{

    private WaterManager waterManager;

    @BeforeEach
    void setUp() {
        waterManager = new WaterManager();
    }

    // --- canAfford ---

    @Test
    void shouldAffordWhenEnoughWater() {
        assertTrue(waterManager.canAfford(4)); // empieza con 4f
    }

    @Test
    void shouldNotAffordWhenNotEnoughWater() {
        assertFalse(waterManager.canAfford(10));
    }

    // --- spendWater ---

    @Test
    void spendWaterShouldReduceCurrentWater() {
        waterManager.spendWater(2);
        assertEquals(2f, waterManager.getCurrentWater(), 0.01f);
    }

    @Test
    void spendAllWaterShouldLeaveZero() {
        waterManager.spendWater(4);
        assertEquals(0f, waterManager.getCurrentWater(), 0.01f);
    }

    // --- update / regeneración ---

    @Test
    void updateShouldRegenWaterAfterOneSecond() {
        float before = waterManager.getCurrentWater();
        waterManager.update(1.0f, new ArrayList<>());
        assertTrue(waterManager.getCurrentWater() > before);
    }

    @Test
    void waterShouldNotExceedMax() {
        // Forzar agua al máximo y seguir regenerando
        for (int i = 0; i < 100; i++) {
            waterManager.update(1.0f, new ArrayList<>());
        }
        assertTrue(waterManager.getCurrentWater() <= waterManager.MAX_WATER);
    }


}
