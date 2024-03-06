
package acme.entities.auditRecords;

public enum Mark {

	A_PLUS("A+"), A("A"), B("B"), C("C"), F("F"), F_MINUS("F-");


	private String value;


	Mark(final String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
