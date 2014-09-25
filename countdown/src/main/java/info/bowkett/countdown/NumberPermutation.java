package info.bowkett.countdown;

import java.util.*;

import static info.bowkett.countdown.CalculationPermutation.*;
import static info.bowkett.countdown.CalculationPermutation.Operator.*;

/**
 * Created by jbowkett on 24/09/2014.
 */
public class NumberPermutation  {
  private final int[] numbers;
  public NumberPermutation(int... numbers) {
    this.numbers = numbers;
  }

  public int[] getNumbers() {
    return numbers;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final NumberPermutation that = (NumberPermutation) o;

    return Arrays.equals(numbers, that.numbers);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(numbers);
  }

  public List<CalculationPermutation> getCalculationPermutations() {
    final List<CalculationPermutation> calculations = new ArrayList<>();

    final int operatorCount = numbers.length -1;

    final TreeNode root = TreeNode.root();

    for (int level = 0 ; level < operatorCount ; level++){
      root.addOperatorsToAllLeaves();
    }

    final List<TreeNode> leaves = new ArrayList<>();
    root.collectLeaves(leaves);

    for (TreeNode leaf : leaves) {
      final List<Operator> operationsList = new ArrayList<>();
      leaf.collectOperations(operationsList);
      final Operator[] operations = new Operator[operationsList.size()];
      calculations.add(new CalculationPermutation(this, operationsList.toArray(operations)));
    }
    return calculations;
  }

  private static class TreeNode{
    private final Operator op;

    private final TreeNode [] children = new TreeNode[4];
    private final TreeNode parent;

    private TreeNode(TreeNode parent, Operator op) {
      this.op = op;
      this.parent = parent;
    }
    private static TreeNode root(){
      return new TreeNode(null, null);
    }

    private void addOperatorsToAllLeaves() {
      if(isLeaf()){
        children[0] = new TreeNode(this, PLUS);
        children[1] = new TreeNode(this, MINUS);
        children[2] = new TreeNode(this, DIVIDE);
        children[3] = new TreeNode(this, MULTIPLY);
      }
      else{
        for (TreeNode child : children){
          child.addOperatorsToAllLeaves();
        }
      }
    }

    private boolean isLeaf() {
      return children[0] == null;
    }

    private List<TreeNode> collectLeaves(List<TreeNode> leaves) {
      if(isLeaf()){
        leaves.add(this);
      }
      else{
        for (TreeNode child : children){
          child.collectLeaves(leaves);
        }
      }
      return leaves;
    }

    private void collectOperations(List<Operator> operations) {
      if (op != null){
        operations.add(0, op);
        parent.collectOperations(operations);
      }
    }
  }
}
