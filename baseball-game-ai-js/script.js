(function(){
	function BaseballGameController($scope){
		$scope.gameStatus = "ready";
		$scope.userNumber;
		$scope.userNumberValue;
		$scope.aiNumber;
		var ai;
		var checker;
		var checkResult;
		var userHistory = $('#userHistory');
		var aiHistory = $('#aiHistory');

		$scope.userInput = function(){
			if($scope.gameStatus == 'ready'){
				$scope.userNumber = new Numbers(('' + $scope.userNumberValue).substring(0,3));
				$scope.gameStatus = 'started';
				ai = new Ai();
				checker = new Checker();
				userHistory.html("");
				$scope.userNumberValue = "";
			} else if($scope.gameStatus == 'started'){
				if(validNumber($scope.userNumberValue)){
					var userGuess = new Numbers('' + $scope.userNumberValue);
					$scope.userNumberValue = "";
					checkResult = checker.check(ai.aiNumbers, userGuess);
					if(checkResult.isSame) {
						$scope.gameStatus = 'over';
						alert("you win!");
						return;
					}
					userHistory.append(historyHtml(userGuess, checkResult));

					var aiGuessNumbers = ai.getNextGuess();
					checkResult = checker.check(aiGuessNumbers, $scope.userNumber);
					if (checkResult.isSame) {
						$scope.gameStatus = 'over';
						alert("ai win! ai was: " + ai.aiNumbers.at(0) + ai.aiNumbers.at(1) + ai.aiNumbers.at(2));
						return;
					}
					aiHistory.append(historyHtml(aiGuessNumbers, checkResult));
					ai.guessResultIn(aiGuessNumbers, checkResult);
				}
			} else if($scope.gameStatus == 'over'){
				userHistory.html("");
				aiHistory.html("");
				$scope.gameStatus = 'ready';
				$scope.userNumber = undefined;
			}
		};
	};

	function validNumber(n){
		if((""+n).length<=1) return false;
		
		return true;
	}

	function historyHtml(numbers, checkResult){
		return '<p>'
			+ '<p class="rbtn pure-button">' + numbers.at(0) + '</p>'
			+ '<p class="rbtn pure-button">' + numbers.at(1) + '</p>'
			+ '<p class="rbtn pure-button">' + numbers.at(2) + '</p>'
			+ ' -> ' 
			+ '<p class="rbtn pure-button">' + checkResult.strikeCount + 'S ' + checkResult.ballCount + 'B</p>'
			+ '</p>';
	}
	var app = angular
				.module('flask', [])
				.controller('BaseballGameController', BaseballGameController);
})();
