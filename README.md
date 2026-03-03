当前已实现内容：

GameState.gameInitialised 设置为 true

Human & AI health 初始化为 20

Human mana 初始化为 2

Turn 初始化为 1

Avatar 逻辑坐标已存入：

gameState.humanAvatarPosition
gameState.aiAvatarPosition

默认值为：

Human: tile (1,2)

AI: tile (7,2)

Avatar Unit 对象已创建并存入：

gameState.humanAvatar
gameState.aiAvatar

所有初始化测试已通过：

sbt test
Passed: 4
🔹 需要棋盘模块完成的内容

当前单位出现在左上角是正常现象，因为：

目前没有 Tile[][] board 结构

drawUnit 传入的 Tile 未绑定到 grid

棋盘模块需要：

在 GameState 中建立：

public Tile[][] board;

在棋盘初始化时填充 board[x][y]

在 Initalize 中改为：

Tile humanTile = gameState.board[1][2];
Tile aiTile = gameState.board[7][2];

使用真实棋盘 Tile 调用：

BasicCommands.drawUnit(out, humanAvatar, humanTile);
