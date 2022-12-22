<script>
    import {Modal, TextInput, RadioButtonGroup, RadioButton, DatePicker,DatePickerInput,Grid, Row, Column} from "carbon-components-svelte";
    import { createEventDispatcher } from 'svelte';
    export let openModal;
    export let personObject;

    const dispatch = createEventDispatcher();

    function updateApp(){
        dispatch('update')
    }

    let statusRadios =["Aktiv","Nicht-Aktiv"];

    async function updateUser(){
        console.log(personObject.status)
        if(personObject.status =="Aktiv"){
            status = true;
        }else {
            status = false;
        }
        let user = {
            id: personObject.id,
            nachname: personObject.nachname,
            vorname: personObject.vorname,
            geburtsdatum: new Date(personObject.geburtsdatum),
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
        size="sm"
        bind:open={openModal}
        modalHeading="Bestehenden Benutzer bearbeiten"
        primaryButtonText="Update"
        on:click:button--primary={()=>(updateUser())}
        on:open
        on:close={openModal = false}
        on:submit
>
    <Grid>
        <Row>
            <Column aspectRatio="2x1">
                <DatePicker datePickerType="single" on:change bind:value={personObject.geburtsdatum}>
                    <DatePickerInput labelText="Geburtsdatum" placeholder="mm/dd/yyyy" />
                </DatePicker>
            </Column>
            <Column aspectRatio="2x1">
                <TextInput
                        bind:value={personObject.vorname}
                        labelText="Vorname"
                        placeholder="Geben sie den Vornamen ein..."
                />
                <TextInput
                        bind:value={personObject.nachname}
                        labelText="Nachname"
                        placeholder="Geben sie den Nachnamen ein..."
                />
                <RadioButtonGroup
                        orientation="vertical"
                        legendText="Status"
                        bind:selected={personObject.status}
                >
                    {#each statusRadios as value}
                        <RadioButton labelText={value} {value}/>
                    {/each}
                </RadioButtonGroup>
            </Column>
        </Row>
    </Grid>
</Modal>

