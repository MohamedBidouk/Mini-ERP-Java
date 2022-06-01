package application;

public class Category {

	String Cat;

	public String getCat() {
		return Cat;
	}

	public void setCat(String cat) {
		Cat = cat;
	}

	public Category(String cat) {
		super();
		Cat = cat;
	}

	@Override
	public String toString() {
		return  Cat+"|" ;
	}
	
}
