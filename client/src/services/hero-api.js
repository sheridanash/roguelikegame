const baseUrl = `${window.API_URL}/api/hero`

export async function findAllHeros() {
    
   
    const response = await fetch(baseUrl);
    if (response.status === 200){
        return response.json();
    } else if (response.status === 403) {
        return Promise.reject(403);
    }
    return Promise.reject("Could not fetch heroes.");
}

export async function findHeroById(id) {
    const response = await fetch(`${baseUrl}/${id}`);
    if (response.status === 200){
        return response.json();
    } else if (response.status === 404) {
        return Promise.reject(404);
    }
    return Promise.reject("Hero not found");
}

export async function addHero(hero) {
    const init = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        },
        body: JSON.stringify(hero)
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

async function updateHero(hero) {
    const init = {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json",
            "Authorization": `Bearer ${localStorage.getItem("TOKEN")}`
        },
        body: JSON.stringify(hero)
    };
    const response = await fetch(`${baseUrl}/${hero.heroId}`, init);
    if (response.status === 204) {
        return Promise.resolve();
    } else if (response.status === 403) {
        return Promise.reject(403);
    }
    return Promise.reject("Could not save hero.");
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
    return Promise.reject("Could not delete hero.");
}