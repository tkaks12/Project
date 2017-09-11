package schedule;

public class ScheduleVO {
	private String id;
	private String date;
	private int hour;
	private int min;
	private String content;
	private int seed;
	
	public ScheduleVO() {
		
	}

	public ScheduleVO(String id, String date, int hour, int min, String content, int seed) {
		this.id = id;
		this.date = date;
		this.hour = hour;
		this.min = min;
		this.content = content;
		this.seed = seed;
	}

	public void setSeed(int seed) {
		this.seed = seed;
	}
	
	public int getSeed() {
		return seed;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "ScheduleVO [id=" + id + ", date=" + date + ", hour=" + hour + ", min=" + min + ", content=" + content
				+ "]";
	}
}
