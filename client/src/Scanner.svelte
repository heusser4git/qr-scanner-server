<script>
    import {Content, Button, Grid, Row, Column, Modal, ToastNotification,} from "carbon-components-svelte";
    import {Html5Qrcode} from "html5-qrcode";
    import {onMount} from "svelte";

    let html5Qrcode
    let result = {};
    let open = false;

    let toastNotification = false;
    async function getPersonalItem(id) {
        let httpHeaders = new Headers({
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        });
        try {
            const returnValue = await fetch(`http://localhost:7778/scanner/items/` + id, {
                method: 'GET',
                headers: httpHeaders,
                credentials: 'same-origin'
            });
            result = await returnValue.json();
        } catch (ex) {
            toastNotification = true;
        }
        open = true
    }

    onMount(init)

    function init() {
        html5Qrcode = new Html5Qrcode('reader')
    }

    function start() {
        html5Qrcode.start(
            {facingMode: 'environment'},
            {
                fps: 10,
                qrbox: {width: 250, height: 250},
            },
            onScanSuccess,
            onScanFailure
        )
    }

    async function stop() {
        await html5Qrcode.stop()

    }

    async function onScanSuccess(decodedText, decodedResult) {
        await stop()
        getPersonalItem(decodedText);
    }

    function onScanFailure(error) {
        console.warn(`Code scan error = ${error}`)
    }
</script>
<Content>
    <Grid>
        <h1>Scanner</h1>
        <Row>
            <Column>
                <reader id="reader"></reader>
            </Column>
            <Column>
                <Button on:click={start}>Start</Button>
                <Button on:click={stop}>Stop</Button>
            </Column>
        </Row>
    </Grid>
    <Modal
            bind:open
            modalHeading="Resultat Mitarbeiter Scan"
            primaryButtonText="New Scan"
            on:click:button--primary={() => (open = false, start())}
            on:open
            on:close
            on:submit
    >
        {#if result === ""}
            <p>Es wurde keine Benutzer gefunden mit dieser ID</p>
        {:else}
            <div class="bx--form-item bx--text-input-wrapper">
                <label for="result-firstname" class="bx--label">Vorname</label>
                <input readonly id="result-firstname" class="bx--text-input" value={result.vorname}>
            </div>
            <div class="bx--form-item bx--text-input-wrapper">
                <label for="result-name" class="bx--label">Nachname</label>
                <input readonly id="result-name" class="bx--text-input" value={result.nachname}>
            </div>
            <div class="bx--form-item bx--text-input-wrapper">
                <label for="result-birthday" class="bx--label">Geburtsdatum</label>
                <input readonly id="result-birthday" class="bx--text-input"
                       value={new Date(result.geburtsdatum).toLocaleDateString()}>
            </div>
            {#if result.status}
                <div class="bx--form-item bx--text-input-wrapper">
                    <label for="result-status-true" class="bx--label">Status</label>
                    <input readonly id="result-status-true" class="bx--text-input input-green"
                           value="Der Mitarbeiter hat noch Zugang">
                </div>
            {:else}
                <div class="bx--form-item bx--text-input-wrapper">
                    <label for="result-status-false" class="bx--label">Status</label>
                    <input readonly id="result-status-false" class="bx--text-input input-red"
                           value="Der Mitarbeiter hat keinen Zugang">
                </div>
            {/if}
        {/if}
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
</Content>
<style>
    reader {
        display: inline-block;
        background-color: black;
        width: 1089px;
        min-height: 819px;
        border-style: solid;
        border-color: black;
        border-width: 10px;
    }

    .input-red {
        background-color: rgba(255, 131, 92, 0.9) !important;
    }

    .input-green {
        background-color: rgba(54, 255, 91, 0.9) !important;
    }
</style>


