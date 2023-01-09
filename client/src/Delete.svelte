<script>
    import {Modal, ToastNotification} from "carbon-components-svelte"
    import { createEventDispatcher } from 'svelte';
    export let openModal;
    export let personObject;
    const dispatch = createEventDispatcher();
    let toastNotification = false;

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
            toastNotification = true;
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
{#if toastNotification}
    <ToastNotification
            hideCloseButton
            title="Fehler"
            subtitle="Keine Antwort vom Server, prüfen sie die Serverbindung oder laden sie die Seite neu mit F5"
            caption={new Date().toLocaleString()}
            on:click={()=> (toastNotification= false)}
    />
{/if}
