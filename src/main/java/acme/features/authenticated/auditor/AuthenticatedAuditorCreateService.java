
package acme.features.authenticated.auditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.accounts.Principal;
import acme.client.data.accounts.UserAccount;
import acme.client.data.models.Dataset;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuthenticatedAuditorCreateService extends AbstractService<Authenticated, Auditor> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedAuditorRepository repository;

	// AbstractService<Authenticated, Consumer> ---------------------------


	@Override
	public void authorise() {
		boolean status;

		status = !super.getRequest().getPrincipal().hasRole(Auditor.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Auditor auditor;
		Principal principal;
		int userAccountId;
		UserAccount userAccount;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		userAccount = this.repository.findUserAccountById(userAccountId);

		auditor = new Auditor();
		auditor.setUserAccount(userAccount);

		super.getBuffer().addData(auditor);
	}

	@Override
	public void bind(final Auditor auditor) {
		assert auditor != null;

		super.bind(auditor, "firm", "profesionalId", "certifications", "link");
	}

	@Override
	public void validate(final Auditor auditor) {
		assert auditor != null;
	}

	@Override
	public void perform(final Auditor auditor) {
		assert auditor != null;

		this.repository.save(auditor);
	}

	@Override
	public void unbind(final Auditor auditor) {
		Dataset dataset;

		dataset = super.unbind(auditor, "firm", "profesionalId", "certifications", "link");

		super.getResponse().addData(dataset);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}

}
