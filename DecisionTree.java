import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.lang.Math;
import java.util.LinkedList;
import java.util.Queue;


public class DecisionTree {
	Node root;

	public DecisionTree(HashSet<Example> examples, ArrayList<String> features) {
		this.root = id3(examples, features, "root");
	}

	//use training examples and id3 algorithm to create decision tree
	public Node id3(HashSet<Example> examples, ArrayList<String> features, String value) {
		//check if all examples are classified 0 or 1 -> if so pass back node w that classification
		int classification = classify(examples);
		
		if (classification == 1 || (classification == -1 && features.isEmpty())) return new Node("Survived", value, true);
		else if (classification == 0 || (classification == -2 && features.isEmpty())) return new Node("Died", value, true);

		//otherwise find the best attirbute to split the examples on
		String best_feature = getBestFeature(examples, features);

		HashMap<String,HashSet<Example>> split_map = splitByFeature(examples, best_feature);
		ArrayList<String> features_clone = new ArrayList<>(features);
		features_clone.remove(best_feature);

		Node node = new Node(best_feature, value, false);
		
		//beware that a test set may have a feature value that does not show up in the training set
		//in that case make a branch for that value and have it take on the majority label
		for (HashSet<Example> subset : split_map.values()) {
			//check what split_map.values() are here!
			String best_feature_value = subset.iterator().next().getFeatureValue(best_feature);
			node.children.add(id3(subset, features_clone, best_feature_value));
		}

		return node;
	}

	private int classify(HashSet<Example> examples) {
		int dead = 0;
		int survived = 0;
		for (Example e : examples) {
			if (e.survived == 1) survived++;
			else dead++;
		}
		
		if (dead == 0) return 1; //all survived
		else if (survived == 0) return 0; //all died
		else if (survived > dead) return -1; //no classification but majority survived
		else return -2; //no classification but at least half died
	}

	private String getBestFeature(HashSet<Example> examples, ArrayList<String> features) {
		double min_entropy = Double.MAX_VALUE;
		String best_feature = "none";
		int set_size = examples.size();

		for (String f : features) {
			HashMap<String, int[]> map = new HashMap<>();
			for (Example e : examples) {
				String key = e.getFeatureValue(f);
				int[] value = (map.containsKey(key)) ? map.get(key) : new int[3];
				
				value[0]++; //feature value count
				if (e.survived == 1) value[1]++; //how many surivived w this feature value
				else value[2]++; //how many died w this feature value

				map.put(key, value);
			}

			//want to choose the feature that minimizes the entropy of the next iteration
			double curr_entropy = getEntropy(map, set_size);
			if (curr_entropy < min_entropy) {
				min_entropy = curr_entropy;
				best_feature = f;
			}
		}
		
		return best_feature;
	}

	//make sure math is right on this method!
	private double getEntropy(HashMap<String, int[]> map, int set_size) {
		double entropy = 0;
		//empty strings are in map but still have to go somewhere
		for (int[] counts : map.values()) {
			double subset_size = (double) counts[0];
			double survived = (double) counts[1];
			double died = (double) counts[2];

			double numer = survived*died*Math.log(2)*Math.log(2);
			if (numer == 0) continue;			
			double denom = subset_size*set_size*Math.log(survived/subset_size)*Math.log(died/subset_size);
			entropy += numer/denom;
		}
		//check overflow here
		return entropy;
	}

	private HashMap<String,HashSet<Example>> splitByFeature(HashSet<Example> examples, String feature) {
		HashMap<String,HashSet<Example>> subsets = new HashMap<>();

		for (Example e : examples) {
			String key = e.getFeatureValue(feature);
			HashSet<Example> value = (subsets.containsKey(key)) ? subsets.get(key) : new HashSet<>();
			value.add(e);
			subsets.put(key, value);
		}

		return subsets;
	}

	public void test(HashSet<Example> test_examples) {
		double correct = 0;
		for (Example e : test_examples) {
			System.out.println("ON PASSENGER: " + e.id);
			Node node = this.root;
			boolean no_train_for_test_ex = false; //if there is a test example feature value does not occur in decision tree skip the example
			
			while (!node.isLeaf && !no_train_for_test_ex) {
				String feature = node.name;
				for (Node child : node.children) {
					if (e.getFeatureValue(feature).equals(child.value)) {
						node = child;
						no_train_for_test_ex = !no_train_for_test_ex;
						break;
					}
				}
				no_train_for_test_ex = !no_train_for_test_ex; 
			}

			if ((node.name.equals("Survived") && e.survived == 1) || (node.name.equals("Died") && e.survived == 0)) correct++;
			System.out.println(correct);
		}

		double total = (double) test_examples.size();
		System.out.println(correct/total);
	}
	
	public void printTree() {
		//print an n-ary tree
	}
}