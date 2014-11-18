QUnit.test( "number generate test", function( assert ) {
	var numbers1 = new NumbersFactory().createRandom();
	assert.notEqual(numbers1, undefined);
	assert.notEqual(numbers1.data, undefined);
	for(var i=0;i<3;i++)
		assert.notEqual(numbers1.data[i], undefined);
});

QUnit.test( "number random generate test", function( assert ) {
	var numbers1 = new NumbersFactory().createRandom();
	assert.notEqual(numbers1, undefined);
	var numbers2 = new NumbersFactory().createRandom();
	assert.notEqual(numbers1, numbers2);
	assert.ok(!numbers1.equals(numbers2));
});

QUnit.test( "checker test - isSame", function( assert ) {
	var n1 = new Numbers(1,2,3);
	var n2 = new Numbers(1,2,3);
	var checker = new Checker();
	var checkResult = checker.check(n1, n2);
	assert.ok(checkResult.isSame, "isSame");
	assert.equal(checkResult.ballCount, 0);
	assert.equal(checkResult.strikeCount, 3);
});

QUnit.test( "checker test - notSame 2s", function( assert ) {
	var n1 = new Numbers(1,2,3);
	var n2 = new Numbers(1,2,4);
	var checker = new Checker();
	var checkResult = checker.check(n1, n2);
	assert.ok(!checkResult.isSame, "isSame");
	assert.equal(checkResult.strikeCount, 2);
});

QUnit.test( "setreducer test - 1b only", function( assert ) {
	var reducer = new OneBallSetReducer(new Numbers("123"));
	var testSample = new Set();
	testSample.add(new Numbers("123"));// x
	testSample.add(new Numbers("132"));// x
	testSample.add(new Numbers("456"));// x
	testSample.add(new Numbers("789"));// x
	testSample.add(new Numbers("178"));// x
	testSample.add(new Numbers("819"));// o
	testSample.add(new Numbers("829"));// x
	testSample.add(new Numbers("823"));// x
	testSample.add(new Numbers("832"));// x
	testSample.add(new Numbers("831"));// x
	testSample.add(new Numbers("836"));// o

	assert.equal(testSample.size(), 11);
	testSample.reduce(reducer);
	assert.equal(testSample.size(), 2);
});

QUnit.test( "setreducer test - 2b only", function( assert ) {
	var reducer = new TwoBallSetReducer(new Numbers("123"));
	var testSample = new Set();
	testSample.add(new Numbers("123"));// x
	testSample.add(new Numbers("103"));// x
	testSample.add(new Numbers("120"));// x
	testSample.add(new Numbers("023"));// x
	testSample.add(new Numbers("230"));// o
	testSample.add(new Numbers("312"));// x
	testSample.add(new Numbers("231"));// x
	testSample.add(new Numbers("310"));// o
	testSample.add(new Numbers("456"));// x
	testSample.add(new Numbers("413"));// x
	testSample.add(new Numbers("431"));// o
	testSample.add(new Numbers("319"));// o

	testSample.reduce(reducer);
	assert.equal(testSample.size(), 4);
});

QUnit.test( "setreducer test - 3b only", function( assert ) {
	var reducer = new ThreeBallSetReducer(new Numbers("123"));
	var testSample = new Set();
	testSample.add(new Numbers("123"));// x
	testSample.add(new Numbers("132"));// x
	testSample.add(new Numbers("321"));// x
	testSample.add(new Numbers("312"));// o
	testSample.add(new Numbers("321"));// x
	testSample.add(new Numbers("231"));// o
	testSample.add(new Numbers("213"));// x
	testSample.add(new Numbers("456"));// x
	testSample.add(new Numbers("324"));// x

	testSample.reduce(reducer);
	assert.equal(testSample.size(), 2);
});

