import java.util.Map;
import java.util.SortedMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;
import java.util.LinkedList;
import java.util.Comparator;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.io.Serializable;

/**
 * This class is a template implementation of the data structure known as <code>balanced binary heap</code>.<br>
 * It is designed to fit into the Java Collection framework, introduced in Java SDK 1.2.<br>
 * <p>A heap is data structure with an order in the items, but this order is not as strict as 
 * e.g. a sorted list.<br>
 * If it is shown as a tree, each node has two children and each child has to be smaller (for a
 * min-heap) or greater than its parent node (for a max-heap); through this the root node will be 
 * the smallest / greatest node in the whole tree.<br>
 * This is a very good feature if you want to implement a priority queue where you are only 
 * interested in the item with the highest or lowest priority.
 * <p>Preserving this structure is rather inexpensive compared to a totally ordered list.
 * Inserting a new item takes O(lg <em>n</em>) operations, deleting from the top as well 
 * and looking at the top element can be done in constant time = O(1).
 * <p>Since all elements have to be in a certain order, either a <code>Comparator</code> has
 * to be set or the elements must have an implicite natural order
 * (e.g. implement the interface <code>Comparable</code>).
 * <p><b>Note that this implementation is not synchronised.</b>
 * <p>Like a map, this data structure stores some kind of "key" (here: the priority) for each 
 * given data element (a.k.a. satellite data). The priority does not have to be unique, but must
 * be comparable (maybe with the help of a <code>Comparator</code>).
 * <p>There is no such class in the Collection Framework introduced in JDK1.2 till 1.4 - but there
 * is one within the Apache Jakart-Project 
 * (see <a href="http://jakarta.apache.org/commons/collections/api/index.html">BinaryHeap</a>) - which 
 * requires the use of wrappers like <code>java.lang.Integer</code> for primitive datatypes like 
 * <code>int</code>, <code>long</code>, <code>double</code>, and <code>float</code>. If you need 
 * something for those type, consider using a subclass of <code>AbstractFixedPriorityTypeHeap</code>.
 * <p>In version 1.5 (a.k.a. Java 5 a.k.a. Tiger) SUN not only introduced generics (something like templates in C++), but 
 * also added a class <code>PriorityQueue</code> to the package <code>java.util</code>, which is 
 * based on a heap data structure and will solve problems in most cases much more elagant than 
 * this class does! Please check <a href="http://java.sun.com/j2se/1.5.0/">the Java homepage</a> first whether 
 * <code>java.util.PriorityQueue</code> is not the thing you're <i>really</i> looking for!
 * To my best knowledge, SUN's PriorityQueue is not measurably faster than my implementation - 
 * but I guess it has been tested much better and can be used more flexible because of the generics concept.
 * <p>To all others: Feel free to use this code in whatever way you like! (But don't blame me for anything.)<BR>
 * The complete <b>source code</b> should be available at
 * <a href="http://www.michaelspringmann.de/programs/algorithms/HeapJava/src/">this URL</a>.<BR>
 * To the students trying to find an easy way to get homework in a Computer Science course done:
 * this code is far too complex to convince your professor that you did it last night - look somewhere else 
 * or -even better- do your programming assignments on your own.
 * <p>In case you want to use this data structure to implement 
 * <A HREF="http://en.wikipedia.org/wiki/Heapsort" TARGET="_blank">Heapsort</a>, 
 * please read my remarks on that
 * in <a href="http://www.michaelspringmann.de/programs/algorithms/HeapJava/doc/HeapTimeTest.html">HeapTimeTest</a>
 * @see java.util.Collection
 * @see java.util.List
 * @see java.lang.Comparable
 * @see java.util.Comparator
 * @see java.util.Map
 * @see Heap.HeapElement
 * @see AbstractFixedPriorityTypeHeap
 * @version 1.1
 * @author Michael Springmann
 */
public abstract class Heap implements Collection, Cloneable, Serializable
{
  /** used only for serialization */
  static final long serialVersionUID = 4772146584980607812L;

  /** indicates the position of the first element in internal structure */
  protected final static int FIRST_INDEX = 0;		// index of first element in list

  /** used for internal indication of an error */
  protected final static int NO_SUCH_INDEX = -1;	// indicates that there is no child or parent

  /** the internal data structure used for storing the elements */
  protected List elements;

