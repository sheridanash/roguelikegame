import { useState, useEffect, useContext } from "react";
import { useNavigate } from "react-router-dom";
import GameLoadCard from "./GameLoadCard";
import { createGame, findGameById, findGamesByPlayerId } from "../services/game-api";
import { findHeroById } from "../services/hero-api";


function Dashboard() {

    const navigate = useNavigate();
    const player = JSON.parse(localStorage.getItem("player"));
    const [games, setGames] = useState(player.games);
    const [waiting, setWaiting] = useState(false)
  
    const [game, setGame] = useState({});

    // game to be posted
    const newGame = {
        score: 0,
        isBlueprint: false,
        playerId: player.playerId
    }

    useEffect(() => {
        fetchGames();
    }, []);

    useEffect(() => {
        playerRankings()
    })

    function fetchGames() {
        findGamesByPlayerId(player.playerId)
            .then(json => {
                setGames(json);
            })
            // .then(() => {
            //     console.log(games);
            // })
            .catch(console.log)
    }

    const playerRankings = () => {
        let scores = [];
        for (let i = 0; i < games.length; i++) {
            scores.push(games[i].score)
        }

        scores.sort((a, b) => { return b.score - a.score });
        let tableHtml = "";
        for (let row = 0; row < scores.length; row++) {
            tableHtml += "<tr>";
            for (let col = 0; col < 2; col++) {
                if (col == 1) {
                    tableHtml += `<td id="td${row}_${col}">${scores[row]}</td>`;
                } else {
                    tableHtml += `<td id="td${row}_${col}">${row + 1}</td>`;
                }
            }
            tableHtml += "</tr>"

        }

        document.getElementById("playerBoard").innerHTML = tableHtml;

    }



    function CreateNewGame() {
        setWaiting(true);

        createGame(newGame)
            .then(json => {
                setGame(json); //this does nothing
                fetchGames();
            })
            .then(() => {
                let currentGame = games[games.length - 1];
                console.log(currentGame);
                //console.log(game);
                localStorage.setItem("game", JSON.stringify(currentGame));
                
        })
            .then(setWaiting(false))
            .then(() => {if(waiting==false){
                navigate("/play")
            }

            })
            .catch(console.error)
        
        
       
        
         
       
    }

    function displayGames() {
        // display default if games == null\
       
       
        return games.map(g => (
            <GameLoadCard game={g} />
        ));
    }

    return (
        <div>
            <div>
                <br></br>
                <div>
                    <center>
                        <button type="button" className="btn btn-lrg btn-info" disabled={waiting} onClick={() => { CreateNewGame() }} >
                            Start a New Game!
                        </button></center>
                </div>

                <div>
                    {displayGames()}
                </div>
                <center><h3 style={{ color: 'white' }}>My High Scores</h3></center>
                <table className="table table-bordered table-dark">
                    <thead>
                        <tr>
                            <th scope="col">Rank</th>
                            {/* <th scope="col">Username</th> */}
                            <th scope="col">Score</th>
                        </tr>
                    </thead>
                    <tbody id="playerBoard">
                    </tbody>
                </table>

            </div>
        </div>
    );
}

export default Dashboard;