/*
 * TestHarness.java
 *
 * Copyright (C) 2012-2024 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.testing;

import acme.client.helpers.StringHelper;
import acme.client.testing.ApplicationAbstractTest;

public abstract class TestHarness extends ApplicationAbstractTest {

	// Business methods -------------------------------------------------------

	protected void signIn(final String username, final String password) {
		assert !StringHelper.isBlank(username);
		assert !StringHelper.isBlank(password);

		super.requestHome();
		super.clickOnMenu("Sign in");
		super.fillInputBoxIn("username", username);
		super.fillInputBoxIn("password", password);
		super.fillInputBoxIn("remember", "true");
		super.clickOnSubmit("Sign in");
		super.checkCurrentPath("/any/system/welcome");
		super.checkLinkExists("Account");
	}

	protected void signOut() {
		super.requestHome();
		super.clickOnMenu("Sign out");
		super.checkCurrentPath("/any/system/welcome");
		super.checkLinkExists("Sign in");
	}

	protected void signUp(final String username, final String password, final String name, final String surname, final String email, final String phone) {
		assert !StringHelper.isBlank(username);
		assert !StringHelper.isBlank(password);
		assert !StringHelper.isBlank(name);
		assert !StringHelper.isBlank(surname);
		assert !StringHelper.isBlank(email);
		// HINT: phone can be null

		super.requestHome();
		super.clickOnMenu("Sign up");
		super.fillInputBoxIn("username", username);
		super.fillInputBoxIn("password", password);
		super.fillInputBoxIn("confirmation", password);
		super.fillInputBoxIn("identity.name", name);
		super.fillInputBoxIn("identity.surname", surname);
		super.fillInputBoxIn("identity.email", email);
		super.fillInputBoxIn("accept", "true");
		super.clickOnSubmit("Sign up");
		super.checkCurrentPath("/any/system/welcome");
		super.checkLinkExists("Sign in");
	}

}
