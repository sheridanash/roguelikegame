const baseUrl = `${window.API_URL}/api/tile`

export async function findAllTiles() {
    
  
    const response = await fetch(baseUrl);
    if (response.status === 200){
        return response.json();
    } else if (response.status === 403) {
        return Promise.reject(403);
    }
    return Promise.reject("Could not fetch tiles. ");
} 

export async function findTileById(id) {
    const response = await fetch(`${baseUrl}/${id}`);
    if (response.status === 200){
        return response.json();
    } else if (response.status === 404) {
        return Promise.reject(404);
    }
    return Promise.reject("Tile not found");
}

export async function addTile(tile) {
    const init = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        },
        body: JSON.stringify(tile)
    }

    const response = await fetch(`${baseUrl}`, init);
    if (response.status === 201) {
        return response.json();
    } else if (response.status === 400) {
        const messages = await response.json();
        return Promise.reject({ status: response.status, messages });
    }

    return Promise.reject({ status: response.status });
}


async function putTile(tile) {
    const init = {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json",
            "Authorization": `Bearer ${localStorage.getItem("TOKEN")}`
        },
        body: JSON.stringify(tile)
    };
    const response = await fetch(`${baseUrl}/${tile.tileId}`, init);
    if (response.status === 204) {
        return Promise.resolve();
    } else if (response.status === 403) {
        return Promise.reject(403);
    }
    return Promise.reject("Could not save tile");
}

export async function deleteById(id) {
    const init = { method: "DELETE", headers: { 
        "Authorization": `Bearer ${localStorage.getItem("TOKEN")}` 
    
    } };
    const response = await fetch(`${baseUrl}/${id}`, init);
    if (response.status === 204) {
        return Promise.resolve();
    } else if (response.status === 403) {
        return Promise.reject(403);
    }
    return Promise.reject("Could not delete tile.");
}