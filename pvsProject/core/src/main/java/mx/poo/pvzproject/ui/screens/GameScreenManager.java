package mx.poo.pvzproject.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import mx.poo.pvzproject.MainGame;
import mx.poo.pvzproject.gameProcess.entities.enemies.Enemy;
import mx.poo.pvzproject.gameProcess.entities.plants.Plant;
import mx.poo.pvzproject.gameProcess.entities.soap.SoapDefense;
import mx.poo.pvzproject.gameProcess.entities.soap.SoapWave;
import mx.poo.pvzproject.gameProcess.managers.RenderSystem;
import mx.poo.pvzproject.gameProcess.entities.projectiles.Projectile;
import mx.poo.pvzproject.gameProcess.managers.SubBossManager;
import mx.poo.pvzproject.ui.render.GameRendererManager;
import mx.poo.pvzproject.ui.utils.Assets;
import mx.poo.pvzproject.ui.utils.Constants;
import mx.poo.pvzproject.ui.utils.Cooldowns;

import java.util.ArrayList;

/**
 * Pantalla principal donde se ejecuta la lógica del juego.
 *
 * <p>
 * Implementa la interfaz {@link Screen} de LibGDX y la interfaz
 * {@link IGameContext}, actuando como el núcleo central del estado
 * del juego.
 * </p>
 *
 * <p>
 * Sus responsabilidades principales son:
 * </p>
 * <ul>
 *     <li>Gestionar el ciclo de vida de la pantalla (show, render, resize, dispose).</li>
 *     <li>Mantener el estado global del juego (plantas, enemigos, proyectiles, etc.).</li>
 *     <li>Coordinar los subsistemas principales:
 *          <ul>
 *              <li>{@link SubBossManager} (lógica del juego).</li>
 *              <li>{@link GameRendererManager} (renderizado).</li>
 *          </ul>
 *     </li>
 *     <li>Controlar transiciones entre niveles.</li>
 *     <li>Administrar estados de victoria, derrota y reinicio.</li>
 * </ul>
 *
 * <p>
 * Al implementar {@link IGameContext}, permite que los distintos
 * subsistemas trabajen contra una abstracción en lugar de depender
 * directamente de esta clase concreta, evitando dependencias
 * circulares y mejorando la modularidad del proyecto.
 * </p>
 *
 * @author SmallJunior
 * @version 1.0
 */
public class GameScreenManager implements Screen, IGameContext {

    /** Referencia al juego principal. */
    private final MainGame game;

    /** Controla si se está realizando una transición de nivel. */
    private boolean transitioning = false;

    /** Manager principal de lógica del juego. */
    public SubBossManager managers;

    /** Manager principal de renderizado. */
    public GameRendererManager renderers;

    /** Indica si el Game Over acaba de activarse. */
    private boolean gameOverJustTriggered = false;

    /** Indica si el juego está activo. */
    private boolean gameActive = true;

    /** Estado de victoria. */
    private boolean victory = false;

    /** Estado de derrota. */
    private boolean defeat = false;

    /** Índice de la planta actualmente seleccionada. */
    private int selectedPlant = 0;

    /** Sistema de cooldowns del juego. */
    private final Cooldowns cd = new Cooldowns();

    /** Lista de plantas activas. */
    private final ArrayList<Plant> plants = new ArrayList<>();

    /** Lista de enemigos activos. */
    private final ArrayList<Enemy> enemies = new ArrayList<>();

    /** Lista de proyectiles activos. */
    private final ArrayList<Projectile> projectiles = new ArrayList<>();

    /** Lista de oleadas Soap activas. */
    private final ArrayList<SoapWave> soapWaves = new ArrayList<>();

    /** Lista de defensas Soap activas. */
    private final ArrayList<SoapDefense> soapDefenses = new ArrayList<>();

    /**
     * Constructor del gestor principal de pantalla de juego.
     *
     * @param game referencia al juego principal
     */
    public GameScreenManager(MainGame game) {
        this.game = game;
    }

    /**
     * Se ejecuta cuando la pantalla se muestra por primera vez.
     *
     * <p>
     * Inicializa:
     * </p>
     * <ul>
     *     <li>Los managers principales.</li>
     *     <li>Las defensas Soap en cada línea del mapa.</li>
     *     <li>La música correspondiente al nivel actual.</li>
     * </ul>
     */
    @Override
    public void show() {
        managers = new SubBossManager(this);
        renderers = new GameRendererManager(
            this,
            game.renderSystem.batch,
            game.renderSystem.shapeRenderer,
            game.renderSystem.viewport
        );

        float soapX = Constants.GRID_START_X - 80f;
        for (int i = 0; i < Constants.LANES; i++) {
            soapDefenses.add(new SoapDefense(soapX, Constants.getPlantY(i), i));
        }

        updateBackgroundMusic();
    }

