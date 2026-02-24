package mx.poo.pvzproject;

import com.badlogic.gdx.Game;
import mx.poo.pvzproject.gameProcess.managers.RenderSystem;
import mx.poo.pvzproject.ui.screens.GameScreenManager;
import mx.poo.pvzproject.ui.screens.MenuScreen;
import mx.poo.pvzproject.ui.utils.Assets;

/**
 * Clase principal del videojuego.
 * <p>
 * Esta clase extiende {@link Game} de LibGDX y actúa como punto de entrada
 * de la aplicación. Se encarga de inicializar los sistemas principales,
 * cargar los recursos globales y coordinar el cambio entre pantallas
 * (menú principal y pantalla de juego).
 * </p>
 *
 * <p>
 * No contiene lógica de juego directa, únicamente administra recursos
 * compartidos y controla la navegación entre pantallas.
 * </p>
 *
 * @author SmallJunior
 * @version 1.0
 */
public class MainGame extends Game {

    /**
     * Sistema encargado del renderizado principal del juego.
     */
    public RenderSystem renderSystem;

    /**
     * Método llamado al iniciar la aplicación.
     * <p>
     * Inicializa el sistema de renderizado, carga los assets globales
     * y establece la pantalla inicial del juego (menú principal).
     * </p>
     */
    @Override
    public void create() {
        renderSystem = new RenderSystem();
        Assets.load();
        setScreen(new MenuScreen(this));
    }

    /**
     * Inicia la partida cambiando a la pantalla de juego.
     * <p>
     * Este método es invocado normalmente desde el menú principal
     * cuando el jugador decide comenzar una nueva partida.
     * </p>
     */
    public void startGame() {
        setScreen(new GameScreenManager(this));
    }

    /**
     * Libera los recursos utilizados por el juego.
     * <p>
     * Se encarga de liberar el sistema de renderizado,
     * los assets cargados y la pantalla activa.
     * </p>
     */
    @Override
    public void dispose() {
        if (renderSystem != null) renderSystem.dispose();
        Assets.dispose();
        if (getScreen() != null) getScreen().dispose();
    }
}
