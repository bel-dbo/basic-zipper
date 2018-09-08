package zipper.domain;

public class StuffIndex {
	private String id;
	private String processTag;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProcessTag() {
		return processTag;
	}
	public void setProcessTag(String processTag) {
		this.processTag = processTag;
	}
	@Override
	public String toString() {
		return id + ";" + processTag;
	}
}
