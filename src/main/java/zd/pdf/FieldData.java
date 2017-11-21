package zd.pdf;
public class FieldData{
	static class  position{
		float left;
		float top;
		float width;
		float height;
		float xPercentage;
		float yPercentage;
		float widthPercentage;
		float heightPercentage;
		
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
	}
	
	position[] positions;
	String name;
	//String text;
	String value;               // text in the textfield
	
	int fieldType;
	int size;                   // number of elements in the datafield 
	boolean checkStatus;

	/*public String getText() {
		return text;
	}*/
	public String getValue() {
		return value;
	}
	
	// 2 represents for checkbox; 4 represents for text field; 7 represents for signature field
	public int getSize() {
		return size;
	}
	public String getName() {
		return name;
	}
	
	public position[] getPositions() {
		return positions;
	}
	
	public void setPositions(position[] positions) {
		this.positions = positions; 
	}
	
	public int getFieldType() {
		return fieldType;
	}
	public void setFieldType(int fieldType) {
		this.fieldType = fieldType;
		
	}
	
	public boolean getCheckStatus(){
		return checkStatus;
	}
	public void setCheckStatus(boolean checkStatus) {
		this.checkStatus = checkStatus;
	}
	
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return (name);
	}
}

