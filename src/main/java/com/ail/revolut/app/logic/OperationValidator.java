package com.ail.revolut.app.logic;

// TODO may be not need
public class OperationValidator {

	public static boolean validateDepositOperation(long balance, long amount) {
		if (amount <= 0) {
			return false;
		}
		return true;
	}

	public static boolean validateWithdrawOperation(long balance, long amount) {
		return true;
	}

	public static boolean validateTransferOperation(long fromBlance, long toBalance, long amount) {
		return true;
	}
}
