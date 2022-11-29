function Rules() {
    return (
    <div>
                <h1 className ="text"><strong><center>WELCOME TO ROGUELIKE!</center></strong></h1>

                <br></br>
                
                <h4 className ="rules-text">
                    Looking to kill 10 minutes? Play our game! Roguelikes are a popular genre of game
                    where the game resets each time you die. Our game is an adventure game based on
                    four elements: air, water, earth, and fire. Solve the puzzles, collect all elements 
                    and be the town hero!
                </h4>
                <h4 className="rules-text">
                    Make an account and login to save your score, or play as a guest. If you get stuck,
                    save your progress and come back later.
                </h4>

                <br></br>

                <h4 className="text">
                    Objective:
                </h4>
                <p className="rules-text">
                    Collect all elements and return to start.
                </p>
                <h4 className="text">
                    How Elements Work:
                </h4>
                <p className="rules-text">
                    Air - Allows hero to put out fire.
                </p>
                <p className="rules-text">
                    Water - Allows hero to walk on water.
                </p>
                <p className="rules-text">
                    Earth - Allows hero to clear through rubble.
                </p>
                <p className="rules-text">
                    Fire - Allows hero to burn walls.
                </p>

                <h4 className="text"> 
                    How Scoring Works:
                </h4>
                <p className="rules-text">
                    Collecting Element - 10 points
                </p>

                {/* <h4 className="text">
                    Lives:
                </h4>
                <p className="rules-text">Get 100 Gold - Get another life Lose Live When Hero Runs Into Monster</p> */}

                <h4 className="text">
                    What to Avoid:
                </h4>
                {/* <p className="rules-text">
                    Monsters
                </p> */}
                <p className="rules-text">
                    Fire (if you do not have the fire or water element)
                </p>
                <p className="rules-text">
                    Water (if you do not have the water element)
                </p>

            </div>
    )
}

export default Rules;