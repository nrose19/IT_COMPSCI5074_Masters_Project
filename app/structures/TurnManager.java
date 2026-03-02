package structures;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.basic.Player;
import structures.basic.Unit;

/*
responsible for turn flow control.
 */

public class TurnManager {

    // 定义规则常量，消除魔法数字
    private static final int INITIAL_ROUND = 1;
    private static final int MAX_MANA = 9;
    private static final int MANA_BASE_OFFSET = 1; // 初始法力规则：轮次 + 1

    private Player human;
    private Player ai;
    private Player currentPlayer;

    // 使用 roundNumber 代替 turnNumber，确保双方在同一轮内的资源对等
    private int roundNumber = 0;
    private ActorRef out;

    public TurnManager(ActorRef out, Player human, Player ai) {
        this.out = out;
        this.human = human;
        this.ai = ai;
    }

    /**
     * 判定是否为当前玩家的单位
     */
    public boolean isCurrentPlayersUnit(Unit unit) {
        return unit.getOwner() == currentPlayer;
    }

    /**
     * 切换玩家回合，并处理单位状态重置
     */
    public void switchTurn(BoardManager boardManager) {
        // 1. 切换前重置当前玩家所有单位的状态
        for (Unit unit : boardManager.getUnitsByPlayer(currentPlayer)) {
            unit.resetTurnState();
        }

        // 2. 轮次逻辑：当后手玩家（AI）回合结束时，递增全局大轮次
        if (currentPlayer == ai) {
            roundNumber++;
        }

        // 3. 切换当前活跃玩家
        currentPlayer = (currentPlayer == human) ? ai : human;
    }

    public boolean isHumanTurn() {
        return currentPlayer == human;
    }

    /**
     * 游戏启动：初始化起始轮次并分配首轮资源
     */
    public void startGame() {
        roundNumber = INITIAL_ROUND;
        currentPlayer = human;
        refreshManaForCurrentPlayer();
    }

    /**
     * 结束当前玩家回合，执行抽牌、切换与资源刷新流程
     */
    public void endTurn(BoardManager boardManager) {
        // 1. 规则：回合结束时当前玩家抽取一张牌
        currentPlayer.drawCard();

        // 2. 执行回合切换（内部处理轮次递增与状态重置）
        switchTurn(boardManager);

        // 3. 为切换后的新玩家刷新法力
        refreshManaForCurrentPlayer();

        // 4. UI 消息反馈
        String msg = (currentPlayer == human) ? "Your Turn" : "AI Turn";
        BasicCommands.addPlayer1Notification(out, msg, 2);
    }

    /**
     * 基于当前大轮次（Round）分配 Mana。
     * 修正了原逻辑中由于 turnNumber 每一小步都递增导致的 AI 资源优势漏洞。
     */
    private void refreshManaForCurrentPlayer() {
        // 规则公式：法力 = 当前轮次 + 1，上限为 MAX_MANA
        int manaAmount = Math.min(roundNumber + MANA_BASE_OFFSET, MAX_MANA);
        currentPlayer.setMana(manaAmount);

        // 同步 UI 渲染
        if (currentPlayer == human) {
            BasicCommands.setPlayer1Mana(out, human);
        } else {
            BasicCommands.setPlayer2Mana(out, ai);
        }
    }

    /**
     * 判定胜负：检查双方 Avatar 健康度
     */
    public boolean checkWinCondition() {
        if (human.getHealth() <= 0) {
            BasicCommands.addPlayer1Notification(out, "Player 2 Wins!", 10);
            return true;
        }
        if (ai.getHealth() <= 0) {
            BasicCommands.addPlayer1Notification(out, "Player 1 Wins!", 10);
            return true;
        }
        return false;
    }

    public Player getCurrentPlayer() { return currentPlayer; }
    public Player getHumanPlayer() { return human; }
    public Player getAiPlayer() { return ai; }
    public int getRoundNumber() { return roundNumber; }
}