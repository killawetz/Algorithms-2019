package lesson3;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    private static class Node<T> {
        final T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        }
        else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        }
        else {
            assert closest.right == null;
            closest.right = newNode;
        }
        if(headSet != null) {
            if(t.compareTo(headSet.getToElement()) < 0) {
                headSet.add(t);
            }
        }

        if(tailSet != null) {
            if(t.compareTo(tailSet.getFromElement()) >= 0) {
                tailSet.add(t);
            }
        }
        if(subSet != null) {
            if(t.compareTo(subSet.getFromElement()) >= 0 && t.compareTo(subSet.getToElement()) < 0 ) {
                subSet.add(t);
            }
        }
        size++;
        return true;
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    public int height() {
        return height(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    private int height(Node<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     */
    @Override
    public boolean remove(Object o) {
        if(root == null) return false;
        T buffer = (T) o;
        Node<T> closest = find(buffer);
        root = delete(root, closest.value);
        size--;
        return true;
    }
    //Run Time O(h)
    //Memory O(h)

    private Node delete(Node<T> root, T val) {
        if (root == null) return root;
        int comparison = val.compareTo(root.value);
        if (comparison < 0) root.left = delete(root.left, val);
        else if (comparison > 0) root.right = delete(root.right, val);
        else {
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;
            Node<T> bufNode = new Node<>(minimum(root.right));
            bufNode.right = root.right;
            bufNode.left = root.left;
            root = bufNode;
            root.right = delete(root.right, root.value);
        }
            return root;
        }

    private T minimum(Node root)
    {
        T min = (T) root.value;
        while (root.left != null)
        {
            min = (T) root.left.value;
            root = root.left;
        }
        return min;
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        }
        else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        }
        else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }


    public class BinaryTreeIterator implements Iterator<T> {

        private Stack<Node<T>> stack = new Stack<>();



        private BinaryTreeIterator(Node<T> root) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
        }

        /**
         * Проверка наличия следующего элемента
         * Средняя
         */
        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }
        //Run Time O(1)
        //Memory O(h), where h is tree heigh


        /**
         * Поиск следующего элемента
         * Средняя
         */
        @Override
        public T next() {
            Node<T> minimum = stack.pop();
            Node<T> right = minimum.right;
            while(right!=null){
                stack.push(right);
                right = right.left;
            }
            return minimum.value;
        }
        //Run Time O(h)
        //Memory O(h)

        /**
         * Удаление следующего элемента
         * Сложная
         */
        @Override
        public void remove() {
            // TODO
            throw new NotImplementedError();
        }
    }


    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator(root);
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    private CustomSet subSet;
    private CustomSet headSet;
    private CustomSet tailSet;

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        subSet = new CustomSet(toElement, fromElement);
        for (T value : this) {
            if (value.compareTo(fromElement) >= 0 && value.compareTo(toElement) < 0)  {
                subSet.add(value);
            }
        }
        return subSet;
    }
    //Time O(N)
    //Memory O(N)

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        headSet = new CustomSet(toElement, null);
        for(T value : this) {
            if(value.compareTo(toElement) < 0) {
                headSet.add(value);
            }
            }
        return headSet;
    }
    //Time O(N)
    //Memory O(N)

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        tailSet = new CustomSet(null, fromElement);
        for (T value : this) {
            if (value.compareTo(fromElement) >= 0) {
                tailSet.add(value);
            }
        }
        return tailSet;
    }
    //Time O(N)
    //Memory O(N)

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }

    public class CustomSet extends TreeSet {
        private T toElement;
        private T fromElement;

        CustomSet(T toElement, T fromElement) {
            this.toElement = toElement;
            this.fromElement = fromElement;
        }

        private T getToElement() {
            return toElement;
        }

        private T getFromElement() {
            return fromElement;
        }

        @Override
        public boolean add(Object o) {
            T val = (T) o;
            if(fromElement != null && toElement == null) {
                if(val.compareTo(fromElement) >= 0) {
                    super.add(val);
                    BinaryTree.this.add(val);
                    return true;
                }
                    throw new IllegalArgumentException();
                }
            if(toElement != null && fromElement == null) {
                if (val.compareTo(toElement) < 0) {
                    super.add(val);
                    BinaryTree.this.add(val);
                    return true;
                }
                    throw new IllegalArgumentException();
                }
            if (toElement != null && fromElement != null) {
                if(val.compareTo(toElement) < 0 && val.compareTo(fromElement) >= 0) {
                    super.add(val);
                    BinaryTree.this.add(val);
                    return true;
                }
                throw new IllegalArgumentException();
            }
            return true;
        }
    }
}