  /** 
   * the used comparator for ordering the elements
   * <p>There is no sense in switching it while the heap 
   * is not empty because it would leave the data in a completly 
   * undetermined order. Hence the datastructure would be destroyed.
   * <p>For changing the comparator, create a new heap with the 
   * desired comparator and add then all elements to it with the 
   * <code>addAll(Collection c)</code> method.
   * @see Heap#addAll(Collection)
   */
  protected final Comparator COMP;

  /**
   * the used strategy for ordering the elements
   * <p>It's the first thing a new (non-abstract) heap like <code>MinHeap</code> or 
   * <code>MaxHeap</code> should internally set in there constructor to specify their
   * behavior.
   * <p>There is no sense in switching it while the heap 
   * is not empty because it would leave the data in a completly 
   * undetermined order. Hence the datastructure would be destroyed.
   * <p>For changing the strategy, create a new heap with the 
   * desired strategy and add then all elements to it with the 
   * <code>addAll(Collection c)</code> method.
   * @see Heap#addAll(Collection)
   * @see Heap.MinHeap
   * @see Heap.MaxHeap
   */
  protected HeapStrategy theStrategy;

  /**
   * Constructs an empty heap.<br>
   * This is an abstract class that cannot be instantiated directly.<br>
   * Use a subclass that fully specifies its behaviour.
   * @see Heap.MinHeap
   * @see Heap.MaxHeap
   */
  public Heap()
  {
    elements = new ArrayList();	// use a list as the internal dynamic data structure
    this.COMP = UseNaturalOrder.getComparator();
  }

  /**
   * Constructs an empty heap with the specified initial capacity.
   * @param initialCapacity	the initial capacity of the heap
   */
  protected Heap(int initialCapacity)
  {
    elements = new ArrayList(initialCapacity);
    this.COMP = UseNaturalOrder.getComparator();
  }

  /**
   * Constructs a heap containing all elements of the specified collection.
   * @param c	the collection that should inserted into the heap. 
   *            Since no comparator is given, all elements of the collection need to be comparable with each other.
   * @throws ClassCastException	is any element does not implement java.lang.Comparable
   */
  protected Heap(Collection c)
  {
    this(c.size());
    addAll(c);
  }

  /**
   * Constructs an empty heap using a specific comparartor to keep the elements in their order.
   * @param comp	the comparartor to order the elements
   */
  protected Heap(Comparator comp)
  {
    elements = new ArrayList();	// use a list as the internal dynamic data structure
    this.COMP = comp;
  }

  /**
   * Constructs an empty heap using a specific comparartor and initial capacity.
   * @param comp		the comparartor to order the elements
   * @param initialCapacity	the initial capacity of the heap
   */
  protected Heap(Comparator comp, int initialCapacity)
  {
    elements = new ArrayList(initialCapacity);
    if (comp == null) {
      this.COMP = UseNaturalOrder.getComparator();
    } else {
      this.COMP = comp;
    }
  }

  /**
   * Constructs a heap containing all elements of the specified sorted map.
   * @param map		the sorted map that should inserted into the heap 
   * 			(will use the comparartor of the sorted map)
   */
  protected Heap(SortedMap map)
  {
    this(map.comparator(), map);
  }

  /**
   * Constructs a heap containing all elements of the specified map using the specified comparator.
   * @param comp	the comparartor to order the elements (if <code>null</code> natural order will be tried)
   * @param map		the map that should inserted into the heap
   */
  protected Heap(Comparator comp, Map map)
  {
    this(comp, map.size());
    Map.Entry entry;
    Iterator itr = map.entrySet().iterator(); 
    while (itr.hasNext())
    {
      entry = (Map.Entry) itr.next();
      add(new HeapElement(entry.getKey(), entry.getValue()));
    }
  }

  /**
   * internally used for cloning
   * @param elements	the elements of the new heap
   * @param comp	the comparator used by the new heap 
   *                    (<code>null</code> represents natural order of elements)
   */
  protected Heap(List elements, Comparator comp)
  {
    this.elements = elements;
    this.COMP = comp;
  }

  /**
   * returns a clone of this heap
   * @return	a clone of this heap
   */
  public abstract Object clone();

