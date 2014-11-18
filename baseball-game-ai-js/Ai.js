function Ai(){
	this.tryCount = 0;
	this.history = [];
	this.remainders;
	this.aiNumbers;
	this.init();
}
Ai.prototype = {
	init: function(){
		this.tryCount = 0;
		this.remainders = new NumbersFactory().createAllPossibilities();
		this.aiNumbers = new NumbersFactory().createRandom();
	},
	getNextGuess: function(){
		this.tryCount++;
		return this.chooseNextRandomCombination();
	},
	chooseNextRandomCombination: function(){
		for(name in this.remainders.elements) 
			return this.remainders.elements[name];
	},
	guessResultIn: function(guessed, checkResult){
		if (!checkResult.isSame) {
			this.history.push(new NumbersHistory(guessed, checkResult));
			var setReducer = new SetReducerFactory().getSetReducer(checkResult);
			this.remainders.reduce(setReducer(guessed));
		}
	}
}

function NumbersHistory(numbers, result){
	this.timeStamp = new Date();
	this.numbers;
	this.result;
}

function NumbersFactory(){	
}

NumbersFactory.prototype = {
	createRandom: function(){
		var numbers = [];
		for(var i=0;i<10;i++)
			numbers[i] = i;
		numbers = this.shuffle(numbers);
		return new Numbers(numbers[0],numbers[1],numbers[2]);
	},
	shuffle: function (o){
	    for(var j, x, i = o.length; i; j = Math.floor(Math.random() * i), x = o[--i], o[i] = o[j], o[j] = x);
	    return o;
	},
	createAllPossibilities: function() {
		var set = new Set();
		for (var i = 0; i < 10; i++)
			for (var j = 0; j < 10; j++)
				for (var k = 0; k < 10; k++)
					if (i != j && j != k && i != k)
						set.add(new Numbers(i, j, k));
		return set;
	}
}

function Numbers(){
	this.data=[];
	if(arguments.length == 1){
		for(var i=0;i<3;i++)
			this.data[i] = arguments[0].charAt(i)-'0';
	} else if(arguments.length == 3)
		for(var i=0;i<3;i++)
			this.data[i] = arguments[i];
}
Numbers.prototype = {
	equals: function(other){
		if(other instanceof Numbers){
			for(var i=0;i<3;i++)
				if(this.data[i] != other.data[i]) return false;
			return true;
		}
		return false;
	},
	at: function(index){
		return this.data[index];
	},
	contains: function(i){
		for (var j = 0; j < this.data.length; j++)
			if (this.data[j] == i)
				return true;
		return false;
	},
	containsButNotSamePosition: function(i, position) {
		for (var j = 0; j < this.data.length; j++)
			if (position != j && this.data[j] == i)
				return true;
		return false;
	},
	hashcode: function(){
		return ""+this.data[0]+this.data[1]+this.data[2];
	},
	toString: function(){
		return ""+this.data[0]+this.data[1]+this.data[2];
	}
}

function Checker(){}
Checker.prototype = {
	check: function(n1, n2){
		var isSame = n1.equals(n2);
		var strikeCount = 0, ballCount = 0;
		
		for (var i = 0; i < 3; i++) {
			if (n1.at(i) == n2.at(i))
				strikeCount++;
			else
				for (var j = 0; j < 3; j++)
					if (i != j && n1.at(i) == n2.at(j))
						ballCount++;
		}

		return new CheckResult(isSame, strikeCount, ballCount);
	}
}

function Set(){
	this.elements = {};
	this.length = 0;
}
Set.prototype = {
	add: function(element){
		var e = this.elements[element.hashcode()];
		if(e == undefined) this.length++;
		this.elements[element.hashcode()] = element;
	},
	size: function(){
		return this.length;
	},
	remove: function(element){
		var e = this.elements[element.hashcode()];
		if(e != undefined) this.length--;
		delete this.elements[element.hashcode()];
	},
	reduce: function(predicate){
		for(var name in this.elements){
			var element = this.elements[name];
			if(predicate(element)) this.remove(element);
		}
	}
}

function CheckResult(isSame, strikeCount, ballCount){
	this.isSame = isSame;
	this.strikeCount = strikeCount;
	this.ballCount = ballCount;
	this.isOut = (strikeCount == 0 && ballCount == 0);
}

function OneBallSetReducer(numbers){
	return function(next){
		if(numbers.equals(next)) return true;
		var contains = false;
		for (var i = 0; i < 3; i++) {
			if (next.containsButNotSamePosition(numbers.at(i), i)) {
				contains = true;
				for (var j = 0; j < 3; j++) {
					if (i != j) {
						if (next.contains(numbers.at(j))) {
							contains = false;
							break;
						}
					}
				}
				break;
			}
		}
		return !contains;
	}
}

