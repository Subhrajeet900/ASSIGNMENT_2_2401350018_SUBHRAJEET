// name - subhrajeet dash 
// course - btech cse fsd 
// subject - data stucture 
// =============================
import java.util.*;

// ------------------ PATIENT DATA CLASS ------------------
class Patient {
    int id;
    String name;
    String date;
    String treatment;

    Patient(int id, String name, String date, String treatment) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.treatment = treatment;
    }

    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Date: " + date + ", Treatment: " + treatment;
    }
}

// ------------------ LINKED LIST FOR PATIENT RECORDS ------------------
class PatientLinkedList {
    class Node {
        Patient data;
        Node next;
        Node(Patient p) { data = p; }
    }

    Node head;

    void insert(Patient p) {
        Node n = new Node(p);
        n.next = head;
        head = n;
    }

    boolean delete(int id) {
        Node prev = null, curr = head;

        while (curr != null) {
            if (curr.data.id == id) {
                if (prev == null) head = curr.next;
                else prev.next = curr.next;
                return true;
            }
            prev = curr;
            curr = curr.next;
        }
        return false;
    }

    Patient retrieve(int id) {
        Node temp = head;
        while (temp != null) {
            if (temp.data.id == id) return temp.data;
            temp = temp.next;
        }
        return null;
    }

    void printAll() {
        Node temp = head;
        while (temp != null) {
            System.out.println(temp.data);
            temp = temp.next;
        }
        if (head == null) System.out.println("No patient records.");
    }
}

// ------------------ STACK (UNDO FEATURE) ------------------
class UndoStack {
    Stack<Patient> stk = new Stack<>();

    void push(Patient p) { stk.push(p); }

    Patient pop() {
        if (!stk.isEmpty()) return stk.pop();
        return null;
    }
}

// ------------------ CIRCULAR QUEUE (EMERGENCY FIFO) ------------------
class EmergencyQueue {
    Patient[] arr;
    int front = 0, rear = -1, size = 0;

    EmergencyQueue(int cap) { arr = new Patient[cap]; }

    boolean enqueue(Patient p) {
        if (size == arr.length) return false;
        rear = (rear + 1) % arr.length;
        arr[rear] = p;
        size++;
        return true;
    }

    Patient dequeue() {
        if (size == 0) return null;
        Patient p = arr[front];
        front = (front + 1) % arr.length;
        size--;
        return p;
    }
}

// ------------------ BILLING (POLYNOMIAL) ------------------
class Billing {
    // basic cost formula = base + (days * perDay) + (days^2 * intensity)
    long calculate(int days, int base, int perDay, int intensity) {
        return base + (days * perDay) + (days * days * intensity);
    }
}

// ------------------ POSTFIX CALCULATOR (INVENTORY) ------------------
class PostfixCalc {
    int eval(String exp) {
        Stack<Integer> s = new Stack<>();
        for (String c : exp.split(" ")) {
            if (c.matches("-?\d+")) s.push(Integer.parseInt(c));
            else {
                int b = s.pop();
                int a = s.pop();
                switch (c) {
                    case "+": s.push(a + b); break;
                    case "-": s.push(a - b); break;
                    case "*": s.push(a * b); break;
                    case "/": s.push(a / b); break;
                }
            }
        }
        return s.pop();
    }
}

// ------------------ MAIN SYSTEM ------------------
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PatientLinkedList list = new PatientLinkedList();
        UndoStack undo = new UndoStack();
        EmergencyQueue eq = new EmergencyQueue(10);
        Billing bill = new Billing();
        PostfixCalc calc = new PostfixCalc();

        while (true) {
            System.out.println("
--- HOSPITAL MANAGEMENT MENU ---");
            System.out.println("1. Add Patient");
            System.out.println("2. Delete Patient");
            System.out.println("3. Retrieve Patient");
            System.out.println("4. View All Patients");
            System.out.println("5. Undo Last Add");
            System.out.println("6. Emergency Queue (enqueue/dequeue)");
            System.out.println("7. Billing Calculation");
            System.out.println("8. Inventory Calculation (postfix)");
            System.out.println("0. Exit");
            System.out.print("Enter Choice: ");

            int ch = sc.nextInt(); sc.nextLine();

            if (ch == 1) {
                System.out.print("ID: "); int id = sc.nextInt(); sc.nextLine();
                System.out.print("Name: "); String name = sc.nextLine();
                System.out.print("Date: "); String date = sc.nextLine();
                System.out.print("Treatment: "); String tr = sc.nextLine();

                Patient p = new Patient(id, name, date, tr);
                list.insert(p);
                undo.push(p);
                System.out.println("Added.");
            }
            else if (ch == 2) {
                System.out.print("Enter ID to delete: ");
                int id = sc.nextInt();
                System.out.println(list.delete(id) ? "Deleted." : "Not found.");
            }
            else if (ch == 3) {
                System.out.print("Enter ID to retrieve: ");
                int id = sc.nextInt();
                Patient p = list.retrieve(id);
                System.out.println(p == null ? "Not found." : p);
            }
            else if (ch == 4) list.printAll();

            else if (ch == 5) {
                Patient p = undo.pop();
                if (p != null) list.delete(p.id);
                System.out.println("Undo complete.");
            }
            else if (ch == 6) {
                System.out.println("1-Enqueue  2-Dequeue");
                int op = sc.nextInt(); sc.nextLine();
                if (op == 1) {
                    System.out.print("ID: "); int id = sc.nextInt(); sc.nextLine();
                    eq.enqueue(new Patient(id, "Emergency", "NA", "Critical"));
                    System.out.println("Emergency added.");
                } else System.out.println("Removed: " + eq.dequeue());
            }
            else if (ch == 7) {
                System.out.print("Days: "); int d = sc.nextInt();
                System.out.print("Base Fee: "); int b = sc.nextInt();
                System.out.print("Per-Day Fee: "); int pd = sc.nextInt();
                System.out.print("Intensity (quadratic): "); int q = sc.nextInt();
                System.out.println("TOTAL BILL = " + bill.calculate(d, b, pd, q));
            }
            else if (ch == 8) {
                System.out.print("Enter postfix (ex: 10 2 * 5 +): ");
                String exp = sc.nextLine();
                System.out.println("Result = " + calc.eval(exp));
            }
            else if (ch == 0) break;
        }
    }
}
