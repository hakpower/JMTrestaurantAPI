package model.member;

public class MemberDTO {
	private int num;
	private String m_id;
	private String name;
	private String email;
	private int age;
	private String gender;
	private String reg_date;
	private int level;
	
	public MemberDTO(String m_id, String name, String email, int age, String gender, String reg_date,
			int level) {
		super();
		this.num = num;
		this.m_id = m_id;
		this.name = name;
		this.email = email;
		this.age = age;
		this.gender = gender;
		this.reg_date = reg_date;
		this.level = level;
	}
	
	public MemberDTO(int num, String m_id, String name, String email, int age, String gender, String reg_date,
			int level) {
		super();
		this.num = num;
		this.m_id = m_id;
		this.name = name;
		this.email = email;
		this.age = age;
		this.gender = gender;
		this.reg_date = reg_date;
		this.level = level;
	}
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getM_id() {
		return m_id;
	}
	public void setM_id(String m_id) {
		this.m_id = m_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	@Override
	public String toString() {
		return "{\"num\":" + num + ", \"m_id\":\"" + m_id + "\", \"name\":\"" + name + "\", \"email\":\"" + email + "\", \"age\":" + age
				+ ", \"gender\":\"" + gender + "\", \"level\":\""+level+"\", \"reg_date\":\"" + reg_date + "\"}";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + level;
		result = prime * result + ((m_id == null) ? 0 : m_id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + num;
		result = prime * result + ((reg_date == null) ? 0 : reg_date.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MemberDTO other = (MemberDTO) obj;
		if (age != other.age)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (level != other.level)
			return false;
		if (m_id == null) {
			if (other.m_id != null)
				return false;
		} else if (!m_id.equals(other.m_id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (num != other.num)
			return false;
		if (reg_date == null) {
			if (other.reg_date != null)
				return false;
		} else if (!reg_date.equals(other.reg_date))
			return false;
		return true;
	}
	
	
}
