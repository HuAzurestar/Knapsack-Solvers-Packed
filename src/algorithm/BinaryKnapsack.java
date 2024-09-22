/** Copyright (c) 2024 Liangyu Hu
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 */
package algorithm;

// Import java packages
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * The class "BinaryKnapsack" contains several lists of integers, representing
 * the weights, values, and names of items. For example, to add a single item
 * with weight 5 and value 10, you can use the method "addItem" in the form
 * addItem(String name, int weight, int value). Alternatively, to add a list
 * of items with weights [5, 3, 2] and values [10, 8, 6], you can use the method
 * "addItems" in the form addItems(String name, int[] weights, int[] values).
 * 
 * For capacity management, either the "setCapacity" method or the constructor
 * can be used. Finally, an interface should be provided to describe the KP
 * (Knapsack Problem) and facilitate solving it via an external solver within
 * the "algorithm" package.
 * 
 * @author Liangyu Hu
 * @version 2024-09-21
 */
public class BinaryKnapsack {

    // Define attributes
    private List<Integer> weights;              // List of item weights
    private List<Integer> values;               // List of item values
    private Map<String, Integer> itemsLocation; // Map from item name to the item's
                                                //   first location in the list
    private Map<String, Integer> itemsCount;    // Map from item name to its count
    private int capacity;                       // Capacity of the knapsack
    
    /** Initializes the attributes of the BinaryKnapsack object.
     */
    private void __initializeKnapsack() {
        this.weights = new java.util.ArrayList<Integer>();
        this.values = new java.util.ArrayList<Integer>();
        this.itemsLocation = new java.util.LinkedHashMap<String, Integer>();
        this.itemsCount = new java.util.LinkedHashMap<String, Integer>();
        this.capacity = 0;
    }
    
    /** Constructs a empty BinaryKnapsack object.
     * 
     * Set the capacity to 0.
     */
    public BinaryKnapsack() {
        // Initialize attributes
        __initializeKnapsack();
    }
    
    /** Constructs a new BinaryKnapsack object.
     * 
     * @param capacity the capacity of the knapsack
     */
    public BinaryKnapsack(int capacity) {
        // 1. Initialize attributes
        __initializeKnapsack();
        
        // 2. Check capacity
        if (capacity < 0) {
            throw new IllegalArgumentException("Invalid capacity");
        }
        
        // 3. Set the capacity
        this.capacity = capacity;
    }
    
    /** Constructs a new BinaryKnapsack object by standard format.
     * 
     * @param kpStd the standard format of the knapsack
     */
    public BinaryKnapsack(int[] kpStd) {
        // 1. Initialize attributes
        __initializeKnapsack();
        
        // 2.1 Check format of "kpStd"
        if (kpStd.length < 2 || kpStd.length != 2 + 2 * kpStd[0]) {
            throw new IllegalArgumentException("Invalid format of kpStd");
        }

        // 2.2 Check capacity
        if (kpStd[1] < 0) {
            throw new IllegalArgumentException("Invalid capacity");
        }

        // 3.1 Get the number of items and capacity
        int itemsNumber = kpStd[0];
        int capacity = kpStd[1];
        
        // 3.2 Set the capacity
        this.capacity = capacity;
        
        // 3.3 Set the items
        String itemName = "*";
        int[] weights = new int[itemsNumber];
        int[] values = new int[itemsNumber];
        for (int i = 0; i < itemsNumber; i++) {
            weights[i] = kpStd[2 + i];
            values[i] = kpStd[2 + itemsNumber + i];
        }
        
        // 3.4 Add the items
        addItems(itemName, weights, values);
    }
    
    /** @return the capacity of the knapsack
     */
    public int getCapacity() {
        return capacity;
    }
    
