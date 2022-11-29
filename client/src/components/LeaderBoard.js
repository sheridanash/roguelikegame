import { useEffect, useState } from "react";
import { findAllPlayers, findPlayerByUsername} from "../services/player-api";


function LeaderBoard() {
    



    // const player = JSON.parse(localStorage.getItem("player"));
    const [players, setPlayers] = useState([]);

    

   


    useEffect(() => {
        findAllPlayers()
            .then(json => setPlayers(json))
            .catch(console.error)

    }, []);

    useEffect(() => {
        currentRankings()
    } )

    

  

    

    const currentRankings = () => {

       
        let rankings = [];

        for (let i = 0; i < players.length; i++) {
            let high_score = 0;
            for (let g = 0; g < players[i].games.length; g++) {
                if (players[i].games[g].score > high_score) {
                    high_score = players[i].games[g].score
                }
            }

            const newRanking = {
                username: players[i].username,
                score: high_score
            }
            rankings.push(newRanking);
        }
        rankings.sort((a, b) => { return b.score - a.score })
       
        let tableHtml = "";
        for (let row = 0; row < rankings.length; row++) {
            tableHtml += "<tr>";
            for (let col = 0; col < 3; col++) {
                if(col == 1 ){
                    tableHtml +=  `<td id="td${row}_${col}">${rankings[row].username}</td>`;
                }else if(col ==2){
                    tableHtml += `<td id="td${row}_${col}">${rankings[row].score}</td>`;
                }else{
                    tableHtml += `<td id="td${row}_${col}">${row +1}</td>`;
                }
            }
            tableHtml += "</tr>"

        }
    
        document.getElementById("board").innerHTML = tableHtml;

    }
        







    return (

        <>
            <center><h1 style={{ color: 'white' }}>LeaderBoard!</h1></center>

            <table className="table table-bordered table-dark">
                <thead>
                    <tr>
                        <th scope="col">Rank</th>
                        <th scope="col">Username</th>
                        <th scope="col">Score</th>
                    </tr>
                </thead>
                <tbody id="board">
                    {/* {currentRankings()} */}
                </tbody>
            </table>

           

        </>
    )

}

export default LeaderBoard;