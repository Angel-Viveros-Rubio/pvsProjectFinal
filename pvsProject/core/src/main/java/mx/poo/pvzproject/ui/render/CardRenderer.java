package mx.poo.pvzproject.ui.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import mx.poo.pvzproject.ui.screens.IGameContext;
import mx.poo.pvzproject.ui.utils.Assets;
import mx.poo.pvzproject.ui.utils.Constants;

/**
 * Renderizador de tarjetas de selección de plantas.
 *
 * <p>
 * Esta clase se encarga de dibujar todas las cartas disponibles
 * en la barra de selección del jugador.
 * </p>
 *
 * <p>
 * Depende exclusivamente de {@link IGameContext} para obtener:
 * </p>
 * <ul>
 *     <li>La planta actualmente seleccionada.</li>
 *     <li>Los tiempos de enfriamiento (cooldowns).</li>
 *     <li>El sistema de renderizado de texto.</li>
 * </ul>
 *
 * <p>
 * Esto evita el acoplamiento directo con una implementación concreta
 * del manejador de pantalla.
 * </p>
 *
 * <p>
 * Funcionalidades principales:
 * </p>
 * <ul>
 *     <li>Renderizado visual de cada carta.</li>
 *     <li>Escalado visual cuando una carta está seleccionada.</li>
 *     <li>Superposición oscura cuando la carta está en cooldown.</li>
 *     <li>Visualización del tiempo restante del cooldown.</li>
 * </ul>
 *
 * @author SmallJunior
 * @version 1.0
 */
public class CardRenderer {

    /** Contexto del juego. */
    private final IGameContext ctx;

    /**
     * Constructor del renderizador de cartas.
     *
     * @param ctx contexto del juego
     */
    public CardRenderer(IGameContext ctx) {
        this.ctx = ctx;
    }

    /**
     * Renderiza todas las cartas de selección y sus respectivos
     * indicadores de estado.
     *
     * @param batch SpriteBatch activo
     */
    protected void render(SpriteBatch batch) {

        // Renderizado base de cartas
        renderCard(batch, Assets.cardCornShooter, Constants.CARD_X1, ctx.getSelectedPlant() == 0);
        renderCard(batch, Assets.cardPapa,        Constants.CARD_X2, ctx.getSelectedPlant() == 1);
        renderCard(batch, Assets.cardWaterPlant,  Constants.CARD_X3, ctx.getSelectedPlant() == 2);
        renderCard(batch, Assets.cardRedBom,      Constants.CARD_X4, ctx.getSelectedPlant() == 3);
        renderCard(batch, Assets.cardLilyPad,     Constants.CARD_X5, ctx.getSelectedPlant() == 4);
        renderCard(batch, Assets.cardMaceta,      Constants.CARD_X6, ctx.getSelectedPlant() == 5);
        renderCard(batch, Assets.cardCampanilla,  Constants.CARD_X7, ctx.getSelectedPlant() == 6);
        renderCard(batch, Assets.cardChampi,      Constants.CARD_X8, ctx.getSelectedPlant() == 7);
        renderCard(batch, Assets.cardShovel,      Constants.CARD_X9, ctx.getSelectedPlant() == 8);

        // Superposición de cooldown
        renderCooldown(batch, Assets.cardCornShooter, Constants.CARD_X1, ctx.getCooldowns().getCorn());
        renderCooldown(batch, Assets.cardPapa,        Constants.CARD_X2, ctx.getCooldowns().getPapa());
        renderCooldown(batch, Assets.cardWaterPlant,  Constants.CARD_X3, ctx.getCooldowns().getWater());
        renderCooldown(batch, Assets.cardRedBom,      Constants.CARD_X4, ctx.getCooldowns().getRedBom());
        renderCooldown(batch, Assets.cardLilyPad,     Constants.CARD_X5, ctx.getCooldowns().getLilyPad());
        renderCooldown(batch, Assets.cardMaceta,      Constants.CARD_X6, ctx.getCooldowns().getMaceta());
        renderCooldown(batch, Assets.cardCampanilla,  Constants.CARD_X7, ctx.getCooldowns().getCampanilla());
        renderCooldown(batch, Assets.cardChampi,      Constants.CARD_X8, ctx.getCooldowns().getChampi());
    }

    /**
     * Dibuja una carta individual.
     *
     * <p>
     * Si la carta está seleccionada, se renderiza ligeramente
     * más pequeña para generar un efecto visual de selección.
     * </p>
     *
     * @param batch    SpriteBatch activo
     * @param tex      textura de la carta
     * @param cardX    posición horizontal base
     * @param selected indica si está seleccionada
     */
    private void renderCard(SpriteBatch batch,
                            com.badlogic.gdx.graphics.Texture tex,
                            float cardX,
                            boolean selected) {

        float scale  = selected ? 0.9f : 1.0f;
        float width  = Constants.CARD_WIDTH  * scale;
        float height = Constants.CARD_HEIGHT * scale;

        float x = cardX + (Constants.CARD_WIDTH  - width)  / 2f;
        float y = Constants.CARDS_Y + (Constants.CARD_HEIGHT - height) / 2f;

        batch.draw(tex, x, y, width, height);
    }

    /**
     * Renderiza la superposición visual de cooldown sobre una carta.
     *
     * <p>
     * Si el tiempo de cooldown es mayor a cero:
     * </p>
     * <ul>
     *     <li>Se aplica un oscurecimiento semitransparente.</li>
     *     <li>Se muestra el tiempo restante centrado en la carta.</li>
     * </ul>
     *
     * @param batch     SpriteBatch activo
     * @param tex       textura de la carta
     * @param cardX     posición horizontal base
     * @param cooldown  tiempo restante del enfriamiento
     */
    private void renderCooldown(SpriteBatch batch,
                                com.badlogic.gdx.graphics.Texture tex,
                                float cardX,
                                float cooldown) {

        if (cooldown <= 0f) return;

        float width  = Constants.CARD_WIDTH;
        float height = Constants.CARD_HEIGHT;
        float x = cardX;
        float y = Constants.CARDS_Y;

        // Oscurecimiento
        batch.setColor(0f, 0f, 0f, 0.5f);
        batch.draw(tex, x, y, width, height);
        batch.setColor(Color.WHITE);

        // Texto de tiempo restante
        String text = String.format("%.0f", cooldown);

        ctx.getRenderSystem().layout.setText(
            ctx.getRenderSystem().font,
            text
        );

        ctx.getRenderSystem().font.draw(
            batch,
            text,
            x + width  / 2f - ctx.getRenderSystem().layout.width  / 2f,
            y + height / 2f + ctx.getRenderSystem().layout.height / 2f
        );
    }
}
