import { useState, useEffect, useContext } from "react";
import { Link, useNavigate } from "react-router-dom";
import AuthContext from "../contexts/AuthContext";
import { findPlayerByUsername } from "../services/player-api";
import { login } from '../services/auth-api';



function Login() {

    const [candidate, setCandidate] = useState({
        username: "",
        password: ""
    });

    const navigate = useNavigate();
    const [hasError, setHasError] = useState(false);

    const authContext = useContext(AuthContext);

    const onChange = event => {
        const next = { ...candidate };
        next[event.target.name] = event.target.value;
        setCandidate(next);
    };

    const onSubmit = event => {
        event.preventDefault();
        login(candidate)
            .then(principal => {
                authContext.login(principal);
                navigate("/dashboard");
            }).catch(() => setHasError(true));
    };
  
    function login(candidate) {
        findPlayerByUsername(candidate.username)
            .then(player => {
                localStorage.setItem("player", JSON.stringify(player));
                navigate("/dashboard");
            })
            .catch(() => setHasError(true));

    }

    return (
        <div>
            <center>
                <form onSubmit={onSubmit}>
                <h4 className='register-text'>Login</h4>
                    <div className="w-25 ">
                        <label htmlFor="loginUsername" className="form-label" className="login-text">Username</label>
                        <input type="text" className="form-control" name="username" id="loginUsername"
                            onChange={onChange} value={candidate.username} required />
                    </div>
                    <div className="w-25 p-3">
                        <label htmlFor="loginPassword" className="form-label" className="login-text">Password</label>
                        <input type="password" className="form-control" name="password" id="loginPassword"
                            onChange={onChange} value={candidate.password} required />
                    </div>
                    <div>
                        <button type="submit" className="btn btn-primary">Submit</button>
                    </div>
                    {hasError && <div className="alert alert-danger">Bad credentials...</div>}
                </form>
            </center>
        </div>
    )
}

export default Login;