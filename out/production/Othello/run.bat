@echo off
xcopy /q /y out\production\Othello\Agent.class .
xcopy /q /y lib\game_engine.jar .
if exist Agent.class (
    if exist game_engine.jar (
        java -jar game_engine.jar 0 game.oth.OthelloGame 1234567890 10 3 2000 game.oth.players.GreedyPlayer Agent
        del game_engine.jar
    )
    del Agent.class
)