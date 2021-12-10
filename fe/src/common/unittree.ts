import {TreeNode} from "primeng/api";

export class UnitTreeModel {

  public convertToTree(data: any[]): TreeNode[] {
    let nodes = [];
    for (const cont of data) {
      nodes.push(this.convertToNode(cont));
    }
    return nodes;
  }

  private convertToNode(cont: any): TreeNode {
    return {
      label: cont.unitName,
      data: cont.unitId,
      leaf: cont.isLeaf === null ? true : false
    };
  }
}