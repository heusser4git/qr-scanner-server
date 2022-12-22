<script>
    import {Modal, TextInput, RadioButtonGroup, RadioButton, DatePicker,DatePickerInput,Grid, Row, Column} from "carbon-components-svelte";
    import { createEventDispatcher } from 'svelte';
    export let openModal;
    const dispatch = createEventDispatcher();
    function updateApp(){
        dispatch('update')
    }
    let nachname
    let vorname
    let geburtsdatum
    let status
    let statusRadios =["Aktiv","Nicht Aktiv"];
    let statusRadio = statusRadios[0];

    async function addUser(){
        if(statusRadio =="Aktiv"){
            status = true;
        }else {
            status = false;
        }
        let user = {
            nachname: nachname,
            vorname: vorname,
            geburtsdatum: new Date(geburtsdatum),
            status: status
        }

        let headers = new Headers({
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        })
        try {
            await fetch('http://localhost:7777/personal/items', {
                method: 'POST',
                mode: 'no-cors',
                headers,
                body: JSON.stringify(user)
            })
        }catch (error){
            console.log(error)
        }
        openModal = false
        updateApp();
    }
</script>


<Modal
        class="higher-Modal"
        size="sm"
        bind:open={openModal}
        modalHeading="Neuen Benutzer hinzufügen"
        primaryButtonText="Add"
        on:click:button--primary={()=>(addUser())}
        on:open
        on:close
        on:submit
>
    <Grid>
        <Row>
            <Column aspectRatio="2x1">
                <DatePicker datePickerType="single" on:change bind:value={geburtsdatum}>
                    <DatePickerInput labelText="Geburtsdatum" placeholder="mm/dd/yyyy" />
                </DatePicker>
            </Column>
            <Column aspectRatio="2x1">
                <TextInput
                        bind:value={vorname}
                        labelText="Vorname"
                        placeholder="Geben sie den Vornamen ein..."
                />
                <TextInput
                        bind:value={nachname}
                        labelText="Nachname"
                        placeholder="Geben sie den Nachnamen ein..."
                />
                <RadioButtonGroup
                        orientation="vertical"
                        legendText="Status"
                        bind:selected={statusRadio}
                >
                    {#each statusRadios as value}
                        <RadioButton labelText={value} {value}/>
                    {/each}
                </RadioButtonGroup>
            </Column>
        </Row>
    </Grid>
</Modal>
