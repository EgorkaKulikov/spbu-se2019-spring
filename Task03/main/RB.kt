package trees

internal enum class Color
{
  RED, BLACK
}

class RBTree<K: Comparable<K>, V>: BalancedSearchTree<K, V>()
{
  inner class RBNode(key: K, value: V, parent: Node?): BalancedNode(key, value, parent)
  {
    internal var color = Color.RED

    internal fun verifyRB(): Pair<Boolean, Int>
    {
      if (color == Color.RED
        && ((left != null && (left as RBNode).color == Color.RED)
          || (right != null && (right as RBNode).color == Color.RED)))
      {
        return Pair(false, 0)
      }
      else
      {
        var leftCorrect: Pair<Boolean, Int>

        if (left == null)
        {
          leftCorrect = Pair(true, 1)
        }
        else
        {
          leftCorrect = (left as RBNode).verifyRB()
        }

        var rightCorrect: Pair<Boolean, Int>

        if (right == null)
        {
          rightCorrect = Pair(true, 1)
        }
        else
        {
          rightCorrect = (right as RBNode).verifyRB()
        }

        val nodeCorrect = (leftCorrect.second == rightCorrect.second)
          && (leftCorrect.first) && (rightCorrect.first)
        
        var nodeHeight = leftCorrect.second
        if (color == Color.BLACK)
        {
          nodeHeight++
        }

        return Pair(nodeCorrect, nodeHeight)
      }
    }

    internal fun getUncle(): RBNode?
    {
      if (parent == parent?.parent?.left)
      {
        return parent?.parent?.right as RBNode?
      }
      else
      {
        return parent?.parent?.left as RBNode?
      }
    }
  }

  override fun createNode(key: K, value: V, parent: Node?): Node =
    RBNode(key, value, parent)

  override fun innerInsert(key: K, value: V): Node
  {
    val oldSize = size
    
    val inserted = super.innerInsert(key, value) as RBNode

    if (size == oldSize)
    {
      return inserted
    }
    else if (inserted.parent == null)
    {
      inserted.color = Color.BLACK
      
      return inserted
    }
    else
    {
      var current = inserted

      while (current.parent != null)
      {
        var parent = current.parent as RBNode
        val uncle = current.getUncle()

        if (parent.color == Color.BLACK)
        {
          return current
        }
        else if (uncle != null && uncle.color == Color.RED)
        {
          parent.color = Color.BLACK
          uncle.color = Color.BLACK
          
          current = parent.parent as RBNode
          current.color = Color.RED
        }
        else
        {
          var grandFather = parent.parent as RBNode

          if (current == parent.right
            && parent == grandFather.left)
          {
            parent.rotateLeft()
            current = current.left as RBNode
          }
          else if (current == parent.left
            && parent == grandFather.right)
          {
            parent.rotateRight()
            current = current.right as RBNode
          }

          parent = current.parent as RBNode
          grandFather = parent.parent as RBNode

          if (current == parent.left)
          {
            grandFather.rotateRight()
          }
          else
          {
            grandFather.rotateLeft()
          }

          parent.color = Color.BLACK
          grandFather.color = Color.RED

          return inserted
        }
      }

      current.color = Color.BLACK

      return current
    }
  }

  internal fun isRBTree(): Boolean
  {
    if (root == null)
    {
      return true
    }
    else
    {
      val rbRoot = root as RBNode
      val correct = (rbRoot.color == Color.BLACK)
        && (rbRoot.verifyRB().first)
      
      return correct 
    }
  }
}