  /**
   * returns the number of elements in the heap
   * @return	the number of elements
   */
  public int size()
  {
    return elements.size();	// use information from internal data structure
  }

  /**
   * Sets the (minimum) size of this heap.
   * If greater than actually used size, capacity is adjusted to this value.
   * If the new size is less than the current size, all elements at index newSize and greater are discarded.
   * @param newSize	the new size of the heap
   */
  public void setSize(int newSize)
  {
    if (elements instanceof ArrayList) 
    { // little wierd to do since ArrayList does not offer setSize() itself, Vector does.
      if (newSize > size()) {
        ((ArrayList)elements).ensureCapacity(newSize);
      } else {
        int last = size();
        ArrayList al = (ArrayList) elements;
        while (--last >= newSize)
          al.remove(last);
      }
    } else if (elements instanceof Vector) {
      ((Vector)elements).setSize(newSize);
    } else if (elements instanceof LinkedList) {
      if (newSize < size()) {
        LinkedList ll = (LinkedList) elements;
        while (size() > newSize)
          ll.removeLast();
      }
      // otherwise ignore setting the size
    } else
      throw new UnsupportedOperationException();
  }

  /** Trims the capacity of this heap to be the heap's current size. */
  public void trimToSize()
  {
    if (elements instanceof ArrayList)
      ((ArrayList)elements).trimToSize();
    else if (elements instanceof Vector)
      ((Vector)elements).trimToSize();
    if (!(elements instanceof LinkedList))
      throw new UnsupportedOperationException();
  }

  protected List getElementsClone()
  {
    if (elements instanceof ArrayList)
      return (List) ((ArrayList)elements).clone();
    if (elements instanceof Vector)
      return (List) ((Vector)elements).clone();

    // else: use general approach that works for sure
    return new ArrayList(elements);
  }

  /** Discards everything from the heap. */
  public void clear()
  {
    elements.clear();
  }

  /** 
   * Returns the comparator used to arrange the elements according to their priority. 
   * @return	the comparator used, <code>null</code> if natural order is used.
   */
  public Comparator comparator()
  {
    if (COMP.equals(UseNaturalOrder.getComparator())) {
      return null;
    } else {
      return COMP;
    }
  }

  /**
   * Returns the first data element from the heap
   * @return	the data of the heap element on top of the heap.
   * @throws NoSuchElementException	if heap is empty
   */
  public Object firstElement()
  {
    return theStrategy.firstHeapElement().data;
  }

  /**
   * Returns the first priority from the heap. This will be the smallest value within the whole heap 
   * if it is a min-heap, the biggest value if it is a max-heap.
   * @return	the priority of the heap element on top of the heap.
   * @throws NoSuchElementException	if heap is empty
   * @see Heap.MinHeap
   * @see Heap.MaxHeap
   */
  public Object firstElementPriority()
  {
    return theStrategy.firstHeapElement().priority;
  }

  /**
   * Returns the first data element from the heap. Same as firstElement().
   * @return	the data of the heap element on top of the heap.
   * @throws NoSuchElementException	if heap is empty
   */
  public Object peek()
  {
    return firstElement();
  }

  /**
   * Removes the first element of the heap and return its data.
   * @return	the data of the heap element on top of the heap.
   * @throws NoSuchElementException	if heap is empty
   */
  public Object pop()
  {
    return theStrategy.removeFirstElement().data;
  }

  /**
   * Adds a single element to the heap.
   * @param priority	the priority of the data item, according to which it will be arranged in the heap structure
   * @param data	the data item
   * @throws ClassCastException	if priority cannot be compared to the priority of other elements in the heap
   */
  public void push(Comparable priority, Object data)
  {
    theStrategy.push(new HeapElement(priority, data));
  }

  /**
   * Adds a single element to the heap.
   * @param o	the object that should be stored in the heap. It needs to have some order criteria,
   *            e.g. the used comparator must be capable of comparing it 
   *            or if no comparator is used it must implement the interface Compararble.<br>
   *            In the last case the first element added to a heap restricts which kinds of elements
   *            will be accepted in future adding (only elements that can be compared to the first one).
   * @return	true, if adding was successful
   * @throws ClassCastException	if new element cannot be compared to the one on top of the heap
   */
  public boolean add(Object o)
  {
    theStrategy.push(new HeapElement(o));
    return true;
  }

