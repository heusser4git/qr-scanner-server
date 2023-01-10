import assert from 'assert';
import {describe} from 'mocha';
import {checkStringIsNotANumber,checkStringIsNotEmpty,checkStringIsNotNull,checkDate,checkString} from "../src/inputChecker.mjs";

describe("inputChecker", function (){
    describe("checkStringIsNotAnumberIs the given String Not a Number", function (){
        const testResult = checkStringIsNotANumber("Hello")
        console.log(testResult)
        assert.equal(testResult, true);
    })
});