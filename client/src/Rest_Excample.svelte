<script>
    import "carbon-components-svelte/css/white.css";
    import QRGenerator from "./QR-Generator.svelte";
    export let name;
    let gifs = [];
    let searchTerm = "";
    let title;
    let id;

    async function searchForGif(e) {
        try {
            const returnValue = await fetch(`/giphy?term=${searchTerm}`);
            const response = await returnValue.json();
            gifs = response.data;
        } catch (error) {
            console.error(error);
        }
    }

    function add(){
        let object = {title:title,id:id}
        addGiph(object)
    }

    async function addGiph(item){
        let headers = new Headers({
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        })
        try{
            const response = await fetch(`/giphy/item`,{
                method: 'POST',
                headers,
                body: JSON.stringify(item)
            })
            const result = await response.json();
            gifs = result.data;
            console.log(gifs)
        }catch (ex){
            console.error(ex)
        }
    }
</script>

<main>
    <h1>Hello {name}!</h1>
    <div class="search-block">
        <input type="text" placeholder="Search for gif" bind:value={searchTerm} />
        <button on:click={searchForGif}>Search</button>
    </div>
    <div class="gifs">
        {#if gifs.length > 0}
            <div class="gifs-grid">
                {#each gifs as gif}
                    <p>{gif.title} and {gif.id}</p>
                {/each}
            </div>
        {:else}
            No gifs to show yet
        {/if}
    </div>
    <div class="search-block">
        <input type="text" placeholder="Create Title of a Item" bind:value={title} />
        <input type="text" placeholder="Create ID of a Item" bind:value={id} />
        <button on:click={add}>Create</button>
    </div>
    <QRGenerator></QRGenerator>
</main>
