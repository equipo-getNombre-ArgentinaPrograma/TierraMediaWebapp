package inObject;

public class NullUser extends User {

	public static User build() {
		return new NullUser();
	}
	
	public NullUser() {
		super(0, "", 0, 0, "", 0);
	}
	
	public boolean isNull() {
		return true;
	}
	
}
