import { useContext } from "react";
import { Link, useNavigate } from "react-router-dom";
import AuthContext from "../contexts/AuthContext";

function Nav(){

    // const [player, setPlayer] = useState(JSON.parse(localStorage.getItem("player")));
    const player = JSON.parse(localStorage.getItem("player"));
    const navigate = useNavigate();

    const auth = useContext(AuthContext);
    // const { credentials } = auth;
    const credentials = auth.credentials;

    function logout() {
        localStorage.clear();
        navigate("/");
    }

    return (
        <nav className="navbar navbar-expand-lg navbar-light bg-danger">
            <div className="container-fluid">
                <a className="navbar-brand" href="/"><img src="/website_logo.jpg" alt="Game Image" width="50" height="50"></img></a>
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarNav">
                    <ul className="navbar-nav">
                        <li className="nav-item">
                            <a className="nav-link active" aria-current="page" href="/">Home</a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link" href="/about">About Game</a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link" href="/dashboard">Dashboard</a>
                        </li>
                        {/* <li className="nav-item">
                            <a className="nav-link" href="/play">Play Game</a>
                        </li> */}
                        <li className="nav-item">
                            <a className="nav-link" href="/leaderboard">LeaderBoard</a>
                        </li>
                        
                    </ul>
                    <center>
                    <div className="justify-content-end">
                        {/* {credentials && hasAuthority("USER", "ADMIN") && */}
                        { player && player.username &&
                        <>
                        
                            <h3>{player.username}</h3>
                            <button className="btn btn-dark me-2" onClick={logout}>Logout</button>


                        </> 
                        }
                    </div>
                    </center>
                </div>
            </div>
        </nav>
    );
}

export default Nav;