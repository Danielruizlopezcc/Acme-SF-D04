
package acme.features.auditor.codeAudits;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.auditRecords.Mark;
import acme.entities.codeAudits.CodeAudits;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditsListMineService extends AbstractService<Auditor, CodeAudits> {

	@Autowired
	AuditorCodeAuditsRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<CodeAudits> codeAudits;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		codeAudits = this.repository.findAllCodeAuditsByAuditorId(principal.getActiveRoleId());

		super.getBuffer().addData(codeAudits);
	}

	@Override
	public void unbind(final CodeAudits object) {
		assert object != null;

		Dataset dataset;
		String markMode;

		Collection<Mark> marks = this.repository.findMarksByCodeAuditsId(object.getId());
		markMode = MarkMode.calculate(marks);
		dataset = super.unbind(object, "code", "executionDate", "type");
		dataset.put("markMode", markMode);
		if (object.isDraftMode()) {
			final Locale local = super.getRequest().getLocale();
			dataset.put("draftMode", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("draftMode", "No");

		super.getResponse().addData(dataset);
	}

}
