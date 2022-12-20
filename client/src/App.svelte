<script>
	import "carbon-components-svelte/css/white.css";
	import {Header, Content,DataTable,Toolbar,ToolbarContent,ToolbarSearch,
		ToolbarMenu,ToolbarMenuItem,ToolbarBatchActions,Button, Modal,Grid, Row,Column} from "carbon-components-svelte";
	import Edit from "carbon-icons-svelte/lib/Edit.svelte";
	import Trash from "carbon-icons-svelte/lib/TrashCan.svelte";
	import Qr from "carbon-icons-svelte/lib/QrCode.svelte";
	import AddIcon from "carbon-icons-svelte/lib/Add.svelte";
	import Add from "./Add.svelte";

	import QRCode from "svelte-qrcode"

	let rows = [];

	export async function getPersonalItems(){
		try {
			const returnValue = await fetch(`http://localhost:7777/personal/items`);
			rows = await returnValue.json();
		} catch (error){
			console.log(error)
		}
	}

	const headers = [
		{ key: "nachname", value: "Nachname" },
		{ key: "vorname", value: "Vorname" },
		{ key: "geburtsdatum", value: "Geburtsdatum" },
		{ key: "status", value: "Status" },
		{ key: "anzahlEintritte", value: "Besuche" },
	];

	getPersonalItems();

	let filteredRowIds = []
	let selectedRowIds=[];
	let openQr = false;
	let openAdd = false;
	let imageSrc;
	let object={};
	function openQrModal(){
		openQr = true
		for (const row of rows) {
			if (row.id == selectedRowIds[0]){
				object = row
			}
		}
		console.log(object)
	}

</script>

<Header platformName="QR-Scanner">
</Header>

<Content>
	<DataTable sortable radio bind:selectedRowIds {headers} {rows}>
		<Toolbar>
			<ToolbarBatchActions>
				<Button on:click={() => (openQrModal())} icon={Qr}>QR-Code</Button>
				<Button icon={Edit}>Edit</Button>
				<Button icon={Trash}>Delete</Button>
			</ToolbarBatchActions>
			<ToolbarContent>
				<ToolbarSearch
				persistent
				shouldFilterRows
				bind:filteredRowIds/>
				<Button icon={AddIcon} on:click={()=>(openAdd = true)}>Add Item</Button>
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
						<Button bind:href={imageSrc} on:click={()=>{openQr = false}} download={"QR_"+object.vorname+"_"+object.nachname}>Download</Button>
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
</Content>

