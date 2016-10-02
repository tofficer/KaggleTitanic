import java.util.ArrayList;

public class Example {

	int id; //id
	int survived; //label

	ArrayList<String> features; //pclass, name, sex, age, sibsp, parch, ticket, fare, cabin, embarked
	String pclass = "no pclass";
	String name = "no name";
	String sex = "no sex";
	String age = "no age";
	
	String sibsp = "no sibsp";
	String parch = "no parch";
	String numfam = "no numfam";
	
	String ticket = "no ticket";
	String fare = "no fare";
	String cabin = "no cabin";
	String embarked = "no embarked";

	public Example (String row, ArrayList<String> features, int num) {
		this.features = features;

		String[] list = row.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
		
		if (list.length != num) {
			System.out.println("Wrong number of feature values for example");
			System.exit(0);
		}

		double temp_age = -1;
		int temp_sibsp = -1;
		int temp_parch = -1;
		double temp_fare = -1;

		if (num == 12) {
			this.id = Integer.valueOf(list[0].trim());
			this.survived = Integer.valueOf(list[1].trim());
			
			if (!list[2].trim().equals("")) this.pclass = list[2].trim();
			//this.name = list[3].trim();
			if (!list[4].trim().equals("")) this.sex = list[4].trim();
			if (!list[5].trim().equals("")) temp_age = Double.valueOf(list[5].trim());
			if (!list[6].trim().equals("")) temp_sibsp = Integer.valueOf(list[6].trim());
			if (!list[7].trim().equals("")) temp_parch = Integer.valueOf(list[7].trim());
			//this.ticket = list[8].trim();
			//if (!list[9].trim().equals("")) temp_fare = Double.valueOf(list[9].trim());
			//this.cabin = list[10].trim();
			if (!list[11].trim().equals("")) this.embarked = list[11].trim();
		}
		else if (num == 11) {
			this.id = Integer.valueOf(list[0].trim());
			
			if (!list[1].trim().equals("")) this.pclass = list[1].trim();
			//this.name = list[2].trim();
			if (!list[3].trim().equals("")) this.sex = list[3].trim();
			if (!list[4].trim().equals("")) temp_age = Double.valueOf(list[4].trim());
			if (!list[5].trim().equals("")) temp_sibsp = Integer.valueOf(list[5].trim());
			if (!list[6].trim().equals("")) temp_parch = Integer.valueOf(list[6].trim());
			//this.ticket = list[7].trim();
			//if (!list[8].trim().equals("")) temp_fare = Double.valueOf(list[8].trim());
			//this.cabin = list[9].trim();
			if (!list[10].trim().equals("")) this.embarked = list[10].trim();
		}
		
		if (temp_age > 60) this.age = "60+";
		else if (temp_age > 40) this.age = "41-60";
		else if (temp_age > 20) this.age = "21-40";
		else if (temp_age >= 0) this.age = "0-20";

		if (temp_sibsp > 5) this.sibsp = "6+";
		else if (temp_sibsp > 2) this.sibsp = "3-5";
		else if (temp_sibsp >= 0) this.sibsp = "0-2";
		
		if (temp_parch > 5) this.parch = "6+";
		else if (temp_parch > 2) this.parch = "3-5";
		else if (temp_parch >= 0) this.parch = "0-2";

		int temp_numfam = -1;
		if (temp_sibsp != -1 && temp_parch != -1) temp_numfam = temp_sibsp + temp_parch;
		else if (temp_sibsp != -1) temp_numfam = temp_sibsp;
		else if (temp_parch != -1) temp_numfam = temp_parch;

		if (temp_numfam > 8) this.numfam = "8+";
		else if (temp_numfam > 5) this.numfam = "5-8";
		else if (temp_numfam > 0) this.numfam = "0-4";

		// if (temp_fare > 100) this.fare = "100+";
		// else if (temp_fare > 50) this.fare = "51-100";
		// else if (temp_fare >= 0) this.fare = "0-50";

	}

	public String getFeatureValue(String feature_name) {
		if (feature_name.equals("pclass")) return this.pclass;
		if (feature_name.equals("name")) return this.name;
		if (feature_name.equals("sex")) return this.sex;
		if (feature_name.equals("age")) return this.age;
		if (feature_name.equals("sibsp")) return this.sibsp;
		if (feature_name.equals("parch")) return this.parch;
		if (feature_name.equals("ticket")) return this.ticket;
		if (feature_name.equals("fare")) return this.fare;
		if (feature_name.equals("cabin")) return this.cabin;
		if (feature_name.equals("embarked")) return this.embarked;

		return "Not a feature";
	}

}