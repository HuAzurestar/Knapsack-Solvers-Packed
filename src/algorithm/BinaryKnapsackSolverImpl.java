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

/**
 * The interface "BinaryKnapsackSolver" contains methods to solve the 
 * Binary Knapsack Problem. It allows linking to a BinaryKnapsack instance, 
 * optimizing the problem, and retrieving results.
 * 
 */
public interface BinaryKnapsackSolverImpl {

    /**
     * Links the given BinaryKnapsack instance for solving.
     * @param kp the BinaryKnapsack instance to be linked
     * @return true if the linking was successful, false otherwise
     */
    boolean linkBinaryKnapsack(BinaryKnapsack kp);

    /**
     * Links the standard form of the BinaryKnapsack problem represented as 
     * an array of integers.
     * @param kpStd an array representing the standard form of the BinaryKnapsack 
     * problem
     * @return true if the linking was successful, false otherwise
     */
    boolean linkBinaryKnapsackStandard(int[] kpStd);

    /**
     * Optimizes the linked BinaryKnapsack problem.
     * @return true if the optimization was successful, false otherwise
     */
    boolean optimize();

    /**
     * Returns the current status of the solver.
     * @return a string representing the solver's status
     */
    String getStatus();

    /**
     * Returns the result of the optimization in the standard integer array form.
     * @return an integer array representing the optimized result
     */
    int[] getResultStandard();

    /**
     * TODO: This method should be implemented to return the result in a more
     * appropriate format once finalized.
     */
    // int[] getResult();

    /**
     * Returns the result of the optimization in a List of strings (temporary 
     * placeholder). This method should be revised later for a more appropriate 
     * result format.
     * @return a List of strings representing the result
     */
    String[] getResultString();
}