function TwoBallSetReducer(numbers){
	return function(next){
		if(numbers.equals(next)) return true;
		var a = next.containsButNotSamePosition(numbers.at(0), 0)
				&& next.containsButNotSamePosition(numbers.at(1), 1);
		var a1 = next.containsButNotSamePosition(numbers.at(0), 0)
				&& next.containsButNotSamePosition(numbers.at(2), 2);
		var a2 = next.containsButNotSamePosition(numbers.at(1), 1)
				&& next.containsButNotSamePosition(numbers.at(2), 2);

		a = a || a1 || a2;
		if (!a) {
			return true;
		} else {
			var isContainsEvery = true;
			for (var i = 0; i < 3; i++)
				isContainsEvery &= next.contains(numbers.at(i));
			return isContainsEvery;
		}
	}
}

function ThreeBallSetReducer(numbers){
	return function(next){
		if(numbers.equals(next)) return true;
		for (var i = 0; i < 3; i++)
			if (!next.containsButNotSamePosition(numbers.at(i), i)) 
				return true;
		return false;
	}
}

function OneStrikeSetReducer(numbers){
	return function(next){
		if(numbers.equals(next)) return true;
		var count = 0;
		for (var i = 0; i < 3; i++)
			count += (numbers.at(i) == next.at(i)) ? 1 : 0;
		if (count != 1)
			return true;
		else {
			count = 0;
			for (var i = 0; i < 3; i++)
				count += numbers.contains(next.at(i)) ? 1 : 0;
			return count > 1;
		}
	}
}

function TwoStrikeSetReducer(numbers){
	return function(next){
		if(numbers.equals(next)) return true;
		var a = next.at(0) == numbers.at(0) && next.at(1) == numbers.at(1);
		var a1 = next.at(0) == numbers.at(0) && next.at(2) == numbers.at(2);
		var a2 = next.at(1) == numbers.at(1) && next.at(2) == numbers.at(2);
		a = a || a1 || a2;

		return !a;
	}
}

function ExcludeSetReducer(numbers){
	return function(next){
		if(numbers.equals(next)) return true;
		for (var i = 0; i < 3; i++)
			if (next.contains(numbers.at(i))) 
				return true;
		return false;
	}
}

function OneStrikeOneBallSetReducer(numbers){
	return function(next){
		if(numbers.equals(next)) return true;
		var count = 0;
		for (var i = 0; i < 3; i++)
			count += next.at(i) == numbers.at(i) ? 1 : 0;
		if (count != 1)
			return true;
		else {
			count = 0;
			for (var i = 0; i < 3; i++)
				count += next.containsButNotSamePosition(numbers.at(i), i) ? 1 : 0;
			return count != 1;
		}
	}
}

function OneStrikeTwoBallSetReducer(numbers){
	return function(next){
		if(numbers.equals(next)) return true;
		for (var i = 0; i < 3; i++)
			if (!next.contains(numbers.at(i)))
				return true;
		return false;
	}
}

function SetReducerFactory(){
	this.excludeSetReducer =  ExcludeSetReducer;
	this.oneBallSetReducer = OneBallSetReducer;
	this.twoBallSetReducer = TwoBallSetReducer;
	this.threeBallSetReducer = ThreeBallSetReducer;
	this.oneStrikeSetReducer = OneStrikeSetReducer;
	this.twoStrikeSetReducer = TwoStrikeSetReducer;
	this.oneStrikeOneBallSetReducer = OneStrikeOneBallSetReducer;
	this.oneStrikeTwoBallSetReducer = OneStrikeTwoBallSetReducer;
}

SetReducerFactory.prototype = {
	getSetReducer: function(checkResult){
		if (checkResult.isOut) {
			return this.excludeSetReducer;
		} else if (checkResult.strikeCount == 0) {
			if (checkResult.ballCount == 1)
				return this.oneBallSetReducer;
			else if (checkResult.ballCount == 2)
				return this.twoBallSetReducer;
			else if (checkResult.ballCount == 3)
				return this.threeBallSetReducer;
		} else if (checkResult.ballCount == 0) {
			if (checkResult.strikeCount == 1)
				return this.oneStrikeSetReducer;
			else if (checkResult.strikeCount == 2)
				return this.twoStrikeSetReducer;
		} else {
			if (checkResult.strikeCount == 1 && checkResult.ballCount == 1) {
				return this.oneStrikeOneBallSetReducer;
			} else if (checkResult.strikeCount == 1 && checkResult.ballCount == 2) {
				return this.oneStrikeTwoBallSetReducer;
			}
		}
	}
}