    /** Sets the capacity of the knapsack.
     * 
     * @param capacity the capacity of the knapsack
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /** Adds an item with the given name, weight and value.
     * 
     * If the item already exists, an {@code IllegalArgumentException} is thrown.
     * 
     * @param name the name of the item
     * @param weight the weight of the item
     * @param value the value of the item
     */
    public void addItem(String name, int weight, int value) {
        if (itemsLocation.containsKey(name)) {
            throw new IllegalArgumentException(
                    "Item already exists: " + name);
        }
        
        weights.add(weight);
        values.add(value);
        itemsLocation.put(name, this.weights.size() - 1);
        itemsCount.put(name, 1);
    }
    
    /** Adds multiple items with the same name but different values and weights.
     * 
     * If the item already exists or the sizes of the lists don't match,
     * an {@code IllegalArgumentException} is thrown.
     * 
     * @param name the name of the item
     * @param weights the list of weights for the items
     * @param values the list of values for the items
     */
    public void addItems(String name, int[] weights, int[] values) {
        if (itemsLocation.containsKey(name)) {
            throw new IllegalArgumentException(
                    "Item already exists: " + name);
        }
        if (weights.length != values.length) {
            throw new IllegalArgumentException(
                    "Weights and values lists must have the same size");
        }
        
        for (int i = 0; i < weights.length; i++) {
            this.weights.add(weights[i]);
            this.values.add(values[i]);
        }
        itemsLocation.put(name, this.weights.size() - values.length);
        itemsCount.put(name, values.length);
    }
    
    /** @return the weight of the item with the given name and index.
     * @param itemIndex: the NO.itemIndex item of the given name
     * @param itemName
     */
    public int getWeight(String itemName, int itemIndex) {
        if(!itemsLocation.containsKey(itemName)) {
            throw new IllegalArgumentException(
                    "Item with name '" + itemName + "' not found");
        }
        
        return weights.get(itemsLocation.get(itemName) + itemIndex);
    }

    /** @return the value of the item with the given name and index.
     * @param itemIndex: the NO.itemIndex item of the given name
     * @param itemName
     */
    public int getValue(String itemName, int itemIndex) {
        if(!itemsLocation.containsKey(itemName)) {
            throw new IllegalArgumentException(
                    "Item with name '" + itemName + "' not found");
        }
        
        return values.get(itemsLocation.get(itemName) + itemIndex);
    }

    /** @return the total number of items in the knapsack.
     */
    public int getItemsCount() {
        return weights.size();
    }
    
    /** @return If the given item exists in the knapsack, return its
     * number of instances and 0 otherwise.
     */
    public int getItemsCount(String itemName) {
        if (itemsCount.containsKey(itemName)) {
            return itemsCount.get(itemName);
        }

        return 0;
    }
    
    /** @return the weight list of the knapsack
     */
    public int[] getWeights() {
        int[] weights = new int[this.weights.size()];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = this.weights.get(i);
        }
        
