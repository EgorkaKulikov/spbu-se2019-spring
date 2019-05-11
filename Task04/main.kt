class Node<K: Comparable<K>, V>
(
  var key: K,
  var value: V
)
{
  var parent: Node<K, V>? = null
  var left: Node<K, V>? = null
  var right: Node<K, V>? = null
}


class Tree<K: Comparable<K>, V>: Iterable<Node<K, V>?>
{
  private var root: Node<K, V>? = null

  public fun insert(key: K, value: V)
  {
    var previous: Node<K, V>? = null
    var current = root

    while (current != null)
    {
      previous = current

      if (current.key <= key)
      {
        current = current.right
      }
      else
      {
        current = current.left
      }
    }

    current = Node(key, value)
    current.parent = previous

    if (previous != null)
    {
      if (previous.key <= key)
      {
       previous.right = current
      }
      else
      {
        previous.left = current
      }
    }

    if (root == null)
    {
      root = current
    }
  }

  public fun find(key: K) : Node<K, V>?
  {
    var current = root

    while (current != null && current.key != key)
    {
      if (current.key <= key)
      {
        current = current.right
      }
      else
      {
        current = current.left
      }
    }

    return current
  }

  override fun iterator(): Iterator<Node<K, V>?>
  {
    return TreeIterator() 
  }

  inner class TreeIterator(): Iterator<Node<K, V>?>
  {
    override public fun hasNext(): Boolean
    {
      if (hasNext == false && next == null)
      {
        initNext()
        
        return hasNext
      }
      else if (hasNext == true)
      {
        return true
      }
      else 
      {
        findNext()
        hasNext = (next != null)
        
        return hasNext
      }
    }
  
    override public fun next(): Node<K, V>?
    {
      hasNext = false
     
      return next
    }

    private var next: Node<K, V>? = null

    private var hasNext: Boolean = false

    private fun initNext()
    {
      next = root
      getLeft()
      hasNext = next != null
    }

    private fun findNext()
    {
      if (next?.right != null)
      {
        next = next?.right
        getLeft()
      }
      else if (next == next?.parent?.left)
      {
        next = next?.parent
      }
      else if (next == next?.parent?.right)
      {
        getRight()
      }
      else
      {
        next = null
      }
    } 

    private fun getLeft()
    {
      while (next?.left != null)
      {
        next = next?.left
      }
    }

    private fun getRight()
    {
      while (next == next?.parent?.right)
      {
        next = next?.parent
      }

      next = next?.parent
    }
  }
}
