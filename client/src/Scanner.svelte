<script>
    import {Content, Button, Grid, Row, Column, Modal, TextInput,RadioButtonGroup, RadioButton} from "carbon-components-svelte";
    import { Html5Qrcode } from "html5-qrcode";
    import { onMount } from "svelte";

    let scanning = false
    let html5Qrcode
    let result = {};
    let open = true;

    export async function getPersonalItem(id) {
        let httpHeaders = new Headers({
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        });
        try {
            const returnValue = await fetch(`http://localhost:7777/scanner/items/` + id, {
                method: 'GET',
                headers: httpHeaders,
                credentials: 'same-origin'
            });
            result = await returnValue.json();
        } catch (ex) {
            console.log(ex)
        }
        open = true
        console.log(result)
    }

    onMount(init)

    function init() {
        html5Qrcode = new Html5Qrcode('reader')
    }

    function start() {
        html5Qrcode.start(
            { facingMode: 'environment' },
            {
                fps: 10,
                qrbox: { width: 250, height: 250 },
            },
            onScanSuccess,
            onScanFailure
        )
        scanning = true
    }

    async function stop() {
        await html5Qrcode.stop()
        scanning = false
    }

    function onScanSuccess(decodedText, decodedResult) {
        getPersonalItem(decodedText);
        //alert(`Code matched = ${decodedText}`)
        console.log(decodedResult)
        console.log(result)
    }

    function onScanFailure(error) {
        console.warn(`Code scan error = ${error}`)
    }

</script>
<Content>
    <Grid>
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
            on:click:button--primary={() => (open = false)}
            on:open
            on:close
            on:submit
    >
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
            <input readonly id="result-birthday" class="bx--text-input" value={new Date(result.geburtsdatum).toLocaleDateString()}>
        </div>
        {#if result.status}
            <div class="bx--form-item bx--text-input-wrapper">
                <label for="result-status-true" class="bx--label">Status</label>
                <input readonly id="result-status-true" class="bx--text-input input-green" value="Der Mitarbeiter hat noch Zugang">
            </div>
        {:else}
            <div class="bx--form-item bx--text-input-wrapper">
                <label for="result-status-false" class="bx--label">Status</label>
                <input readonly id="result-status-false" class="bx--text-input input-red" value="Der Mitarbeiter hat keinen Zugang">
            </div>
        {/if}
    </Modal>
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
    .input-red{
        background-color: rgba(255,131,92,0.9) !important;
    }
    .input-green{
        background-color: rgba(54,255,91,0.9) !important;
    }
</style>


