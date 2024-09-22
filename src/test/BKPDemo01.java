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
package test;

import algorithm.BinaryKnapsack;
import algorithm.DynamicSolver;

/** This class is used for testing the {@link algorithm.BinaryKnapsack},
 * including testing the {@link algorithm.BinaryKnapsack#toString()} method.
 */
public class BKPDemo01 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        BinaryKnapsackConstructionTest();
        DynamicSolverTest();
        DynamicSolverTest1();
    }
    
    public static void BinaryKnapsackConstructionTest() {
        // 输出30个"-"，以及test title
        System.out.println("-".repeat(30));
        System.out.println("Binary Knapsack Construction Test");
        System.out.println("-".repeat(30));
        
        // Construct a algorithm.BinaryKnapsack object
        BinaryKnapsack kp = new BinaryKnapsack(10);
        
        // Add three items
        kp.addItem("Sun", 1, 2);
        kp.addItem("Star", 2, 1);
        kp.addItem("Shine", 1, 1);
        
        // Add multiple items
        kp.addItems("Hello", new int[]{2, 3, 4}, new int[]{3, 2, 1});
        
        // Print the kp
        System.out.println(kp.toString());
        System.out.println(kp.toVerboseString());
        
        // Add 7 items named "QQ" with different weights and values
        kp.addItems("QQ", new int[]{1, 2, 3, 4, 5, 6, 7}, new int[]{7, 6, 5, 4, 3, 2, 1});
        
        // Print the kp
        System.out.println(kp.toString());
        System.out.println(kp.toVerboseString());
    }

    public static void DynamicSolverTest() {
        // 输出30个"-"，以及test title
        System.out.println("-".repeat(30));
        System.out.println("Dynamic Solver Test");
        System.out.println("-".repeat(30));

        BinaryKnapsack kp = new BinaryKnapsack(10);
        kp.addItem("Sun", 1, 2);
        kp.addItem("Star", 2, 1);
        kp.addItem("Shine", 1, 1);
        kp.addItems("Hello", new int[]{2, 3, 4}, new int[]{3, 2, 1});
        kp.addItems("QQ", new int[]{1, 2, 3, 4, 5, 6, 7}, new int[]{7, 6, 5, 4, 3, 2, 1});

        // Create a DynamicSolver instance and link the knapsack
        DynamicSolver solver = new DynamicSolver();
        solver.linkBinaryKnapsack(kp);// Check the status after linking
        System.out.println("Solver status after linking:");
        System.out.println(solver.getStatus());
        
        // Optimize the knapsack problem
        boolean result = solver.optimize();
        if (result) {
            System.out.println("Solver status after optimization:");
            System.out.println(solver.getStatus());
            
            // Display the results
            System.out.println("Optimal solution:");
            System.out.println("Optimal value: " + solver.getResultStandard());
            String[] resultString = solver.getResultString();
            System.out.println("Optimal value: " + resultString[0]);
            System.out.println("Items chosen: " + resultString[1]);
        } else {
            System.out.println("Optimization failed.");
        }
    }

    public static void DynamicSolverTest1() {
        // 输出30个"-"，以及test title
        System.out.println("-".repeat(30));
        System.out.println("Dynamic Solver Test1");
        System.out.println("-".repeat(30));

        BinaryKnapsack kp = new BinaryKnapsack(5);
        kp.addItem("Sun", 1, 2);
        kp.addItem("Star", 2, 1);
        kp.addItem("Shine", 1, 1);
        kp.addItems("Hello", new int[]{2, 3, 4}, new int[]{3, 2, 1});

        // Create a DynamicSolver instance and link the knapsack
        DynamicSolver solver = new DynamicSolver();
        solver.linkBinaryKnapsack(kp);// Check the status after linking
        System.out.println("Solver status after linking:");
        System.out.println(solver.getStatus());
        
        // Optimize the knapsack problem
        boolean result = solver.optimize();
        if (result) {
            System.out.println("Solver status after optimization:");
            System.out.println(solver.getStatus());
            
            // Display the results
            System.out.println("Optimal solution:");
            System.out.println("Optimal value: " + solver.getResultStandard());
            String[] resultString = solver.getResultString();
            System.out.println("Optimal value: " + resultString[0]);
            System.out.println("Items chosen: " + resultString[1]);
        } else {
            System.out.println("Optimization failed.");
        }
    }
    
}
