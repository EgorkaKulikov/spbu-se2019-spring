import kotlin.math.abs

fun <K: Comparable<K>>max(a: K?, b: K?): K? = if (b == null || (a != null && a > b)) a else b
fun <K: Comparable<K>>min(a: K?, b: K?): K? = if (b == null || (a != null && a < b)) a else b

open class DebugInfoSubtree<K: Comparable<K>, V, NT: Node<K, V, NT>>(node: Node<K, V, NT>) {
    var minKey = node.key
    var maxKey = node.key
    var isCorrect = true
    init {
        if (node.value == null) {
            isCorrect = false
        }
        if (node.left != null && node != node.left!!.parent) {
            isCorrect = false
        }
        if (node.right != null && node != node.right!!.parent) {
            isCorrect = false
        }
        if (node.type == TypeSon.RightSon && node != node.parent!!.right) {
            isCorrect = false
        }
    }
}

class DebugInfoBSSubtree<K: Comparable<K>, V>(node: BSNode<K, V>): DebugInfoSubtree<K, V, BSNode<K, V>>(node) {
    init {
        if (node.left != null)
        {
            val debugInfoLeftSubtree = DebugInfoBSSubtree(node.left!!)
            if (debugInfoLeftSubtree.maxKey >= node.key)
                isCorrect = false
            minKey = min(minKey, debugInfoLeftSubtree.minKey)!!
            maxKey = max(maxKey, debugInfoLeftSubtree.maxKey)!!
        }
        if (node.right != null)
        {
            val debugInfoRightSubtree = DebugInfoBSSubtree(node.right!!)
            if (debugInfoRightSubtree.minKey <= node.key)
                isCorrect = false
            minKey = min(minKey, debugInfoRightSubtree.minKey)!!
            maxKey = max(maxKey, debugInfoRightSubtree.maxKey)!!
        }
        if (minKey > maxKey)
            isCorrect = false
    }
}

class DebugInfoRBSubtree<K: Comparable<K>, V>(node: RBNode<K, V>): DebugInfoSubtree<K, V, RBNode<K, V>>(node) {
    var colorHeight = 0
    init {
        if (node.left != null)
        {
            val debugInfoLeftSubtree = DebugInfoRBSubtree(node.left!!)
            if (debugInfoLeftSubtree.maxKey >= node.key)
                isCorrect = false
            colorHeight = debugInfoLeftSubtree.colorHeight
            minKey = min(minKey, debugInfoLeftSubtree.minKey)!!
            maxKey = max(maxKey, debugInfoLeftSubtree.maxKey)!!
        }
        if (node.right != null)
        {
            val debugInfoRightSubtree = DebugInfoRBSubtree(node.right!!)
            if (debugInfoRightSubtree.minKey <= node.key)
                isCorrect = false
            if (colorHeight != debugInfoRightSubtree.colorHeight)
                isCorrect = false
            minKey = min(minKey, debugInfoRightSubtree.minKey)!!
            maxKey = max(maxKey, debugInfoRightSubtree.maxKey)!!
        }
        else if (colorHeight != 0)
            isCorrect = false
        if (minKey > maxKey)
            isCorrect = false
        if (node.color == Color.Black)
            colorHeight++
    }
}

class DebugInfoAVLSubtree<K: Comparable<K>, V>(node: AVLNode<K, V>): DebugInfoSubtree<K, V, AVLNode<K, V>>(node) {
    var height = 0
    init {
        if (node.left != null)
        {
            val debugInfoLeftSubtree = DebugInfoAVLSubtree(node.left!!)
            if (debugInfoLeftSubtree.maxKey >= node.key)
                isCorrect = false
            height = debugInfoLeftSubtree.height
            minKey = min(minKey, debugInfoLeftSubtree.minKey)!!
            maxKey = max(maxKey, debugInfoLeftSubtree.maxKey)!!
        }
        if (node.right != null)
        {
            val debugInfoRightSubtree = DebugInfoAVLSubtree(node.right!!)
            if (debugInfoRightSubtree.minKey <= node.key)
                isCorrect = false
            if (abs(height - debugInfoRightSubtree.height) > 1)
                isCorrect = false
            minKey = min(minKey, debugInfoRightSubtree.minKey)!!
            maxKey = max(maxKey, debugInfoRightSubtree.maxKey)!!
        }
        else if (height > 1)
            isCorrect = false
        if (minKey > maxKey)
            isCorrect = false
        height++
    }
}
