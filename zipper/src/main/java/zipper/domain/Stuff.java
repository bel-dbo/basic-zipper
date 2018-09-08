package zipper.domain;

public class Stuff extends StuffIndex {
	private String description;
	private String details;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	@Override
	public String toString() {
		return getId() + ":" + getProcessTag();
	}

}