  /**
   * Adds all elements of the Collection to the heap using add().
   * @param c	the collection that should be added to the heap
   * @return	true, if adding was successful
   * @throws ClassCastException	if new element cannot be compared to the one on top of the heap
   */
  public boolean addAll(Collection c)
  {
    Iterator itr = c.iterator();
    while (itr.hasNext())
    {
      add(itr.next());
    }
    return true;
  }

  /**
   * Deletes the element on top of the heap.
   */
  public void deleteFirstElement()
  {
    try {
      theStrategy.removeFirstElement();
    } catch (NoSuchElementException nsee) {
      // also fine, nothing to do
    }
  }

  /**
   * Returns whether the heap contains no elements.
   * @return	true if the heap does not contain any elements, otherwise false
   */
  public boolean isEmpty()
  {
    return elements.isEmpty();
  }

  /**
   * Since the heap is ordered to some extend that would be hard to keep,
   * removing an item different from the top element is not supported!
   * @throws UnsupportedOperationException	if object is not the top element
   */
  public boolean remove(Object o)
  {
    if (o == peek() || o == firstElement()) {
      theStrategy.removeFirstElement();
      return true;
    } else {
      throw new UnsupportedOperationException();
    }
  }

  /**
   * Since the heap is ordered to some extend that would be hard to keep,
   * this feature is not supported!
   * @throws UnsupportedOperationException	since not supported
   */
  public boolean removeAll(Collection c)
  {
    throw new UnsupportedOperationException();
  }

  /**
   * Since the heap is ordered to some extend that would be hard to keep,
   * this feature is not supported!
   * @throws UnsupportedOperationException	since not supported
   */
  public boolean retainAll(Collection c)
  {
    throw new UnsupportedOperationException();
  }

  /**
   * Returns an iterator over the elements in this collection.
   * There are no guarantees concerning the order in which the elements are returned
   * @return	an Iterator over the heap elements
   * @see Heap.HeapElement
   */
  public Iterator iterator()
  {
    return elements.iterator();
  }

  /**
   * Returns an array containing all of the elements in this collection.
   * If the collection makes any guarantees as to what order its elements are returned by its iterator,
   * this method must return the elements in the same order.
   * @return an array containing all of the elements in this collection
   * @see java.util.List#toArray()
   */
  public Object[] toArray()
  {
    return elements.toArray();
  }

  /**
   * Returns an array containing all of the elements in this collection
   * whose runtime type is that of the specified array.
   * If the collection fits in the specified array, it is returned therein.
   * Otherwise, a new array is allocated with the runtime type of the specified array
   * and the size of this collection.
   * @return				an array containing the elements of this collection
   * @throws ArrayStoreException	the runtime type of a is not a supertype of the runtime type of every element in this heap (= Heap.HeapElement).
   * @see java.util.List#toArray(Object[])
   */
  public Object[] toArray(Object[] a)
  {
    return elements.toArray(a);
  }

  /**
   * Returns true if this collection contains the specified element.
   * @param o	element whose presence in this collection is to be tested
   * @return	true if either a heap element or the data of a heap element equals the object
   */
  public boolean contains(Object o)
  {
    Iterator itr = elements.iterator();
    boolean found = false;	// assume not contained
    HeapElement e;

    while (itr.hasNext())
    {
      e = (HeapElement) itr.next();
      if ( e==o || (o == null && e.data == null ) || o.equals(e.data))
      {
        found = true;
        break;
      }
    }
    return found;
  }

  /**
   * Returns true if this collection contains all of the elements in the heap
   * @param c	collection to be checked for containment in this collection
   * @return 	true if this collection contains all of the elements in the heap
   */
  public boolean containsAll(Collection c)
  {
    Iterator itr = c.iterator();
    boolean found = true;		// assume contained

    while (found && itr.hasNext())
    {
      found = contains(itr.next());	// if not contained, looping will and found = false will be returned
    }
    return found;
  } 

  /**
   * Internally used class to store priority together with data in one Object.
   */
  public static class HeapElement implements Serializable
  {
    /** used only for serialization */
    static final long serialVersionUID = 7308398554988091095L;

    /** the priority according to which the heap element will be placed in the internal data structure */
    Object priority;

    /** the satellite data */
    Object data;

