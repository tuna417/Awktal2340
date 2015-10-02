package awktal.mule;

import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.*;
import javafx.beans.property.*;
import javafx.event.*;
import javafx.util.Duration;
import java.util.List;
import java.util.Collections;

public class TurnManager {
    private static TurnManager instance;

    private static GameState gameState;

    private static final int[] roundFoodRequirements = {3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5};

    private Timeline timeline;
    private PlayerTurnSceneController currentScene;
    private int currentTurnTime;
    private int currentPlayerIndex;

    public static TurnManager getInstance() {
        if (instance == null) {
            instance = new TurnManager();
        }
        return instance;
    }

    public static void setGameState(GameState gameState) {
        TurnManager.gameState = gameState;
    }

    private TurnManager() {
        this.timeline = new Timeline(
            new KeyFrame(
                Duration.seconds(1),
                new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent actionEvent) {
                        onClockTick();
                    }
                }
            ),
            new KeyFrame(
              Duration.seconds(1)
            )
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void beginPlayerTurns() {
        currentPlayerIndex = 0;
        Collections.sort(gameState.getPlayers());
        beginPlayerTurn(getCurrentPlayer());
    }
    
    public void beginPlayerTurn(Player player) {
        currentTurnTime = calculatePlayerTurnTime(player);
        loadScene(GameScene.WORLD_VIEW);
        timeline.play();
    }

    private int calculatePlayerTurnTime(Player player) {
        int foodRequirement = roundFoodRequirements[gameState.getRound()-1];
        int foodValue = player.getInventory().getFood();
        int turnTime;
        if (foodValue >= foodRequirement) {
            turnTime = 50;
            player.getInventory().withdrawFood(foodRequirement);
        } else if (foodValue > 0) {
            turnTime = 30;
            player.getInventory().withdrawFood(foodValue);
        } else {
            turnTime = 5;
        }
        return turnTime;
    }

    public void loadScene(GameScene scene) {
        currentScene = (PlayerTurnSceneController) SceneManager.loadScene(scene); 
        currentScene.updateTurnTimer(currentTurnTime); 
    }

    // public void setCurrentScene(PlayerTurnSceneController currentScene) {
    //     this.currentScene = currentScene;
    // }

    private void onClockTick() {
        currentTurnTime--;
        if (currentTurnTime == 0) {
            endPlayerTurn();
        } else {
            currentScene.updateTurnTimer(currentTurnTime);
        }
    }

    public void endPlayerTurn() {
        timeline.stop();
        currentPlayerIndex++;
        if (currentPlayerIndex == gameState.getPlayers().size()) {
            endPlayerTurns();
        } else {
            beginPlayerTurn(getCurrentPlayer());
        }
    }

    private void endPlayerTurns() {
        gameState.newRound();
        if (!gameState.getPropertySelectionEnabled()) {
            System.out.println("Land selection has been disabled.");
            TurnManager.getInstance().beginPlayerTurns();
        } else {
            SceneManager.loadScene(GameScene.LAND_SELECTION);
        }
        
    }

    public Player getCurrentPlayer() {
        return gameState.getPlayers().get(currentPlayerIndex);
    }

    public int getCurrentTurnTime() {
        return currentTurnTime;
    }
}