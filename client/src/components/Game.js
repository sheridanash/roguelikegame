import { useEffect, useState } from "react";
import { findAllGames } from "../services/game-api";
function Game(){

    const [games, setGames] = useState([]);


    useEffect(() => {
        findAllGames()
        .then(json => setGames(json))
        .catch(console.error)
    }, []); 

    return (
        <div>
            {games.map(g => <div>{g.gameId}</div>)}
        </div>
    );
}

export default Game;