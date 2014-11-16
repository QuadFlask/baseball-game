package com.flask.baseball;

import java.util.Scanner;

import com.flask.baseball.domain.model.CheckResult;
import com.flask.baseball.domain.model.Numbers;
import com.flask.baseball.domain.service.Ai;
import com.flask.baseball.domain.service.Checker;
import com.flask.baseball.domain.service.NumbersFactory;

public class AiMain {
	public static void main(String[] args) {
		Ai ai = new Ai();
		Checker checker = new Checker();
		Scanner scanner = new Scanner(System.in);
		ai.setAiNumbers(NumbersFactory.createRandom());

		System.out.println("input your number: ");
		Numbers userNumbers = new Numbers(scanner.next());

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
}
