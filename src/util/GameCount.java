package util;

/**
 * ��Ϸͳ����
 * @author Administrator
 *
 */
public class GameCount {

	private int usedtime;
	
	private int wrongCount;
	
	private String flagsString;

	
	/**
	 * ��ʼ����Ϸͳ����Ϣ
	 */
	public GameCount() {
		usedtime = 0;
		wrongCount = 0;      
		flagsString="";
	}
	
	
	public void setSuccuse(boolean flagsss)
	{
		if(flagsss)
        	flagsString="��";
        else {
			flagsString="��";
		}
	}
	
	public String getString() {
		return flagsString;
	}
	
	public int getWrongCount() {
		return wrongCount;
	}

	public void setWrongCount(int wrongCount) {
		this.wrongCount = wrongCount;
	}
	
	public void addWrongCount()
	{
		this.wrongCount++;
	}


	public long getuseTime() {
		return usedtime;
	}

	public void setuseTime(int usetime_sec) {
		this.usedtime = usetime_sec;
	}
}