    /**
     * Constructs a new HeapElement, where priority and data are the same
     * @param data	the data item, that needs to have some kind of implicite natural order
     */
    HeapElement(Object data)
    {
      this(data, data);
    }

    /**
     * Constructs a new HeapElement is the specified priority and data
     * @param priority	the priority that defines the order of elements in the heap 
     * @param data	the data item
     */
    HeapElement(Object priority, Object data)
    {
      this.priority = priority;
      this.data = data;
    }
  }

  /**
   * A heap where always the element with the lowest priority is on top.
   * @see Heap
   */
  public static class MinHeap extends Heap
  {
    /** used only for serialization */
    static final long serialVersionUID = 7244284139712657637L;

    /**
     * Constructs an empty heap.<br>
     * More details are explained in Heap.
     * @see Heap
     */
    public MinHeap()
    {
      super();
      theStrategy = new MinOnTopStrategy(this);
    }

    /**
     * Constructs an empty heap with the specified initial capacity.
     * @param initialCapacity	the initial capacity of the heap
     */
    public MinHeap(int initialCapacity)
    {
      super(initialCapacity);
      theStrategy = new MinOnTopStrategy(this);
    }

    /**
     * Constructs a heap containing all elements of the specified collection.
     * @param c	the collection that should inserted into the heap. 
     *            Since no comparator is given, all elements of the collection need to be comparable with each other.
     * @throws ClassCastException	is any element does not implement java.lang.Comparable
     */
    public MinHeap(Collection c)
    {
      super(c);
      theStrategy = new MinOnTopStrategy(this);
    }

    /**
     * Constructs an empty heap using a specific comparartor to keep the elements in their order.
     * @param comp	the comparartor to order the elements
     */
    public MinHeap(Comparator comp)
    {
      super(comp);
      theStrategy = new MinOnTopStrategy(this);
    }

    /**
     * Constructs an empty heap using a specific comparartor and initial capacity.
     * @param comp		the comparartor to order the elements
     * @param initialCapacity	the initial capacity of the heap
     */
    public MinHeap(Comparator comp, int initialCapacity)
    {
      super(comp, initialCapacity);
      theStrategy = new MinOnTopStrategy(this);
    }

    /**
     * Constructs a heap containing all elements of the specified sorted map.
     * @param map		the sorted map that should inserted into the heap 
     *				(will use the comparartor of the sorted map)
     */
    public MinHeap(SortedMap map)
    {
      super(map);
      theStrategy = new MinOnTopStrategy(this);
    }

    /**
     * Constructs a heap containing all elements of the specified map using the specified comparator.
     * @param comp	the comparartor to order the elements (if <code>null</code> natural order will be tried)
     * @param map		the map that should inserted into the heap
     */
    public MinHeap(Comparator comp, Map map)
    {
      super(comp, map);
      theStrategy = new MinOnTopStrategy(this);
    }

    /**
     * internally used for cloning
     * @param elements	the elements of the new heap
     * @param comp	the comparator used by the new heap 
     *                    (<code>null</code> represents natural order of elements)
     */
    protected MinHeap(List elements, Comparator comp)
    {
      super(elements, comp);
      theStrategy = new MinOnTopStrategy(this);
    }
    // end of constructors

    /**
     * returns a clone of this heap
     * @return	a clone of this heap
     */
    public Object clone()
    {
      return new MinHeap(getElementsClone(), COMP); 
    }
  }

  /**
   * A heap where the element with the highest priority is always on top.
   * More details are explained in Heap.
   * @see Heap
   */
  public static class MaxHeap extends Heap
  {
    /** used only for serialization */
    static final long serialVersionUID = -488572172727019991L;

    // unfortunately Java needs all the constructors to be specifide for each subclass

    /**
     * Constructs an empty heap.<br>
     * @see Heap
     */
    public MaxHeap()
    {
      super();
      theStrategy = new MaxOnTopStrategy(this);
    }

    /**
     * Constructs an empty heap with the specified initial capacity.
     * @param initialCapacity	the initial capacity of the heap
     */
    public MaxHeap(int initialCapacity)
    {
      super(initialCapacity);
      theStrategy = new MaxOnTopStrategy(this);
    }

