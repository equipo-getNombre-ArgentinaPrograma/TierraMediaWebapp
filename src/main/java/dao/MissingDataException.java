package dao;

public class MissingDataException extends RuntimeException {
	private static final long serialVersionUID = 2143214325435L; //6813697248809460396L;

	public MissingDataException(Exception e) {
		super(e);
	}
}
