package board;

//CREATE TABLE BOARD (
//NUMBER INT AUTO_INCREMENT PRIMARY KEY,
//ID VARCHAR(12),TITLE VARCHAR(200)
//CONTENTS VARCHAR(500));
public class BoardDTO {
	private int num;
	private String id;
	private String title;
	private String contents;
	private String date;

	public BoardDTO() {
	}

	public BoardDTO(int num, String id, String title, String contents, String date) {
		this.num = num;
		this.id = id;
		this.title = title;
		this.id = contents;
		this.date = date;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "[num=" + num + " title=" + title + ", id=" + id + ", date=" + date + "]";
	}
}
