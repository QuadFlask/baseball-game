(function(){
	function BaseballGameController($scope){
		$scope.gameStatus = "ready";
		$scope.userNumber;
		$scope.userNumberValue;
		$scope.aiNumber;
		$scope.userHistoryList = [];
		$scope.aiHistoryList = [];
		var ai;
		var checker;
		var checkResult;

		$scope.userInput = function(){
			if($scope.gameStatus == 'ready'){
				$scope.userNumber = new Numbers(zeroPadding($scope.userNumberValue, 3));
				$scope.gameStatus = 'started';
				ai = new Ai();
				checker = new Checker();
				$scope.userHistoryList = [];
				$scope.aiHistoryList = [];
				$scope.userNumberValue = "";
			} else if($scope.gameStatus == 'started'){
				var userGuess = new Numbers('' + zeroPadding($scope.userNumberValue, 3));
				$scope.userNumberValue = "";
				checkResult = checker.check(ai.aiNumbers, userGuess);
				if(checkResult.isSame) {
					$scope.gameStatus = 'over';
					alert("you win!");
					return;
				}
				$scope.userHistoryList.push({numbers:userGuess, checkResult:checkResult});

				var aiGuessNumbers = ai.getNextGuess();
				checkResult = checker.check(aiGuessNumbers, $scope.userNumber);
				if (checkResult.isSame) {
					$scope.gameStatus = 'over';
					alert("ai win! ai was: " + ai.aiNumbers.at(0) + ai.aiNumbers.at(1) + ai.aiNumbers.at(2));
					return;
				}
				$scope.aiHistoryList.push({numbers:aiGuessNumbers, checkResult:checkResult});
				ai.guessResultIn(aiGuessNumbers, checkResult);
			} else if($scope.gameStatus == 'over'){
				$scope.gameStatus = 'ready';
				$scope.userNumber = undefined;
			}
		};
	};

	function zeroPadding(n, size){
		var result = ""+n;
		if(result.length > size) return result.substring(0, size);
		size -= result.length;
		for(var i=0;i<size; i++)
			result = "0" + result;
		return result;
	}

	var app = angular
				.module('flask', [])
				.controller('BaseballGameController', BaseballGameController)
				.directive('historyList', function(){
					return {
						restrict: 'A',
						scope: {
							historyList: '=gameHistory'
						},
						template: '<p>'
								+ '<p class="rbtn pure-button">{{historyList.numbers.at(0)}}</p>'
								+ '<p class="rbtn pure-button">{{historyList.numbers.at(1)}}</p>'
								+ '<p class="rbtn pure-button">{{historyList.numbers.at(2)}}</p>'
								+ ' -> ' 
								+ '<p class="rbtn pure-button">{{historyList.checkResult.strikeCount}}S  {{historyList.checkResult.ballCount}}B</p>'
								+ '</p>'
					};
				});
})();
