package db;

public class StudentVo {
	private String sno;
    private String sname;
    private int syear;
    private String gender;
    private String major;
    private int score;
    
	public StudentVo() {
		super();
	}

	public StudentVo(String sno, String sname, int syear, String gender, String major, int score) {
		super();
		this.sno = sno;
		this.sname = sname;
		this.syear = syear;
		this.gender = gender;
		this.major = major;
		this.score = score;
	}

	public String getSno() {
		return sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public int getSyear() {
		return syear;
	}

	public void setSyear(int syear) {
		this.syear = syear;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "StudentVo [sno=" + sno + ", sname=" + sname + ", syear=" + syear + ", gender=" + gender + ", major="
				+ major + ", score=" + score + "]";
	}
    
    
}