    /**
     * Constructs a heap containing all elements of the specified collection.
     * @param c	the collection that should inserted into the heap. 
     *            Since no comparator is given, all elements of the collection need to be comparable with each other.
     * @throws ClassCastException	is any element does not implement java.lang.Comparable
     */
    public MaxHeap(Collection c)
    {
      super(c);
      theStrategy = new MaxOnTopStrategy(this);
    }

    /**
     * Constructs an empty heap using a specific comparartor to keep the elements in their order.
     * @param comp	the comparartor to order the elements
     */
    public MaxHeap(Comparator comp)
    {
      super(comp);
      theStrategy = new MaxOnTopStrategy(this);
    }

    /**
     * Constructs an empty heap using a specific comparartor and initial capacity.
     * @param comp		the comparartor to order the elements
     * @param initialCapacity	the initial capacity of the heap
     */
    public MaxHeap(Comparator comp, int initialCapacity)
    {
      super(comp, initialCapacity);
      theStrategy = new MaxOnTopStrategy(this);
    }

    /**
     * Constructs a heap containing all elements of the specified sorted map.
     * @param map		the sorted map that should inserted into the heap 
     *				(will use the comparartor of the sorted map)
     */
    public MaxHeap(SortedMap map)
    {
      super(map);
      theStrategy = new MaxOnTopStrategy(this);
    }

    /**
     * Constructs a heap containing all elements of the specified map using the specified comparator.
     * @param comp	the comparartor to order the elements (if <code>null</code> natural order will be tried)
     * @param map		the map that should inserted into the heap
     */
    public MaxHeap(Comparator comp, Map map)
    {
      super(comp, map);
      theStrategy = new MaxOnTopStrategy(this);
    }

    /**
     * internally used for cloning
     * @param elements	the elements of the new heap
     * @param comp	the comparator used by the new heap 
     *                    (<code>null</code> represents natural order of elements)
     */
    protected MaxHeap(List elements, Comparator comp)
    {
      super(elements, comp);
      theStrategy = new MaxOnTopStrategy(this);
    }
    // end of constructors

    /**
     * returns a clone of this heap
     * @return	a clone of this heap
     */
    public Object clone()
    {
      return new MaxHeap(getElementsClone(), COMP); 
    }
  }

  /**
   * Uses a singleton comparator to adapt the comparison of two 
   * instances of comparable using their own compare method
   * to the interface camparartor.
   * @see java.lang.Comparable
   * @see java.util.Comparator
   */
  protected static class UseNaturalOrder implements Serializable
  {
    /** used only for serialization */
    static final long serialVersionUID = -9130612157088505083L;

    /** singleton instance of the used comparartor */
    private final static Comparator INSTANCE = new Comparator() {
      public int compare(Object o1, Object o2) {
        return ((Comparable) o1).compareTo(o2);
      }
      public boolean equals(Object obj) {
        return (obj == this);
      }
    };

    /**
     * Returns an instance of a comparator that uses the natural order of objects that
     * implement the interface compararble.<br>
     * The attempt to compare objects not satisfying this condition will result in a 
     * ClassCastException.
     * @return		an instance of such a comparartor
     */
    static Comparator getComparator() {
      return INSTANCE;	// return the singleton instance.
    }
  }

  /**
   * Encapsulates the algorithm to arrange the elements for the heap.<BR>
   * This is a abstract template implementation.<BR>
   * @see Heap.MinOnTopStrategy
   * @see Heap.MaxOnTopStrategy
   */
  protected static abstract class HeapStrategy implements Serializable {
    /** used only for serialization */
    static final long serialVersionUID = -5787142770905947059L;

    /** reference to the elements of the heap organized by this strategy */
    protected final List ELEMENTS;

    /** reference to the comparator used by the heap organized by this strategy */
    protected final Comparator COMP;

    protected HeapStrategy(Heap aHeap) {
       this.ELEMENTS = aHeap.elements;
       this.COMP = aHeap.COMP;
    }

    /**
     * returns the index of the first element in the internally used data structure
     * @return	the index of first element
     */
    protected int firstIndex()
    {
      return FIRST_INDEX;
    }

    /**
     * returns the index of the last element in the internally used data structure
     * @return	the index of last element
     */
    protected int lastIndex()
    {
      return (ELEMENTS.size() - 1);
    }

