import { useNavigate } from "react-router-dom";
import Login from "./Login";
import './Home.css';
import Register from './Register';
import { useState } from "react";
import { findGameById} from "../services/game-api";

function Home() {

    const navigate = useNavigate();
    const [hitRegisterButton, setHitRegisterButton] = useState(false);

    function onClickRegister() {
        setHitRegisterButton(true);
    }

    function cancelRegister() {
        setHitRegisterButton(false);
    }
    function findDefaultGame() {
        findGameById(1)
            .then(json => 
                {
                    localStorage.setItem("game", JSON.stringify(json));
                })
            .then(()=>{
                navigate("/play");
            })
            .catch(console.log)
    }

    function confirmAction() {
        let confirmAction = window.confirm("Greetings! As a guest, you will not be able to save your game or register your high score to the leaderboard. Are you sure you want to continue?");
        if (confirmAction) {
            findDefaultGame();
            
        } else {

        }
    }

    return (
        <body className="body">
            <div className="header-text">
                <h1><strong><center>WELCOME TO ROGUELIKE!</center></strong></h1>
                <div>
                    <center>
                        <img src="/banner.jpg" alt="Game Image" width="734" height="189"></img>
                    </center>
                </div>
                <br></br>
                <div id="login">
                    <center>
                        {hitRegisterButton ? <Register /> : <Login />}

                        <div id="accountHelp" className="form-text">No account? No problem! Click below to join now or play as a guest.</div>
                        <br></br>
                        <button type="create" onClick={onClickRegister} className="btn btn-sm btn-danger">Create Account</button> <button type="create" onClick={confirmAction} className="btn btn-sm btn-success">Play As Guest</button>
                        {hitRegisterButton &&
                            <div>
                                <br></br>
                                <button type="button" onClick={cancelRegister} className="btn btn-sm btn-secondary">Cancel</button>
                            </div>
                        }
                    </center>
                </div>
                <br></br>
            </div>
        </body>

    )
}

export default Home;