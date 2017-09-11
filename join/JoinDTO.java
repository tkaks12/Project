package join;

//CREATE TABLE TB_MEMBER(
//	      id varchar(12) NOT NULL PRIMARY KEY,
//	      password varchar(12) NOT NULL,
//	      name varchar(16) NOT NULL,
//	      birth varchar(8) NOT NULL,
//	      gender varchar(1) NOT NULL,
//	      email varchar(50) NULL,
//	      phone varchar(50) NOT NULL
//	      );
public class JoinDTO {
	private String id;
	private String pass;
	private String name;
	private String birth;
	private String gender;
	private String mail;
	private String phone;

	public JoinDTO() {
	}

	public JoinDTO(String id, String pass, String name, String birth, String gender, String mail, String phone) {
		this.id = id;
		this.pass = pass;
		this.name = name;
		this.birth = birth;
		this.gender = gender;
		this.mail = mail;
		this.phone = phone;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
