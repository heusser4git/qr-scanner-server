let stringIsNotANumber;
let stringIsNotNull;
let stringIsNotEmpty;

function checkStringIsNotANumber(string){
    if(isNaN(string)){
        return true
    }else {
        return false
    }
}

function checkStringIsNotEmpty(string){
    if(string === ""){
        return false
    }else{
        return true
    }
}

function checkStringIsNotNull(string){
    if(string === null){
        return false
    }else {
        return true
    }
}

export function checkDate(date){
    if ( Object.prototype.toString.call(date) === "[object Date]" ) {
        if ( !isNaN(date.getTime()) ) {
            return true
        } else {
            return false
        }
    } else {
        return false
    }
}

export function checkString(string){
    stringIsNotANumber = checkStringIsNotANumber(string);
    stringIsNotEmpty = checkStringIsNotEmpty(string);
    stringIsNotNull = checkStringIsNotNull(string);
    if(stringIsNotEmpty && stringIsNotNull && stringIsNotANumber){
        return true
    } else {
        return false
    }
}