    /**
     * returns the index of the parent node given the index of a child node
     * @param elementIndex	index of a child
     * @return	the index of the prarent for this child, 
     *            <code>NO_SUCH_INDEX</code> if child is the root
     */
    protected int parentIndex(int elementIndex)
    {
      if (elementIndex == firstIndex())
        return NO_SUCH_INDEX;
      return ((elementIndex - 1) / 2);
    }

    /**
     * returns the index of the left child node given the index of a parent node
     * @param elementIndex	index of a parent
     * @return	the index of the left child, 
     *            <code>NO_SUCH_INDEX</code> if node has no left child
     */
    protected int leftChildIndex(int elementIndex)
    {
      int index = (elementIndex * 2) + 1;
      if (index > lastIndex())
        return NO_SUCH_INDEX;
      else
        return (index);
    }

    /**
     * returns the index of the right child node given the index of a parent node
     * @param elementIndex	index of a parent
     * @return	the index of the right child, 
     *            <code>NO_SUCH_INDEX</code> if node has no right child
     */
    protected int rightChildIndex(int elementIndex)
    {
      int index = (elementIndex * 2) + 2;
      if (index > lastIndex())
        return NO_SUCH_INDEX;
      else
        return (index);
    }

    /**
     * changes the position of two elements in the internal data structure
     * @param firstIndex	the index of the first element
     * @param secondIndex	the index of the second element
     * @throws ArrayIndexOutOfBoundsException	if an index <em>x</em> is outside valid range (<em>firstIndex() <= x <= lastIndex()</em>)
     */
    protected void swap(int firstIndex, int secondIndex)
    {
      ELEMENTS.set(firstIndex, ELEMENTS.set(secondIndex, ELEMENTS.get(firstIndex)));
    }

    /**
     * internal function to fix the position of the last element by recursive swapping with its parent (if needed)
     */
    protected void upheapify() {
      upheapify(lastIndex());
    }

    /**
     * internal function to fix the position of an element by recursive swapping with its parent (if needed)
     * @param elementIndex	the index of the child node that will be checked and adjusted
     * @throws ArrayIndexOutOfBoundsException	if an index <em>x</em> is outside valid range (<em>firstIndex() <= x <= lastIndex()</em>)
     */
    protected abstract void upheapify(int elementIndex);

    /**
     * internal function to fix the position of frist element by recursive swapping with its children (if needed)
     */
    protected void downheapify() {
      downheapify(firstIndex());
    }

    /**
     * internal function to fix the position of an element by recursive swapping with its children (if needed)
     * @param elementIndex	the index of the parent node that will be checked and adjusted
     * @throws ArrayIndexOutOfBoundsException	if an index <em>x</em> is outside valid range (<em>firstIndex() <= x <= lastIndex()</em>)
     */
    protected abstract void downheapify(int elementIndex);

    /**
     * returns the first heap element (priority + satellite data)
     * @return	the first heap element
     * @throws NoSuchElementException	if heap is empty
     */
    protected HeapElement firstHeapElement()
    {
      return (HeapElement) ELEMENTS.get(firstIndex());
    }

    /**
     * Removes the first element of the heap and return it.
     * @return	the heap element on top of the heap.
     * @throws NoSuchElementException	if heap is empty
     */
    protected HeapElement removeFirstElement()
    {
      if (ELEMENTS.size() == 1)
        return (HeapElement) ELEMENTS.remove(firstIndex());

      // move last element on top and remember the element that had this position before
      HeapElement result = (HeapElement) ELEMENTS.set( firstIndex(), ELEMENTS.remove(lastIndex()) );
      downheapify();	// make sure that the new first element is in correct position (usually not)
      return result;
    }

    /**
     * Adds a single heap element to the heap.
     * @throws ClassCastException	if new element cannot be compared to the other elements of the heap
     */
    protected void push(HeapElement he)
    {
      ELEMENTS.add(he);		// store at end of list
      upheapify();		// move upwards until correct position is reached
    }

    /**
     * compares two elements given their indices in the iternal data structure
     * @param firstElementIndex	the index of the first element
     * @param secondElementIndex	the index of the second element
     * @return	a negative integer, zero, or a positive integer as the first element's priority is less than, equal to, or greater than the second
     * @throws ClassCastException	if the elements cannot be compared
     * @throws ArrayIndexOutOfBoundsException	if any of the indices is out of valid range
     * @see java.lang.Comparable
     * @see java.util.Comparator
     */
    protected int compare(int firstElementIndex, int secondElementIndex)
    {
      return compare((HeapElement) ELEMENTS.get(firstElementIndex), (HeapElement) ELEMENTS.get(secondElementIndex));
    }

