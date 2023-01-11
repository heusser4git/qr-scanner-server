import assert from 'assert';
import {describe} from 'mocha';
import {checkStringIsNotANumber,checkStringIsNotEmpty,checkStringIsNotNull,checkDate,checkString} from "../src/inputChecker.mjs";

describe("inputChecker", function (){
    describe("checkStringIsNotAnumber: Is the given String Not a Number", function (){
        it('should return true', function() {
        const testResult = checkStringIsNotANumber("Hello")
        assert.equal(testResult, true);
        });
    });
});