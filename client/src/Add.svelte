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
        ToastNotification,
        InlineNotification
    } from "carbon-components-svelte";
    import {createEventDispatcher} from 'svelte';
    import {checkString, checkDate} from "./inputChecker.mjs";

    export let openModal;
    const dispatch = createEventDispatcher();

    function updateApp() {
        dispatch('update')
    }

    let nachname
    let vorname
    let geburtsdatum
    let statusRadios = ["Aktiv", "Nicht Aktiv"];
    let statusRadio = statusRadios[0];
    let toastNotification = false;
    let inputNotification = false;

    function checkInputData(firstname, surname, date) {
        let isFirstnameOk = checkString(firstname)
        let isSurnameOk = checkString(surname)
        let isDateOk = checkDate(date)
        if (isFirstnameOk && isSurnameOk && isDateOk) {
            return true
        } else {
            return false
        }
    }

    function addUser() {
        let status = "";
        if(statusRadio ==="Aktiv"){
            status = true;
        }else {
            status = false;
        }
        let check = checkInputData(vorname, nachname, new Date(geburtsdatum))
        if (check) {
            let user = {
                nachname: nachname,
                vorname: vorname,
                geburtsdatum: new Date(geburtsdatum),
                status: status
            }
            postUser(user)
            openModal = false
            inputNotification = false
        } else {
            inputNotification = true
        }
    }

    async function postUser(user) {
        try {
            let headers = new Headers({
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            })
            await fetch('http://localhost:7777/personal/items', {
                method: 'POST',
                mode: 'no-cors',
                headers,
                body: JSON.stringify(user)
            })
        } catch (ex) {
            toastNotification = true;
        }
        updateApp()
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
                    <DatePickerInput labelText="Geburtsdatum" placeholder="mm/dd/yyyy"/>
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
