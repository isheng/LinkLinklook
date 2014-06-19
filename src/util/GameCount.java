package util;

/**
 * 游戏统计类
 * @author Administrator
 *
 */
public class GameCount {

	private int usedtime;
	
	private int wrongCount;
	
	private String flagsString;

	
	/**
	 * 初始化游戏统计信息
	 */
	public GameCount() {
		usedtime = 0;
		wrongCount = 0;      
		flagsString="";
	}
	
	
	public void setSuccuse(boolean flagsss)
	{
		if(flagsss)
        	flagsString="是";
        else {
			flagsString="否";
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
