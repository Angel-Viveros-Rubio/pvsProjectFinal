package mx.poo.pvzproject.gameProcess.managers;

import mx.poo.pvzproject.ui.screens.IGameContext;
import mx.poo.pvzproject.gameProcess.spawn.LevelFactory;
import mx.poo.pvzproject.gameProcess.spawn.SlimeSpawner;
import mx.poo.pvzproject.ui.utils.Constants;

/**
 * Contiene todos los managers de lógica del juego.
 * Recibe IGameContext en lugar de GameScreenManager para romper
 * la dependencia circular.
 */
public class SubBossManager {
    public final WaveManager  waveManager;
    public final SlimeSpawner spawner;
    public final WaterManager waterManager;
    public final PlantPlacer plantPlacer;
    public final GameUpdater gameUpdater;

    public SubBossManager(IGameContext ctx) {
        this.waveManager  = new WaveManager(LevelFactory.getLevel(Constants.CURRENT_LEVEL));
        this.spawner      = new SlimeSpawner(waveManager);
        this.waterManager = new WaterManager();
        this.plantPlacer  = new PlantPlacer(ctx);
        this.gameUpdater  = new GameUpdater(ctx, ctx.getRenderSystem().viewport);
    }

    public void update(float delta) {
        gameUpdater.update(delta);
    }
}
