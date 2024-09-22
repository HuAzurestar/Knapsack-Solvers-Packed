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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DynamicSolver implements BinaryKnapsackSolverImpl {

    // Attributes
    private BinaryKnapsack knapsack;
    private int            status;
    private int[]          optimalSolution;
    private int            optimalValue;
    
    /** The status of the solver, prefixed with "__DS_STATUS_".
     * 
     * There are several status including:
     *     EMPTY:         0 see {@link __DS_STATUS_EMPTY}
     *     LINKED:        1 see {@link __DS_STATUS_LINKED}
     *     INFEASIBLE:    2 see {@link __DS_STATUS_INFEASIBLE}
     *     OPTIMAL:       3 see {@link __DS_STATUS_OPTIMAL}
     *     UNBOUNDED:     4 see {@link __DS_STATUS_UNBOUNDED}
     *     INDETERMINATE: 5 see {@link __DS_STATUS_INDETERMINATE}
     * 
     * @author Liangyu Hu
     * @version 2024-09-21
     */
    static private final int __DS_STATUS_EMPTY = 0;
    static private final int __DS_STATUS_LINKED = 1;
    static private final int __DS_STATUS_INFEASIBLE = 2;
    static private final int __DS_STATUS_OPTIMAL = 3;
    static private final int __DS_STATUS_UNBOUNDED = 4;
    static private final int __DS_STATUS_INDETERMINATE = 5;
    
    /** Initialize the attributes of the solver.
     */
    private void __initializeSolver() {
        this.knapsack = null;
        this.status = __DS_STATUS_EMPTY;
        this.optimalSolution = null;
        this.optimalValue = 0;
    }
    
    /** Link the given BinaryKnapsack object to this solver.
     * 
     * Check if the solver is not empty before linking. If so, unlink it first.
     * 
     * @param kp: the BinaryKnapsack object to be linked.
     */
    private void __linkBinaryKnapsack(BinaryKnapsack kp) {
        // unlink if this solver is not empty
        if (this.status != __DS_STATUS_EMPTY) {
            __initializeSolver();
        }
        
        // link
        this.knapsack = kp;
        this.status = __DS_STATUS_LINKED;
    }
    
    /** Create an empty solver, necessary for linking problem.
     */
    public DynamicSolver() {
        // initialize
        __initializeSolver();
    }
    
    /** Create a solver with a given BinaryKnapsack object.
     * 
     * @param kp: the BinaryKnapsack object
     */
    public DynamicSolver(BinaryKnapsack kp) {
        // initialize and link
        __initializeSolver();
        linkBinaryKnapsack(kp);
    }
    
    /** Create a solver with a standard form of a BinaryKnapsack.
     * 
     * @param kpStd: the standard form of the BinaryKnapsack
     */
    public DynamicSolver(int[] kpStd) {
        // initialize and link
        __initializeSolver();
        linkBinaryKnapsackStandard(kpStd);
    }
    
    /** Link the given BinaryKnapsack object to this solver.
     * 
     * @param kp: the BinaryKnapsack object
     */
    @Override
    public boolean linkBinaryKnapsack(BinaryKnapsack kp) {
        // link the given BinaryKnapsack object to this solver.
        __initializeSolver();
        __linkBinaryKnapsack(kp);
        return true;
    }
    
    /** Link the standard form of the BinaryKnapsack problem represented as 
     * an array of integers.
     * 
     * @param kpStd an array representing the standard form of the BinaryKnapsack 
     * problem
     */
    @Override
    public boolean linkBinaryKnapsackStandard(int[] kpStd) {
        // construct a BinaryKnapsack object by using kpStd and link it.
        BinaryKnapsack kp = new BinaryKnapsack(kpStd);
        __initializeSolver();
        __linkBinaryKnapsack(kp);
        return true;
    }
    
    /** Optimize the problem using a dynamic programming method.
     * 
     * 1. Initialize dp and rc arrays to store the maximum value achievable and 
     *    the index of the last item added for each capacity.
     * 2. Populate the dp and rc arrays using dynamic programming, by checking 
     *    for each item if it improves the current solution for a given capacity.
     * 3.1. Trace back from the final capacity to identify the items that 
     *    contribute to the optimal solution.
     * 3.2. Store the result in the optimalSolution array.
     * 
     * @return true if the optimization is successful, false otherwise
     */
    @Override
    public boolean optimize() {
        if (this.status != __DS_STATUS_LINKED) {
            return false; // Return false if the knapsack is not properly linked
        }
        
        // 1. Initialize dp array and rc array
        int capacity = this.knapsack.getCapacity();
        int itemCount = this.knapsack.getItemsCount();
        int[] weights = this.knapsack.getWeights();
        int[] values = this.knapsack.getValues();
        
        // dp[i][j] represents the maximum value achievable with the first i items and capacity j
        int[][] dp = new int[itemCount + 1][capacity + 1]; 
        
        // rc array to track item selection, rc[i][j] is true if the i-th item is included for capacity j
        boolean[][] rc = new boolean[itemCount + 1][capacity + 1]; 

        // 2. Dynamic programming loop to fill dp and rc arrays
        for (int i = 1; i <= itemCount; i++) {
            for (int j = 0; j <= capacity; j++) {
                // If we do not take the current item
                dp[i][j] = dp[i - 1][j];

                // If we take the current item and it fits in the knapsack
                if (j >= weights[i - 1]) {
                    int tmp = dp[i - 1][j - weights[i - 1]] + values[i - 1];
                    if (tmp > dp[i][j]) {
                        dp[i][j] = tmp;
                        rc[i][j] = true; // Mark this item as taken
                    }
                }
            }
        }

        // 3.1. Trace back the optimal solution using rc array
        List<Integer> solutionIndices = new ArrayList<>();
        int remainingCapacity = capacity;
        for (int i = itemCount; i > 0; i--) {
            if (rc[i][remainingCapacity]) {
                solutionIndices.add(i - 1); // Add item index (adjusted for 0-based indexing)
                remainingCapacity -= weights[i - 1]; // Reduce remaining capacity
            }
        }

        // 3.2. Store the result in optimalSolution and set optimalValue
        this.optimalSolution = new int[solutionIndices.size()];
        for (int i = 0; i < solutionIndices.size(); i++) {
            this.optimalSolution[i] = solutionIndices.get(solutionIndices.size() - 1 - i); // Reverse order for correct solution
        }
        this.optimalValue = dp[itemCount][capacity];  // Store the best achievable value
        
        // Set status to optimized
        this.status = __DS_STATUS_OPTIMAL;
        return true;
    }

    /** Returns a verbose description depend on the status of the solver.
     * 
     * EMPTY: No knapsack is linked.
     * LINKED: A knapsack is linked.
     * OPTIMAL: An optimal solution has been found.
     * 
     * @return a verbose description about the status.
     */
    @Override
    public String getStatus() {
        String status = "";
        if (this.status == __DS_STATUS_EMPTY) {
            status = "EMPTY\n";
        } else if (this.status == __DS_STATUS_LINKED) {
            status = "LINKED\n";
            
            // Add information about the knapsack
            status += "The linked knapsack is:\n";
            status += this.knapsack.toString();
        } else if (this.status == __DS_STATUS_OPTIMAL) {
            status = "OPTIMAL\n";

            // Add information about the knapsack
            status += "The linked knapsack is:\n";
            status += this.knapsack.toString();
            
            // Add information about the optimal solution
            status += String.format("Optimal value: %d\n", this.optimalValue);
            status += String.format("Optimal solution: %s\n", Arrays.toString(this.optimalSolution));
        } else {
            status = "UNKNOWN\n";
        }
        return status;
    }

    /** @return the optimal value
     */
    @Override
    public int[] getResultStandard() {
        return new int[] { this.optimalValue };
    }
    
    /** Returns the result of the optimization in a List of strings (temporary 
     * placeholder). This method should be revised later for a more appropriate 
     * result format.
     * 
     * @return a List of strings representing the result
     */
    @Override
    public String[] getResultString() {
        String result = "";
        int[] solution = this.optimalSolution;

        for (int i = 0; i < solution.length; i++) {
            result += String.format("%d ", solution[i]);
        }
        return new String[] { String.valueOf(this.optimalValue), result };
    }

}
