import { useEffect, useState } from "react";
import { findAll } from "../services/monster-api";
function Monster(){
    const [monsters, setMonsters] = useState([]);


    useEffect(() => {
        findAll()
        .then(json => setMonsters(json))
        .catch(console.error)
    }, []); 

    return (
        <div>
            {monsters.map(m => <div>{m.monsterId}</div>)}
        </div>
    );
}



export default Monster;