package linkedmerge;

class LinkedList {
    
    private Node first;
    private int size;
    
    private class Node {
        int data;
        Node next;
        
        public int compareTo(Node b) {
            if(data < b.data) return -1;
            else if(data > b.data) return 1;
            else return 0;
        }
    }
    
    public LinkedList() 
    {  size = 0;  }
    
    private boolean isEmpty() 
    {  return size == 0;  }
    
    public void add(int item) {
        if( !isEmpty() ) {
            Node oldfirst = first;
            first = new Node();
            first.next = oldfirst;
            first.data = item;
            size++;
        } else {
            first = new Node();
            first.data = item;
            size++;
        }
    }
    
    public int remove() {               // removes first node
        if( !isEmpty() ) {
            int temp = first.data;
            first = first.next;
            size--;
            return temp;
        } else return 0;
    }
    
    public void printList() {
        if( !isEmpty() ) {
            System.out.println("Stack contents: ");
            Node temp = first;
            while(temp != null) {
                System.out.print(temp.data + " ");
                temp = temp.next;
            }
            System.out.println();
        } else System.out.println("Stack is empty.");
    }
    
    // Merge sort for natural runs
    public void sort() {
        Node i, mid, j, hi, last;
        int groups = 1;
        
        for(Node x=first ; x.next!=null; x=x.next)              // find out # of runs
            if(less(x.next, x)) groups++;
        
        i = first; mid = i;
        while(groups > 1) {     // merge until there's just a single run
            while(mid.next != null && !less(mid.next, mid))      // 1st run of set
                mid = mid.next;                                 // (i to mid)
        
            if (mid.next != null)  { j = mid.next; hi = j; }    // get next run
            else { i = first; mid = i; continue; }              // or start over
        
            while(hi.next != null && !less(hi.next, hi))         // 2nd run of set
                hi = hi.next;                                   // (j to hi)
            
            last = merge(i, mid, j, hi);                        // merge 2 runs
            groups--;                                           // decr. # of runs
            
            if(last.next != null) i = last.next;        // keep going w next set
            else i = first;                             // or start over 
            mid = i;
        }
    }
    
    private Node merge(Node i, Node mid, Node j, Node hi) {
        if(less(j, i)) {                // in case j is less than i at beginning
            Node insert = new Node();
            insert.data = j.data;
            insert.next = i.next;
            i.next = insert;
            exch(i, insert);               // as though insert was placed before i
            j = j.next;                 // move j pointer to next node
            if(i == mid) mid = insert;     // adjust mid if i equal to mid
            mid.next = j;
        }
        while(j != null) {
            if(j == hi.next) break;             // j exhausted
            else if(i == mid.next) break;       // i exhausted
            else if(less(j, i.next)) {          // find a home for j if he fits
                Node insert = new Node();
                insert.data = j.data;
                insert.next = i.next;
                i.next = insert;                   
                i = i.next;                     // move i pointer to next node    
                j = j.next;                     // move j pointer to next node
                mid.next = j;
            }
            else i = i.next;                    // move i pointer to next node
        }
        if(j == null) return mid;               // return last node of the merge
        else if(j == hi.next) return mid;
        else return hi;
    }
    
    private boolean less(Node a, Node b) {
        return a.compareTo(b) < 0;
    }
    private void exch(Node a, Node b) {
        int temp;   // or generic type
        temp = a.data;  a.data = b.data;  b.data = temp;
    }
}

public class LinkedMerge {

    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        
//        list.add(11); list.add(4);
//        list.add(14);
//        list.add(17);
//        list.add(24); list.add(23); list.add(19); list.add(12);
//        list.add(13); list.add(6);
//        list.add(21); list.add(11); list.add(7);        
//        list.add(16);
//        list.add(21); list.add(18); list.add(9);
//        list.add(22); list.add(8); list.add(5);
//        list.add(20); list.add(15); list.add(10);
        
        for(int i=0; i<99; i++)
            list.add( (int) (Math.random() * 500) );
        
        list.printList();
        list.sort();
        list.printList();
    }
}
/*
Stack contents: 
10 15 20 5 8 22 9 18 21 16 7 11 21 6 13 12 19 23 24 17 14 4 11 
Stack contents: 
4 5 6 7 8 9 10 11 11 12 13 14 15 16 17 18 19 20 21 21 22 23 24
*/