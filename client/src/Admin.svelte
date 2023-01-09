<script>
    import {
        Content, DataTable, Toolbar, ToolbarContent, ToolbarSearch,
        ToolbarBatchActions, Button, Modal, Grid, Row, Column, ToastNotification,
    } from "carbon-components-svelte";
    import EditIcon from "carbon-icons-svelte/lib/Edit.svelte";
    import Trash from "carbon-icons-svelte/lib/TrashCan.svelte";
    import Qr from "carbon-icons-svelte/lib/QrCode.svelte";
    import AddIcon from "carbon-icons-svelte/lib/Add.svelte";
    import Add from "./Add.svelte";
    import Edit from "./Edit.svelte";
    import QRCode from "svelte-qrcode";
    import Delete from "./Delete.svelte";

    const headers = [
        {key: "nachname", value: "Nachname"},
        {key: "vorname", value: "Vorname"},
        {key: "geburtsdatum", value: "Geburtsdatum"}, //, display: (date) => new Date(date).toLocaleDateString()
        {key: "status", value: "Status"}, //, display: (status) => statusToString(status)
        {key: "anzahlEintritte", value: "Besuche"},
    ];

    let rows = []
    function rawRowsToRows(rawRows){
        let manipulatedRows = [];
        for (const rawRow of rawRows) {
            let rowObject = {
                id: rawRow.id,
                nachname: rawRow.nachname,
                vorname: rawRow.vorname,
                geburtsdatum: new Date(rawRow.geburtsdatum).toLocaleDateString(),
                status: statusToString(rawRow.status),
                anzahlEintritte: rawRow.anzahlEintritte
            }
            manipulatedRows.push(rowObject)
        }
        return manipulatedRows
    }

    let serverError = true;
    let toastNotification

    export async function getPersonalItems() {
        let httpHeaders = new Headers({
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        });
        try {
            const returnValue = await fetch(`http://localhost:7777/personal/items`, {
                method: 'GET',
                headers: httpHeaders,
                credentials: 'same-origin'
            });
            serverError = false;
            rows = rawRowsToRows(await returnValue.json());
        } catch (ex) {
            serverError = true;
            toastNotification = true;
        }
        selectedRowIds = "";
    }

    function statusToString(status) {
        if (status == true) {
            return "Aktiv"
        } else {
            return "Nicht-Aktiv"
        }
    }

    getPersonalItems();

    let filteredRowIds = []
    let selectedRowIds = []
    let openQr = false;
    let openAdd = false;
    let openEdit = false;
    let openDelete = false;
    let imageSrc;
    let selectedObject = {};

    function selectedRowIdToObject() {
        for (const row of rows) {
            if (row.id == selectedRowIds[0]) {
                selectedObject = row
            }
        }
        console.log(selectedObject)
    }

    function openQrModal() {
        selectedRowIdToObject();
        openQr = true
    }

    function openEditModal() {
        selectedRowIdToObject();
        openEdit = true
    }

    function openDeleteModal() {
        selectedRowIdToObject();
        openDelete = true;
    }

</script>
<Content>
    <h1>Administration</h1>
    <DataTable sortable radio bind:selectedRowIds {headers} {rows}>
        <Toolbar>
            <ToolbarBatchActions>
                <Button on:click={() => (openQrModal())} icon={Qr}>QR-Code</Button>
                <Button on:click={() => (openEditModal())} icon={EditIcon}>Edit</Button>
                <Button on:click={() => (openDeleteModal())} icon={Trash}>Delete</Button>
            </ToolbarBatchActions>
            <ToolbarContent>
                <ToolbarSearch
                        persistent
                        shouldFilterRows
                        bind:filteredRowIds/>
                <Button class="addButton" disabled={serverError} icon={AddIcon} on:click={()=>(openAdd = true)}>Add Item</Button>
            </ToolbarContent>
        </Toolbar>
        <svelte:fragment slot="cell" let:row let:cell>
            <p id={cell.key + row.id}>{cell.value}</p>
        </svelte:fragment>
    </DataTable>
    <Modal
            passiveModal
            size="sm"
            bind:open={openQr}
            modalHeading="User-QR Code"
            on:open
            on:close>
        <Grid>
            <Row>
                <Column aspectRatio="3x1"/>
                <Column aspectRatio="3x1">
                    <div>
                        <QRCode value={String(selectedRowIds[0])} bind:image={imageSrc}/>
                    </div>
                    <div>
                        <Button bind:href={imageSrc} on:click={()=>{openQr = false}}
                                download={"QR_"+selectedObject.vorname+"_"+selectedObject.nachname}>Download
                        </Button>
                    </div>
                </Column>
                <Column aspectRatio="3x1"/>
            </Row>
        </Grid>
    </Modal>
    <Add
            bind:openModal={openAdd}
            on:update={()=>getPersonalItems()}
    />
    <Edit
            bind:openModal={openEdit}
            personObject={selectedObject}
            on:update={()=>getPersonalItems()}
    />
    <Delete
            bind:openModal={openDelete}
            personObject={selectedObject}
            on:update={()=>getPersonalItems()}
    />
    {#if toastNotification}
        <ToastNotification
                title="Verbindungsfehler"
                subtitle="Keine Verbindung zum Server, bitte prüfen sie die Internet Verbindung und refreshen die Webseite mit F5"
                caption={new Date().toLocaleString()}
                on:click={()=> (toastNotification= false)}
        />
    {/if}
</Content>