        return weights;
    }
    
    /** @return the value list of the knapsack
     */
    public int[] getValues() {
        int[] values = new int[this.values.size()];
        for (int i = 0; i < values.length; i++) {
            values[i] = this.values.get(i);
        }
        
        return values;
    }
    
    /** @return the standard formula of knapsack problem.
     */
    public int[] getStandardFormula() {
        int[] result = new int[2 + 2 * values.size()];
        result[0] = weights.size();
        result[1] = capacity;
        for (int i = 0; i < values.size(); i++) {
            result[2 + 2 * i] = values.get(i);
            result[2 + 2 * i + 1] = weights.get(i);
        }
        
        return result;
    }
    
    /** @return the the description of the knapsack problem at the beginning
     */
    private String __getKnapsackProblemDescriptionStarting(String version) {
        String result = String.format(
                "Knapsack Problem Data (%s): capacity = %d, items count = %d\n",
                version,
                capacity,
                values.size());
        result += String.format(
                "%-20s %-10s %-10s\n",
                "Items[ID/CNT]",
                "Weight",
                "Value");
        
        return result;
    }
    
    /** @return the description of the knapsack problem at the body
     */
    private String __getKnapsackProblemDescriptionBody(Function<Integer, Boolean> stopCriterion) {
        String result = "";
        int itemsShown = 0;
        
        // Loop through each item name in itemsLocation (keys of itemsLocation)
        for (String itemName : itemsLocation.keySet()) {
            // Retrieve currentLocation and currentCount
            int currentLocation = itemsLocation.get(itemName);
            int currentCount = itemsCount.get(itemName);

            // Output currentCount rows for each item, as needed
            for (int i = 0; i < currentCount; i++) {
                if (stopCriterion.apply(itemsShown)) {
                    result += "... (Please try toVerboseString to view all items)\n";
                    return result;
                }

                itemsShown++;
                String itemNameWithCount = currentCount == 1 ? 
                        itemName : String.format("%s[%d/%d]", itemName, i + 1, currentCount);
                result += String.format("%-20s %-10d %-10d\n",
                        itemNameWithCount,
                        weights.get(currentLocation + i),  // Get the weight from the list
                        values.get(currentLocation + i));  // Get the value from the list
            }
        }
        
        return result;
    }
    
    /** Lambda function designed for verbose description of the knapsack problem. */
    private Boolean __stopCriterion_NeverEnding(Integer itemsShown) {
        return false;
    }
    
    /** Lambda function designed for standard description of the knapsack problem. */
    private Boolean __stopCriterion_UntilTenItems(Integer itemsShown) {
        return itemsShown >= 10;
    }
    
    /** @return a string representation of the binary knapsack problem.
     */
    @Override
    public String toString() {
        // Construct the basic result string with capacity and item count
        String result = __getKnapsackProblemDescriptionStarting("Standard");
        
        // Loop through each item name in itemsLocation (keys of itemsLocation)
        result += __getKnapsackProblemDescriptionBody(cnt -> __stopCriterion_UntilTenItems(cnt));
        
        return result;
    }
    
    /** @return a verbose string representation of the binary knapsack problem.
     */
    public String toVerboseString() {
        // Construct the basic result string with capacity and item count
        String result = __getKnapsackProblemDescriptionStarting("Verbose");
        
        // Loop through each item name in itemsLocation (keys of itemsLocation)
        result += __getKnapsackProblemDescriptionBody(cnt -> __stopCriterion_NeverEnding(cnt));
        
        return result;
    }
    
    /** This method checks the internal consistency of the BinaryKnapsack instance.
     * It verifies that the following attributes maintain their consistency:
     * 
     * <ul>
     *   <li>The lengths of {@code weights} and {@code values} must be equal, 
     *       ensuring that each item weight has a corresponding value.</li>
     *   <li>The {@code itemsLocation} map must correctly point to the starting 
     *       index of each item's data in the {@code weights} and {@code values} 
     *       lists.</li>
     *   <li>The {@code itemsCount} map must reflect the correct number of items 
     *       of each type and match the corresponding entries in the 
     *       {@code weights} and {@code values} lists.</li>
     * </ul>
     * 
     * <p>If any inconsistency is detected, the method returns {@code false}, 
     * indicating that an error was found. Otherwise, it returns {@code true}.</p>
     * 
     * @return {@code true} if the internal state is consistent, 
     *         {@code false} otherwise
     */
    public boolean checkConsistency() {
        // Check that the lengths of weights and values are equal
        if (weights.size() != values.size()) {
            return false;
        }
        
        // Check that the itemsLocation and itemsCount maps are consistent with 
        // weights and values
        for (String itemName : itemsLocation.keySet()) {
            int location = itemsLocation.get(itemName);
            int count = itemsCount.get(itemName);
            
            // Ensure that the range [location, location + count) is valid
            if (location < 0 || location + count > weights.size() || 
                location + count > values.size()) {
                return false;
            }
        }
        
        // If all checks pass, the state is consistent
        return true;
    }
    
}
