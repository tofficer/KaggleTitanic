//Author: Tyler Officer
//Kaggle Titanic dataset

//do in Python next with scikit-learn!

import java.util.HashSet;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.Arrays;

public class Driver {
	public static void main(String[] args) throws IOException, FileNotFoundException {
		if (args.length != 2) {
			System.out.println("Wrong number of arguements");
			System.exit(0);
		}

		File train_file = new File(args[0]);
		File test_file = new File(args[1]);

		HashSet<Example> train_examples = new HashSet<>(); //args[0]
		HashSet<Example> test_examples = new HashSet<>(); //args[1]
		ArrayList<String> features = new ArrayList<>(Arrays.asList("pclass", "sex", "age", "numfam", "embarked"));

		BufferedReader train_br = new BufferedReader(new FileReader(train_file));
		String s = train_br.readLine(); //skip first line of column headers
		while ((s = train_br.readLine()) != null) {
			train_examples.add(new Example(s, features, 12));
		}
		train_br.close();
		System.out.println("Training set done");

		BufferedReader test_br = new BufferedReader(new FileReader(test_file));
		String t = test_br.readLine(); //skip first line of column headers
		while ((t = test_br.readLine()) != null) {
			test_examples.add(new Example(t, features, 11));
		}
		test_br.close();
		System.out.println("Test set done");

		DecisionTree dtree = new DecisionTree(train_examples, features);
		System.out.println("Decision tree created");

		dtree.printTree();
		System.out.println("Tree printed");

		dtree.test(test_examples);
		System.out.println("Complete");
		//make age and fare feature values into ranges
		//cabin?
		//trim stupid features like name, ticket number
	}
}