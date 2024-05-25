
package acme.features.manager.projects;

import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.entities.systemconf.SystemConfiguration;
import acme.roles.Manager;

@Service
public class ManagerProjectUpdateService extends AbstractService<Manager, Project> {

	@Autowired
	private ManagerProjectRepository repository;


	@Override
	public void authorise() {
		Boolean status;
		int masterId;
		Project pr;
		Manager manager;

		masterId = super.getRequest().getData("id", int.class);
		pr = this.repository.findOneProjectById(masterId);
		manager = pr == null ? null : pr.getManager();
		status = pr != null && pr.isDraftMode() && super.getRequest().getPrincipal().hasRole(manager);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Project object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneProjectById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Project object) {
		assert object != null;
		super.bind(object, "code", "title", "abstractProject", "indication", "cost", "link");

	}

	@Override
	public void validate(final Project object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Project existing;

			existing = this.repository.findOneProjectByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "manager.project-module.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("indication"))
			super.state(object.isIndication() == false, "indication", "manager.project-module.form.error.existing-fatal-errors");

		if (!super.getBuffer().getErrors().hasErrors("cost")) {
			super.state(object.getCost().getAmount() > 0, "cost", "manager.project-module.form.error.negative-cost");

			List<SystemConfiguration> sc = this.repository.findSystemConfiguration();
			final boolean foundCurrency = Stream.of(sc.get(0).acceptedCurrencies.split(",")).anyMatch(c -> c.equals(object.getCost().getCurrency()));
			super.state(foundCurrency, "cost", "manager.project-module.form.error.currency-not-supported");
		}
	}

	@Override
	public void perform(final Project object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "title", "abstractProject", "indication", "cost", "link", "draftMode");

		if (object.isIndication()) {
			final Locale local = super.getRequest().getLocale();

			dataset.put("indication", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("indication", "No");

		super.getResponse().addData(dataset);
	}
}
