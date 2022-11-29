const baseUrl = `${window.API_URL}/api/map`

export async function findAllMaps() {
    
  
    const response = await fetch(baseUrl);
    if (response.status === 200){
        return response.json();
    } else if (response.status === 403) {
        return Promise.reject(403);
    }
    return Promise.reject("Could not fetch maps. ");
}

export async function findMapById(id) {
    const response = await fetch(`${baseUrl}/${id}`);
    if (response.status === 200){
        return response.json();
    } else if (response.status === 404) {
        return Promise.reject(404);
    }
    return Promise.reject("Map not found");
}

export async function addMap(map) {
    const init = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        },
        body: JSON.stringify(map)
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

async function updateMap(map) {
    const init = {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json",
            "Authorization": `Bearer ${localStorage.getItem("TOKEN")}`
        },
        body: JSON.stringify(map)
    };
    const response = await fetch(`${baseUrl}/${map.mapId}`, init);
    if (response.status === 204) {
        return Promise.resolve();
    } else if (response.status === 403) {
        return Promise.reject(403);
    }
    return Promise.reject("Could not save map.");
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
    return Promise.reject("Could not delete map.");
}