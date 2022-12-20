<script>
    import { TextArea, Button, Link, ImageLoader } from "carbon-components-svelte";
    import QRCode from "svelte-qrcode"
    import { Html5Qrcode } from "html5-qrcode";
    import { onMount } from "svelte";

    let qrText = "";
    let image;

    // Scanner
    let scanning = false
    let html5Qrcode

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
        alert(`Code matched = ${decodedText}`)
        console.log(decodedResult)
    }

    function onScanFailure(error) {
        console.warn(`Code scan error = ${error}`)
    }



    let object = document.getElementsByClassName("qrcode");
    console.log(object)
    //getElementsByClassName("qrcode").src;
    // PDF Generator


</script>
<main>


    <TextArea labelText="App description" placeholder="Enter a description..." bind:value={qrText}/>

    <QRCode value={qrText} bind:image={image}/>
    <Link bind:href={image} download>download</Link>
    <reader id="reader"></reader>
    {#if scanning}
        <button on:click={stop}>stop</button>
    {:else}
        <button on:click={start}>start</button>
    {/if}
</main>
    <!--
    $Input Feld für Text eingabe

    $Button um den QR-Code zu generieren

    $Anzeige Feld um den generierten Code anzuzeigen

    -->


<style>
    main {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        gap: 20px;
    }
    reader {
        width: 50%;
        min-height: 500px;
        background-color: black;

    }
</style>