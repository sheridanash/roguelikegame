const baseUrl = `${window.API_URL}/api/player`
// const baseUrl = `http://localhost:8080/api/player`

export async function findAllPlayers() {

    // const init = { method: "POST" };
    const response = await fetch(baseUrl);
    if (response.status === 200) {
        return response.json();
    } else if (response.status === 403) {
        return Promise.reject(403);
    }
    return Promise.reject("Could not fetch players. ");
}

export async function findPlayerByUsername(username) {
    const response = await fetch(`${baseUrl}/${username}`);
    if (response.status === 200) {
        return response.json();
    } else if (response.status === 404) {
        return Promise.reject(404);
    }
    return Promise.reject("Invalid username.");
}

export async function createPlayer(player) {
    const init = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        },
        body: JSON.stringify(player)
    }

    const response = await fetch(`${baseUrl}/create`, init);
    if (response.status === 201) {
        return Promise.resolve();
    } else if (response.status === 400) {
        const messages = await response.json();
        return Promise.reject({ status: response.status, messages });
    }

    return Promise.reject({ status: response.status });

}
