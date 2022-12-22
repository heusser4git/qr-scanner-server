<script>
    import {Modal} from "carbon-components-svelte"
    import { createEventDispatcher } from 'svelte';
    export let openModal;
    export let personObject;
    const dispatch = createEventDispatcher();

    function updateApp(){
        dispatch('update')
    }

    async function deleteUser(){
        let headers = new Headers({
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        })
        try {
            await fetch('http://localhost:7777/personal/items/' + personObject.id, {
                method: 'Delete',
                headers,
                credentials: 'same-origin'
            })
        }catch (error){
            console.log(error)
        }
        openModal = false;
        updateApp();

    }
</script>

<Modal
    danger
    bind:open={openModal}
    modalHeading="Benutzer Löschen"
    primaryButtonText="Löschen"
    secondaryButtonText="Abbrechen"
    on:click:button--primary={()=>(deleteUser())}
    on:click:button--secondary={() => (openModal = false)}
    on:open
    on:close
    on:submit
>
    <p>Möchten sie Wirklich den User {personObject.vorname} {personObject.nachname} löschen?</p>

</Modal>