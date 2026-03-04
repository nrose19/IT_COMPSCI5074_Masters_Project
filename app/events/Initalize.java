package events;
import structures.basic.Tile;
import com.fasterxml.jackson.databind.JsonNode;
import structures.basic.Position;
import akka.actor.ActorRef;
import demo.CommandDemo;
import demo.Loaders_2024_Check;
import structures.GameState;
import commands.BasicCommands;
import structures.basic.Position;
import structures.basic.Unit;
import utils.BasicObjectBuilders;
import utils.StaticConfFiles;
/**
 * Indicates that both the core game loop in the browser is starting, meaning
 * that it is ready to recieve commands from the back-end.
 * 
 * { 
 *   messageType = “initalize”
 * }
 * 
 * @author Dr. Richard McCreadie
 *
 */
public class Initalize implements EventProcessor{

	@Override
	public void processEvent(ActorRef out, GameState gameState, JsonNode message) {

		gameState.gameInitialised = true;

		// ===== HEALTH =====
		gameState.humanPlayer.setHealth(20);
		gameState.aiPlayer.setHealth(20);

		BasicCommands.setPlayer1Health(out, gameState.humanPlayer);
		BasicCommands.setPlayer2Health(out, gameState.aiPlayer);

		// ===== MANA =====
		gameState.humanPlayer.setMana(2);
		BasicCommands.setPlayer1Mana(out, gameState.humanPlayer);

		// ===== TURN =====
		gameState.turn = 1;

		// ===== POSITIONS =====
		gameState.humanAvatarPosition = new Position(0, 0, 1, 2);
		gameState.aiAvatarPosition = new Position(0, 0, 7, 2);

		Unit humanAvatar = BasicObjectBuilders.loadUnit(
				StaticConfFiles.humanAvatar, 1, Unit.class);
		Unit aiAvatar = BasicObjectBuilders.loadUnit(
				StaticConfFiles.aiAvatar, 2, Unit.class);

		gameState.humanAvatar = humanAvatar;
		gameState.aiAvatar = aiAvatar;

		Tile humanTile = BasicObjectBuilders.loadTile(
				gameState.humanAvatarPosition.getTilex(),
				gameState.humanAvatarPosition.getTiley());

		Tile aiTile = BasicObjectBuilders.loadTile(
				gameState.aiAvatarPosition.getTilex(),
				gameState.aiAvatarPosition.getTiley());

		BasicCommands.drawUnit(out, humanAvatar, humanTile);
		BasicCommands.drawUnit(out, aiAvatar, aiTile);

	}

}


