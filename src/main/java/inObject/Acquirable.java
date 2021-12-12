package inObject;

public interface Acquirable {
	
	public String getName();

	public int getId();

	public double getPrice();

	public double getCompletionTime();

	public String getAttractionType();
	
	public String getDescription();

	public boolean useQuota();

	public boolean isFull();
	
	public boolean isPromotion();

	public boolean shareAttraction(Object object);

	public void printToScreen();

}
