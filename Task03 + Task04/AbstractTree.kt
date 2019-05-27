import java.util.*

abstract class AbstractTree<K : Comparable<K>, V, N : INode<N>> : ITree<K, V>, Iterable<N>
{
    var root : N? = null
        protected set

    protected abstract fun buildDeque(current : N?, deque : Deque<N>)

    //For tests
    internal fun clear()
    {
        root?.leftChild?.parent = null
        root?.rightChild?.parent = null
        root = null
    }

    override fun iterator(): Iterator<N>
    {
        val deque : Deque<N> = ArrayDeque<N>()
        buildDeque(root, deque)

        return object : Iterator<N>
        {
            override fun next(): N =
                deque.removeFirst()

            override fun hasNext(): Boolean =
                !deque.isEmpty()
        }
    }
}