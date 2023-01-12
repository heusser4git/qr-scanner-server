<script>
    import "carbon-components-svelte/css/white.css";
    import {Header, Content, Grid, Row, Column} from "carbon-components-svelte";
    import Admin from "./Admin.svelte";
    import Scanner from "./Scanner.svelte";

    let admin = false
    let content = true
    let scanner = false
</script>

<Header platformName="QR-Scanner">
</Header>
{#if content}
    <Content>
        <Grid>
            <Row>
                <Column></Column>
                <Column>
                    <img src="img/administration.png" alt="administration" width="300" height="300" on:click={()=> (content = false, admin = true)}>
                    <button id="button-admin" on:click={()=> (content = false, admin = true)}
                            class="bx--btn true bx--btn--field bx--btn--primary">Administration</button>
                </Column>
                <Column></Column>
                <Column>
                    <img src="img/scanner.jpg" alt="scanner" width="300" height="300" on:click={()=> (content = false, scanner = true)}>
                    <button id="button-scanner" on:click={()=> (content = false, scanner = true)}
                    class="bx--btn true bx--btn--field bx--btn--primary">Scanner</button>
                </Column>
                <Column></Column>
            </Row>
        </Grid>
    </Content>
{/if}

{#if admin}
    <Admin/>
{/if}
{#if scanner}
    <Scanner/>
{/if}
<style>
    #button-admin{
        width: 100%;
     }
    #button-scanner{
        width: 100%;
    }
</style>

