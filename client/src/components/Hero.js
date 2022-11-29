import { useEffect, useState } from "react";
import { findAll, findAllHeros } from "../services/hero-api";

function Hero(){
    const [heroes, setHeroes] = useState([]);


    useEffect(() => {
        findAllHeros()
        .then(json => setHeroes(json))
        .catch(console.error)
    }, []); 

    return (
        <div>
            {heroes.map(h => <div>{h.hp}</div>)}
        </div>
    );
}

export default Hero;