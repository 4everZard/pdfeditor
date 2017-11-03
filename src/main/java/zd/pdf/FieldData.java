package zd.pdf;


	
	public class FieldData{
	float left;
	float top;
	float width;
	float height;
	float xPercentage;
	float yPercentage;
	float widthPercentage;
	float heightPercentage;
	
	String name;
	String text;
	String value;
	
	public float getLeft() {
		return left;
	}
	public float getTop(){
		return top;
	}
	public float getwidth() {
		return width;
	}
	public float getheight(){
		return height;
	}
	public String getName() {
		return name;
	}
	public float getXPercent() {
		return xPercentage;
	}
	public float getYPercent() {
		return yPercentage;
	}
	public float getWidthPercent() {
		return widthPercentage;
	}
	public float getHeightPercent() {
		return heightPercentage;
	}
	public String getText() {
		return text;
	}
	public String getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return (name);
	}
	}

