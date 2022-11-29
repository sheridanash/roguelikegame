import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { findGameById } from "../services/game-api";


function GameLoadCard({ game }) {

    // console.log(game);
    // const player = JSON.parse(localStorage.getItem("player"));
    const navigate = useNavigate();
    const [cardGame, setCardGame] = useState(game);
    
    // useEffect(()=> {
    //     setCardGame(game);
    // }, []);

    function loadGame(game) {
        // console.log(game.gameId);
        // findGameById(game.gameId)
        // .then(json => {
        //     setCardGame(json);
        //     console.log(cardGame);
        // })

        localStorage.setItem("game", JSON.stringify(game));
        navigate("/play");
    }

    function deleteGame(game) {
        // under construction
    }

    function calculateGameProgress(game) {
        let total = 0;
        // if (game.hero.water){
        //     total+=25;
        // }
        // if (game.hero.earth){
        //     total+=25;
        // }
        // if (game.hero.fire){
        //     total+=25;
        // }
        // if (game.hero.air){
        //     total+=25;
        // }
        // if (game.hero.hp == 0){
        //     return "DEAD";
        // }
        return `${total}%`;
    }

    return (
        <div className="card bg-info w-25 p-3">
            <p><b>Game Id: {game.gameId}</b></p>
            <p><b>Progress: {calculateGameProgress(game)}</b></p>
            <p><b>Score: {game.score}</b></p>
            <div className="btn-group">
                <button type="button" className="btn w-25 btn-success" onClick={() => loadGame(game)}>Load</button>
                <button type="button" className="btn w-25 btn-warning" onClick={() => deleteGame(game)}>Delete</button>
            </div>
        </div>

    );
}

export default GameLoadCard;