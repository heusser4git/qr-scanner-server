<script>
    import {
        Modal,
        TextInput,
        RadioButtonGroup,
        RadioButton,
        DatePicker,
        DatePickerInput,
        Grid,
        Row,
        Column,
        InlineNotification, ToastNotification
    } from "carbon-components-svelte";
    import { createEventDispatcher } from 'svelte';
    import {checkString, checkDate} from "./inputChecker.mjs";

    export let openModal;
    export let personObject;
    const dispatch = createEventDispatcher();
    let statusRadios =["Aktiv","Nicht-Aktiv"];
    let toastNotification = false;
    let inputNotification = false;

    function updateApp(){
        dispatch('update')
    }

    function checkInputData(firstname, surname, date){
        let isFirstnameOk = checkString(firstname)
        let isSurnameOk = checkString(surname)
        let isDateOk = checkDate(date)
        if (isFirstnameOk && isSurnameOk && isDateOk) {
            return true
        } else {
            return false
        }
    }

    function updateUser(){
        if(personObject.status ==="Aktiv"){
            status = true;
        }else {
            status = false;
        }
        console.log(personObject.geburtsdatum)
        let check = checkInputData(personObject.vorname, personObject.nachname, new Date(personObject.geburtsdatum))
        if(check){
            let user = {
                id: personObject.id,
                nachname: personObject.nachname,
                vorname: personObject.vorname,
                geburtsdatum: new Date(personObject.geburtsdatum),
                status: status
            }
            postUser(user)
            openModal = false
            inputNotification = false
        }else {
            inputNotification = true
        }
    }

    async function postUser(user){
        let headers = new Headers({
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        })
        try {
            this.serverUrl = process.env.API_SERVER_URL != null ? process.env.API_SERVER_URL : 'http://localhost:7778'
            await fetch(this.serverUrl + '/personal/items', {
                method: 'POST',
                mode: 'no-cors',
                headers,
                body: JSON.stringify(user)
            })
        }catch (error){
            toastNotification = true;
        }
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
    {#if inputNotification}
        <InlineNotification
                kind="warning-alt"
                hideCloseButton={true}
                title="Falsche Eingabe"
                subtitle="Bitte überprüfen sie die Eingaben"
        />
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
