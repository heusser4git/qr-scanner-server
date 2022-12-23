<script>
    import "carbon-components-svelte/css/white.css";
    import {
        Header, Content, DataTable, Toolbar, ToolbarContent, ToolbarSearch,
        ToolbarMenu, ToolbarMenuItem, ToolbarBatchActions, Button, Modal, Grid, Row, Column
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
        {key: "geburtsdatum", value: "Geburtsdatum", display: (date) => new Date(date).toLocaleDateString()},
        {key: "status", value: "Status", display: (status) => statusToString(status)},
        {key: "anzahlEintritte", value: "Besuche"},
    ];

    let rows = [];

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
            rows = await returnValue.json();
        } catch (error) {
            console.log(error)
        }
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

    function selctedRowIdToObject() {
        for (const row of rows) {
            if (row.id == selectedRowIds[0]) {
                selectedObject = row
                if(row.status){
                    selectedObject.status = "Aktiv"
                }else {
                    selectedObject.status = "Nicht-Aktiv"
                }
            }
        }
        console.log(selectedObject)
    }

    function openQrModal() {
        selctedRowIdToObject();
        openQr = true
    }
    function openEditModal(){
        selctedRowIdToObject();
        openEdit = true
    }
    function openDeleteModal(){
        selctedRowIdToObject();
        openDelete = true;
    }

</script>

<Header platformName="QR-Scanner">
</Header>

<Content>
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
                <Button class="addButton" icon={AddIcon} on:click={()=>(openAdd = true)}>Add Item</Button>
            </ToolbarContent>
        </Toolbar>
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
</Content>

