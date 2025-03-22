// ====================================================================
// CPSC 319, W25, PA-3: Binary Search trees (BST)
//
// Student Information:
//
// ** Full Name: Moyosore William Adebiyi
//
// ** UCID: 30205542
//
// ** Tutorial Section: T06
//
// ====================================================================

import java.io.*;  // Import for file handling
import java.util.*; // Import for utility classes like Scanner


// Represents a node in the Binary Search Tree (BST)
class TreeNode {
    // Data stored in a string
    String word;
    // Count of occurrences of the word
    int count;
    // Pointers to left and right children
    TreeNode left;
    TreeNode right;

    // Constructor initializes a new node with the given word
    public TreeNode(String word) {
        // Assign the input word to the node
        this.word = word.toLowerCase();
        // New word starts with frequency of 1
        this.count = 1;
        // No children
        this.left = null;
        this.right = null;
    }
}


// Binary Search Tree implementation for storing and analyzing words
class BinarySearchTree {
    // Root of the BST
    private TreeNode root = null;

    // Total words inserted (including duplicates)
    private int wordCount = 0;

    // Count of unique words
    private int uniqueCount = 0;

    // Stores the node with the highest frequency
    private TreeNode mostFrequentNode = null;

    // Inserts a word into the Binary Search Tree (BST)
    public void insert(String word) {
        // Calls the recursive function to insert the word, starting from the root node
        root = insertRecursive(root, word);
    }


    // Helper function to recursively insert a word into the BST
    private TreeNode insertRecursive(TreeNode node, String word) {
        // **BST Property**: Smaller words go to the left subtree, larger words go to the right.

        // If the current node is null, insert a new node with the word.
        if (node == null) {
            // Increase the unique word count
            wordCount++;
            uniqueCount++;
            TreeNode newNode = new TreeNode(word);

            if (mostFrequentNode == null || newNode.count > mostFrequentNode.count) {
                mostFrequentNode = newNode;
            }
            // Return the updated node
            return newNode;
        }

        int compare = word.compareTo(node.word);
        // If word is smaller, insert into left subtree
        if (compare < 0) {
            node.left = insertRecursive(node.left, word);
        }
        // Else if word is larger, insert into right subtree
        else if (compare > 0) {
            node.right = insertRecursive(node.right, word);
        }
        // Else the word already exists, so increase its frequency
        else {
            node.count++;
            wordCount++;
        }

        // Update most frequent node if current node's count is higher
        if (mostFrequentNode == null || node.count > mostFrequentNode.count) {
            mostFrequentNode = node;
        }
        // Return the updated node
        return node;
    }

    // Returns the traversal output as a string based on the selected traversal type
    public String getTraversalOutput(int type) {
        // Creates a StringBuilder to store the traversal result
        StringBuilder result = new StringBuilder();

        // Checks the value of 'type' to determine which traversal to perform
        if (type == 1) {
            // If type is 1, perform in-order traversal
            traverseInOrder(root, result);
        } else if (type == 2) {
            // If type is 2, perform pre-order traversal
            traversePreOrder(root, result);
        } else if (type == 3) {
            // If type is 3, perform post-order traversal
            traversePostOrder(root, result);
        } else {
            // If input is invalid, return an error message
            return "ERROR MESSAGE : Input is invalid.";
        }
        return result.toString();
    }

    // TODO
    // Performs in-order traversal (Left, Root, Right)
    private void traverseInOrder(TreeNode node, StringBuilder result) {
        // Check if the current node is not null before processing
        if (node != null) {
            // Recursively traverse the left subtree (Left)
            traverseInOrder(node.left, result);
            // Append the current node's word to the result (Root)
            /// not sure
            if (!result.isEmpty()) {
                result.append(", ");
            }
            result.append(node.word);
            // Recursively traverse the right subtree (Right)
            traverseInOrder(node.right, result);
        }
    }

    // Performs pre-order traversal (Root, Left, Right)
    private void traversePreOrder(TreeNode node, StringBuilder result) {
        // (1) Check if the current node is not null before processing
        if (node != null) {
            // (2) Append the current node's word to the result (Root)
            /// not sure
            if (!result.isEmpty()) {
                result.append(", ");
            }
            result.append(node.word);

            // Recursively traverse the left subtree (Left)
            traversePreOrder(node.left, result);

            // Recursively traverse the right subtree (Right)
            traversePreOrder(node.right, result);
        }

    }

    // Performs post-order traversal (Left, Right, Root)
    private void traversePostOrder(TreeNode node, StringBuilder result) {
        // (1) Check if the current node is not null before processing
        if (node != null) {
            // Recursively traverse the left subtree (Left)
            traversePostOrder(node.left, result);
            // (3) Recursively traverse the right subtree (Right)
            traversePostOrder(node.right, result);
            // (4) Append the current node's word to the result (Root)
            ///  not sure
            if (!result.isEmpty()) {
                result.append(", ");
            }
            result.append(node.word);
        }
    }


