package info.bowkett.countdown;

import java.util.ArrayList;
import java.util.List;

import static info.bowkett.countdown.Calculation.Operator.*;

/**
 * Created by jbowkett on 29/09/2014.
 */
public class CalculationFactory {

  public List<Calculation> getCalculationsFor(NumberPermutation numberPermutation) {
    final List<Calculation> calculations = new ArrayList<>();

    final int operatorCount = numberPermutation.getNumbers().length -1;

    final TreeNode root = TreeNode.root();

    for (int level = 0 ; level < operatorCount ; level++){
      root.addOperatorsToAllLeaves();
    }

    final List<TreeNode> leaves = new ArrayList<>();
    root.collectLeaves(leaves);

    for (TreeNode leaf : leaves) {
      final List<Calculation.Operator> operationsList = new ArrayList<>();
      leaf.collectOperations(operationsList);
      final Calculation.Operator[] operations = new Calculation.Operator[operationsList.size()];
      calculations.add(new Calculation(numberPermutation, operationsList.toArray(operations)));
    }
    return calculations;
  }

  private static class TreeNode{
    private final Calculation.Operator op;
    private final boolean isRoot;

    private final TreeNode [] children = new TreeNode[4];
    private final TreeNode parent;

    private TreeNode(TreeNode parent, Calculation.Operator op, boolean isRoot) {
      this.op = op;
      this.parent = parent;
      this.isRoot = isRoot;
    }
    private TreeNode(TreeNode parent, Calculation.Operator op) {
      this(parent, op, false);
    }
    private static TreeNode root(){
      return new TreeNode(null, null, true);
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

    private void collectOperations(List<Calculation.Operator> operations) {
      if (!isRoot){
        operations.add(0, op);
        parent.collectOperations(operations);
      }
    }
  }
}