    /**
     * Compares two heap elements. If a comparator has been specified, this will be prefered, else a natural order is assumed.
     * @param firstElement	the first heap element
     * @param secondElement	the second heap element
     * @return	a negative integer, zero, or a positive integer as the first element's priority is less than, equal to, or greater than the second
     * @throws ClassCastException	if the elements cannot be compared
     * @see java.lang.Comparable
     * @see java.util.Comparator
     */
    protected int compare(HeapElement firstElement, HeapElement secondElement)
    {
      // if no comparator has been explicitly specified, it has been set to UseNaturalOrder.getComparator()
      // hence the comparartor can be used and we can avoid case switching if null.
      return COMP.compare(firstElement.priority, secondElement.priority);
    }
  }

  /**
   * Implementation of a <code>HeapStrategy</code> that always puts 
   * the element with the minimal priority on top of the heap.
   * @see Heap
   * @see Heap.HeapStrategy
   */
  protected static class MinOnTopStrategy extends HeapStrategy {
    /** used only for serialization */
    static final long serialVersionUID = 6522139732876557919L;

    protected MinOnTopStrategy(Heap aHeap) {
      super(aHeap);
    }

    /**
     * internal function to fix the position of an element by recursive swapping with its parent (if needed)
     * @param elementIndex	the index of the child node that will be checked and adjusted
     */
    protected void upheapify(int elementIndex)
    {
        int parent = parentIndex(elementIndex);
        if (parent != NO_SUCH_INDEX && compare(parent, elementIndex) > 0)
        { // not in correct order for this heap, correct this
          swap(elementIndex, parent);
          upheapify(parent);
        }
    }

    /**
     * internal function to fix the position of an element by recursive swapping with its children (if needed)
     * @param elementIndex	the index of the parent node that will be checked and adjusted
     */
    protected void downheapify(int elementIndex)
    {
        int smallestChild, otherChild;
        smallestChild = leftChildIndex(elementIndex);
        otherChild = rightChildIndex(elementIndex);
        if (otherChild != NO_SUCH_INDEX && compare(smallestChild, otherChild) > 0)
          smallestChild = otherChild;

        if (smallestChild != NO_SUCH_INDEX && compare(elementIndex, smallestChild) > 0)
        { // not in correct order for this heap, correct this
          swap(elementIndex, smallestChild);
          downheapify(smallestChild);
        }
    }
  }

  /**
   * Implementation of a <code>HeapStrategy</code> that always puts 
   * the element with the maximal priority on top of the heap.
   * @see Heap
   * @see Heap.HeapStrategy
   */
  protected static class MaxOnTopStrategy extends HeapStrategy {
    /** used only for serialization */
    static final long serialVersionUID = 428621450693357202L;

    protected MaxOnTopStrategy(Heap aHeap) {
      super(aHeap);
    }

    /**
     * internal function to fix the position of an element by recursive swapping with its parent (if needed)
     * @param elementIndex	the index of the child node that will be checked and adjusted
     */
    protected void upheapify(int elementIndex)
    {
        int parent = parentIndex(elementIndex);
        if (parent != NO_SUCH_INDEX && compare(elementIndex, parent) > 0)
        { // not in correct order for this heap, correct this
          swap(elementIndex, parent);
          upheapify(parent);
        }
    }

    /**
     * internal function to fix the position of an element by recursive swapping with its children (if needed)
     * @param elementIndex	the index of the parent node that will be checked and adjusted
     */
    protected void downheapify(int elementIndex)
    {
        int greatestChild, otherChild;
        greatestChild = leftChildIndex(elementIndex);
        otherChild = rightChildIndex(elementIndex);
        if (otherChild != NO_SUCH_INDEX && compare(otherChild, greatestChild) > 0)
          greatestChild = otherChild;

        if (greatestChild != NO_SUCH_INDEX && compare(greatestChild, elementIndex) > 0)
        { // not in correct order for this heap, correct this
          swap(elementIndex, greatestChild);
          downheapify(greatestChild);
        }
    }
  }
}  
