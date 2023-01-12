import assert from 'assert';
import {describe} from 'mocha';
import {checkStringIsNotANumber,checkStringIsNotEmpty,checkStringIsNotNull,checkDate,checkString} from "../src/inputChecker.mjs";

describe("inputChecker", function (){
    describe("checkStringIsNotNumber: Is the given String Not a Number or a Number", function (){
        it('should return true with "Hello" ', function() {
            const testResultString = checkStringIsNotANumber("Hello")
            assert.equal(testResultString, true);
        });
        it('should return false with 2', function () {
            const testResultNumber = checkStringIsNotANumber(2)
            assert.equal(testResultNumber, false);
        });
    });

    describe("checkStringIsNotEmpty: Is the given String a normal Word or an empty String", function (){
        it('should return true with "Hello" ', function () {
            const testResultString = checkStringIsNotEmpty("Hello")
            assert.equal(testResultString, true)
        });
        it('should return false with ""', function () {
            const testResultEmptyString = checkStringIsNotEmpty("")
            assert.equal(testResultEmptyString, false)
        });
    })

    describe("checkStringIsNotNull: Is the String null or a normal String", function (){
        it('should return true with "TestString"', function () {
            const testResultWithNormalString = checkStringIsNotNull("TestString")
            assert.equal(testResultWithNormalString, true)
        });
        it('should return false with null', function () {
            const testResultWithNullInitialized = checkStringIsNotNull(null)
            assert.equal(testResultWithNullInitialized, false)
        });
    })
    
    describe("checkDate: the Object from type Date", function (){
        it('should return true with "new Date()"', function () {
            const testResultNormalDate = checkDate(new Date())
            assert.equal(testResultNormalDate, true)
        });
        it('should return false with "11.04.1996"', function () {
            const testResultWithNoDate = checkDate("11.04.1996")
            assert.equal(testResultWithNoDate, false)
        });
    })
    describe("checkString: check the Function over all with different Types", function (){
        it('should return true with "HelloTest"', function () {
            const testResult = checkString("HelloTest")
            assert.equal(testResult, true)
        });
        it('should return false with 1', function () {
            const testResult = checkString(1)
            assert.equal(testResult, false)
        });
    })
});