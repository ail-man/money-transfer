package com.ail.revolut.app.bank;

import static com.ail.revolut.app.bank.Currency.RUB;
import com.ail.revolut.app.helper.BaseTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import org.junit.Test;

public class AccountTest extends BaseTest {

	@Test
	public void testNewAccountShouldHaveZeroBalance() throws Exception {
		Account account = Account.create("", Person.create(""), RUB);
		assertThat(account.getBalance(), equalTo(new Money("0", RUB)));
	}

}