QUnit.test( "setreducer test - 1s only", function( assert ) {
	var reducer = new OneStrikeSetReducer(new Numbers("123"));
	var testSample = new Set();
	testSample.add(new Numbers("123"));// x
	testSample.add(new Numbers("324"));// x
	testSample.add(new Numbers("120"));// x
	testSample.add(new Numbers("321"));// x
	testSample.add(new Numbers("103"));// x
	testSample.add(new Numbers("983"));// o
	testSample.add(new Numbers("123"));// x
	testSample.add(new Numbers("320"));// x
	testSample.add(new Numbers("321"));// x
	testSample.add(new Numbers("145"));// o
	testSample.add(new Numbers("425"));// o

	testSample.reduce(reducer);
	assert.equal(testSample.size(), 3);
});

QUnit.test( "setreducer test - 2s only", function( assert ) {
	var reducer = new TwoStrikeSetReducer(new Numbers("123"));
	var testSample = new Set();
	testSample.add(new Numbers("123"));// x
	testSample.add(new Numbers("120"));// o
	testSample.add(new Numbers("023"));// o
	testSample.add(new Numbers("103"));// o
	testSample.add(new Numbers("132"));// x
	testSample.add(new Numbers("231"));// x
	testSample.add(new Numbers("456"));// x

	testSample.reduce(reducer);
	assert.equal(testSample.size(), 3);
});

QUnit.test( "setreducerfactory test", function( assert ) {
	var reducer = new SetReducerFactory().getSetReducer(new CheckResult(false, 0, 0));
	assert.equal(functionName(reducer), "ExcludeSetReducer");

	reducer = new SetReducerFactory().getSetReducer(new CheckResult(false, 0, 1));
	assert.equal(functionName(reducer), "OneBallSetReducer");

	reducer = new SetReducerFactory().getSetReducer(new CheckResult(false, 0, 2));
	assert.equal(functionName(reducer), "TwoBallSetReducer");

	reducer = new SetReducerFactory().getSetReducer(new CheckResult(false, 0, 3));
	assert.equal(functionName(reducer), "ThreeBallSetReducer");

	reducer = new SetReducerFactory().getSetReducer(new CheckResult(false, 1, 0));
	assert.equal(functionName(reducer), "OneStrikeSetReducer");

	reducer = new SetReducerFactory().getSetReducer(new CheckResult(false, 2, 0));
	assert.equal(functionName(reducer), "TwoStrikeSetReducer");

	reducer = new SetReducerFactory().getSetReducer(new CheckResult(false, 1, 1));
	assert.equal(functionName(reducer), "OneStrikeOneBallSetReducer");

	reducer = new SetReducerFactory().getSetReducer(new CheckResult(false, 1, 2));
	assert.equal(functionName(reducer), "OneStrikeTwoBallSetReducer");
});

QUnit.test( "ai test", function( assert ) {
	var tryCount = 200;
	var sum = 0;
	var max = 0;
	var min = 9999999;
	var result = [];
	var numbersFactory = new NumbersFactory();

	for (var i = 0; i < tryCount; i++) {
		var test = testAi(numbersFactory.createRandom());
		max = Math.max(max, test);
		min = Math.min(min, test);
		sum += test;
		result.push(test);
	}

	printStatistic(tryCount, sum, max, min, result);
	assert.ok(true);
});

function printStatistic(tryCount, sum, max, min,  result){
	console.log("average: " + sum / tryCount);
	console.log("min: " + min + ",  max: " + max);
}

function testAi(userNumber){
	var ai = new Ai();
	var checkResult;
	var checker = new Checker();
	do {
		var nextGuess = ai.getNextGuess();
		checkResult = checker.check(userNumber, nextGuess);
		ai.guessResultIn(nextGuess, checkResult);
	} while (!checkResult.isSame);
	return ai.tryCount;
}

function functionName(fun) {
	var ret = fun.toString();
	ret = ret.substr('function '.length);
	ret = ret.substr(0, ret.indexOf('('));
	return ret;
}