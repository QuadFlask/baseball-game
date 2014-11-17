package com.flask.baseball;

import java.util.Scanner;

import com.flask.baseball.domain.model.CheckResult;
import com.flask.baseball.domain.model.Numbers;
import com.flask.baseball.domain.service.Ai;
import com.flask.baseball.domain.service.Checker;
import com.flask.baseball.domain.service.NumbersFactory;

public class BaseballGameMain {
	private Ai ai;
	private Checker checker;
	private Numbers userNumbers;

	public void start() {
		initGame();

		Scanner scanner = new Scanner(System.in);
		System.out.println("input your number: ");
		userNumbers = new Numbers(scanner.next());

		boolean isUserWin = false;
		while (true) {
			System.out.println("=== Your turn ===");
			String next = scanner.next();
			CheckResult checkResult;

			if (!isUserWin) {
				Numbers userGuess = new Numbers(next);
				checkResult = checker.check(ai.getAiNumbers(), userGuess);
				System.err.print(checkResult);
				System.out.println(" for " + userGuess);
				if (checkResult.isSame()) {
					System.out.println("You Win!");
					System.out.println("Ai number was: " + ai.getAiNumbers());
				}
			}

			System.out.println("                            === Ai turn ===");
			Numbers aiGuessNumbers = ai.getNextGuess();
			checkResult = checker.check(aiGuessNumbers, userNumbers);
			System.out.println("                            ai: " + aiGuessNumbers + " -> " + checkResult);
			if (checkResult.isSame()) {
				System.out.println("Ai Win!");
				return;
			}
			ai.guessResultIn(aiGuessNumbers, checkResult);
		}
	}

	private void initGame() {
		ai = new Ai();
		checker = new Checker();
		ai.setAiNumbers(NumbersFactory.createRandom());
	}

	public static void main(String[] args) {
		new BaseballGameMain().start();
	}
}