    /**
     * Método principal del ciclo de renderizado.
     *
     * @param delta tiempo transcurrido desde el último frame
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.renderSystem.updateCamera();

        if (gameActive) {
            managers.update(delta);
        }

        renderers.renderGame();

        if (!gameActive) {
            renderers.renderGameOver(victory);

            if (gameOverJustTriggered) {
                gameOverJustTriggered = false;
                return;
            }

            renderers.handleGameOverInput(this);
        }
    }

    /**
     * Ajusta el viewport al cambiar el tamaño de la ventana.
     */
    @Override
    public void resize(int w, int h) {
        if (game.renderSystem != null) {
            game.renderSystem.resize(w, h);
        }
    }

    @Override public void pause()  {}
    @Override public void resume() {}
    @Override public void hide()   {}

    /**
     * Libera los recursos asociados al renderizado.
     */
    @Override
    public void dispose() {
        if (renderers != null) {
            renderers.dispose();
        }
    }

    /**
     * Avanza al siguiente nivel del juego.
     *
     * <p>
     * Incrementa el nivel actual si no se ha alcanzado el máximo,
     * actualiza la música y reinicia el estado del juego.
     * </p>
     */
    @Override
    public void advanceToNextLevel() {
        if (transitioning) return;

        transitioning = true;

        if (Constants.CURRENT_LEVEL < Constants.MAX_LEVEL) {
            Constants.CURRENT_LEVEL++;
        }

        updateBackgroundMusic();
        restartAfterGameOver();
    }

    /**
     * Reinicia completamente el estado del juego tras derrota
     * o cambio de nivel.
     */
    @Override
    public void restartAfterGameOver() {
        gameActive = true;
        victory = false;
        defeat = false;

        plants.clear();
        enemies.clear();
        projectiles.clear();
        soapWaves.clear();

        selectedPlant = 0;
        cd.reset();

        managers = new SubBossManager(this);
        soapDefenses.forEach(SoapDefense::reset);
        renderers.gameOverRenderer.reset();

        Assets.currentBackgroundMusic.setPosition(0f);
        Assets.currentBackgroundMusic.play();

        transitioning = false;
    }

    /**
     * Actualiza la música de fondo según el nivel actual.
     */
    private void updateBackgroundMusic() {
        Assets.currentBackgroundMusic.stop();

        if (Constants.CURRENT_LEVEL == 10) {
            Assets.currentBackgroundMusic = Assets.bossMusic;
        } else if (Constants.CURRENT_LEVEL <= 3) {
            Assets.currentBackgroundMusic = Assets.dayMusic;
        } else if (Constants.CURRENT_LEVEL <= 6) {
            Assets.currentBackgroundMusic = Assets.jungleMusic;
        } else {
            Assets.currentBackgroundMusic = Assets.nightMusic;
        }

        Assets.currentBackgroundMusic.play();
    }

    // ==================== Implementación IGameContext ====================

    @Override public ArrayList<Plant> getPlants() { return plants; }
    @Override public ArrayList<Enemy> getEnemies() { return enemies; }
    @Override public ArrayList<Projectile> getProjectiles() { return projectiles; }
    @Override public ArrayList<SoapWave> getSoapWaves() { return soapWaves; }
    @Override public ArrayList<SoapDefense> getSoapDefenses() { return soapDefenses; }

    @Override public boolean isVictory() { return victory; }
    @Override public int getSelectedPlant() { return selectedPlant; }
    @Override public Cooldowns getCooldowns() { return cd; }

    @Override public void setGameActive(boolean a) { this.gameActive = a; }
    @Override public void setVictory(boolean v) { this.victory = v; }
    @Override public void setDefeat(boolean d) { this.defeat = d; }
    @Override public void setSelectedPlant(int p) { this.selectedPlant = p; }
    @Override public void markGameOverTriggered() { gameOverJustTriggered = true; }

    @Override public SubBossManager getManagers() { return managers; }
    @Override public GameRendererManager getRenderers() { return renderers; }
    @Override public RenderSystem getRenderSystem() { return game.renderSystem; }

    /**
     * Devuelve la referencia directa al juego principal.
     *
     * <p>
     * Uso restringido a código interno del paquete {@code ui}.
     * </p>
     *
     * @return instancia de {@link MainGame}
     */
    private MainGame getGame() {
        return game;
    }
}