    // Computes and returns the total number of words in the BST, including duplicates
    public int getWordCount() {
        // Reset the total word count to 0 before recalculating
        int oldWordCount = wordCount;
        wordCount = 0;
        // Call the helper function to count word occurrences
        countWords(root);
        // Return the computed total number of words
        return wordCount;
    }


    // Helper function to count words recursively in the BST
    private void countWords(TreeNode node) {
        // Check if the current node is not null before processing
        if (node != null) {
            // Add the frequency of the current node's word to the total count
            wordCount += node.count;
            // Recursively traverse left subtree
            countWords(node.left);
            // Recursively traverse right subtree
            countWords(node.right);
        }
    }

    // Returns the count of unique words stored in the BST
    public int getUniqueWords() {
        // Return the total number of unique words
        return uniqueCount;
    }

    // Returns the most frequent word along with its occurrence count
    public String getMostFrequentWord() {
        // Retrieve the word stored in mostFrequentNode
        // If there is no word stored return message
        if (mostFrequentNode == null) {
            return "ERROR MESSAGE : No most frequent word found.";
        }
        // Append the frequency count in the format "word (X times)"
        return mostFrequentNode.word + " (" + mostFrequentNode.count + " times)";
    }

    // Computes and returns the height of the BST
    public int getTreeHeight() {
        // Calls the helper function getHeight() starting from the root
        return getHeight(root);

    }

    // Helper function to calculate the height of the BST recursively
    private int getHeight(TreeNode node) {
        // ** Clarification**: Height is measured in edges, not nodes.
        // ** As per lecture slides: 'Trees - (3) Binary (Part 1) - Levels & Heights'
        // This means:
        // - An empty tree has height -1 (since there are no edges).
        // - A tree with a single node has height 0.

        // Base case: If the tree is empty (null node), return height -1.
        if (node == null) {
            return -1;
        }

        // Get the height of the left subtree.
        int leftHeight = getHeight(node.left);

        // Get the height of the right subtree.
        int rightHeight = getHeight(node.right);

        // Return the maximum height between left and right subtrees + 1.
        return Math.max(leftHeight, rightHeight) + 1; /// Google used to find this function
    }


    // Searches for a word in the BST and returns its frequency
    public int searchWord(String word) {
        // Calls the recursive helper function, starting from the root
        return searchWordRecursive(root, word.toLowerCase());
    }


    // Helper function to recursively search for a word in the BST
    private int searchWordRecursive(TreeNode node, String word) {
        // (1) Base case: If node is null, the word is not in the BST
        if (node == null) {
            return 0;
        }

        int compare = word.compareTo(node.word);
        // If word matches current node, return its frequency

        if (compare < 0) {
            // If word is smaller, search left subtree
            return searchWordRecursive(node.left, word);
        }

        else if (compare > 0) {
            // If word is larger, search right subtree
            return searchWordRecursive(node.right, word);
        }
        return node.count;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter input file name: ");
        String filename = scanner.nextLine(); // Read file name from user input

        BinarySearchTree bst = new BinarySearchTree(); // Create BST instance
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) { // Read file line by line
                // Convert to lowercase, remove non-letter characters, and split into words
                String[] words = line.toLowerCase().replaceAll("[^a-zA-Z ]", "").split("\\s+");
                for (String word : words) {
                    if (!word.isEmpty()) { // Ensure word is not empty
                        bst.insert(word); // Insert word into BST
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage()); // Handle file errors
            return;
        }

        // Display statistics
        System.out.println("Total words: " + bst.getWordCount());
        System.out.println("Unique words: " + bst.getUniqueWords());
        System.out.println("Most frequent word: " + bst.getMostFrequentWord());
        System.out.println("Tree height: " + bst.getTreeHeight());

        while (true) {
            System.out.println("Enter a word to search (or type 'traverse' to display tree, 'exit' to quit): ");
            String input = scanner.nextLine().toLowerCase();

            if (input.equals("exit")) break;
            if (input.equals("traverse")) {
                System.out.println("Choose BST traversal method: 1 = IN-ORDER, 2 = PRE-ORDER, 3 = POST-ORDER");
                String choice = scanner.nextLine();
                try {
                    int traversalType = Integer.parseInt(choice);
                    System.out.println(bst.getTraversalOutput(traversalType));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter 1, 2, or 3.");
                }
                continue;
            }

            int frequency = bst.searchWord(input);
            System.out.println("Word '" + input + "' appears " + frequency + " time(s) in the text.");
        }
    }
}