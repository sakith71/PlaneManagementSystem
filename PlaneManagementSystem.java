import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * The PlaneManagement class of the Plane Management application.
 * This class contains the main method which serves as the entry point for the application.
 * It initialises the seating arrangement and ticket array, and provides menu options to the user.
 */
public class PlaneManagementSystem {
    /**
     * Jagged array representing the seating arrangement.
     * Each element stores an integer representing the status of a seat (0 for available, 1 for sold/cancelled).
     * The first dimension represents the rows, and the second dimension represents the seats in each row.
     */
    public static int[][] seats = new int[4][];
    static {
        // Initialization of seating arrangement
        seats[0] = new int[14];
        seats[1] = new int[12];
        seats[2] = new int[12];
        seats[3] = new int[14];
    }

    /**
     * Array storing ticket objects.
     * Each element represents a ticket purchased by a passenger.
     */
    public static Ticket[] tickets = new Ticket[52];

    /**
     * Variables to store column number, row letter number, and ticket index.
     */
    public static int colNumber, rowLetterNum, ticketIndex = 0;

    /**
     * Variables to store row letter, passenger name, surname, email, and ticket price.
     */
    public static String rowLetter, name, surname, email;
    public static double price;

    /**
     * The main method of the Plane Management application.
     * Initializes the seating arrangement and ticket array, and provides menu options to the user.
     * The menu allows users to buy a seat, cancel a seat, find the first available seat,
     * show the seating plan, print ticket information and total sales, search for a ticket, and quit the application.
     * Input validation is performed to ensure that only valid integer choices are accepted.
     *
     * @param args The command-line arguments passed to the application (not used).
     */
    public static void main(String[] args) {

        // Display welcome message and menu options
        System.out.println(" ");
        System.out.println("\tWelcome to the Plane Management application");
        boolean isValid = true;
        while (isValid) {
            System.out.println("*".repeat(50));
            System.out.println("*" + " ".repeat(18) + "MENU OPTIONS" + " ".repeat(18) + "*");
            System.out.println("*".repeat(50));
            System.out.println("\t\t1) Buy a seat");
            System.out.println("\t\t2) Cancel a seat");
            System.out.println("\t\t3) Find first available seat");
            System.out.println("\t\t4) Show seating plan");
            System.out.println("\t\t5) Print ticket information and total Sales");
            System.out.println("\t\t6) Search ticket");
            System.out.println("\t\t0) Quit");
            System.out.println("*".repeat(50));
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Please select an option: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        buySeat();
                        break;
                    case 2:
                        cancelSeat();
                        break;
                    case 3:
                        findFirstAvailable();
                        break;
                    case 4:
                        showSeatingPlan();
                        break;
                    case 5:
                        printTicketInfo();
                        break;
                    case 6:
                        searchTicket();
                        break;
                    case 0:
                        System.out.println();
                        isValid = false;
                        break;
                    default:
                        System.out.println("Please enter valid option.");
                        break;
                }
            } catch (InputMismatchException ex) {
                System.out.println("WARNING!..... Please enter a valid integer.");
            }
        }
    }

    /**
     * Allows the user to select a seat by inputting a row letter and seat number.
     * Input validation is performed for the row letter and seat number.
     * If an invalid row letter or seat number is entered, appropriate error messages are displayed.
     */
    public static void select(){
        boolean isValid = true;
        while (isValid) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter a row letter: ");
            rowLetter = scanner.next().toUpperCase();
            if (rowLetter.length() == 1) {
                rowLetterNum = rowLetter.charAt(0);
                if (rowLetterNum > 64 && rowLetterNum < 69) {
                    isValid = false;
                }else
                    System.out.println("Invalid row letter. Please enter valid row letter.");
            }else
                System.out.println("Invalid row letter. Please enter valid row letter.");
        }
        while (!isValid) {
            try {
                Scanner scanner1 = new Scanner(System.in);
                System.out.print("Enter seat number: ");
                colNumber = scanner1.nextInt();
                if (colNumber > 0 && colNumber < 15) {
                    if ((rowLetterNum == 66 || rowLetterNum == 67) && (colNumber == 13 || colNumber == 14)){
                        System.out.println("Invalid seat number. Please enter valid seat number.");
                    }else isValid = true;
                } else {
                    System.out.println("Invalid seat number. Please enter valid seat number.");
                }
            } catch (
                    InputMismatchException ex) {
                System.out.println("WARNING!..... Please enter valid integer.");
            }
        }
    }

    /**
     * Allows a user to purchase a seat.
     * The user is prompted to select a seat and input their name, surname, and email address.
     * Input validation is performed for the name, surname, and email address.
     * If the selected seat is already sold, an appropriate message is displayed.
     * Upon successful purchase, a Ticket object created and saved, and the seat marked as sold.
     */
    public static void buySeat() {
        select(); // Selecting a seat

        // Checking if the seat is already sold
        if (seats[(rowLetterNum - 64) - 1][colNumber - 1] == 1) {
            System.out.println("This seat is already sold.");
        } else {
            Scanner scanner = new Scanner(System.in);

            // Inputting name
            System.out.print("Enter your first name: ");
            while (true) {
                name = scanner.nextLine().toUpperCase();
                if (!name.matches("[A-Z]*$")) {
                    System.out.println("Invalid name");
                    System.out.print("Enter your name again: ");
                } else break;
            }

            // Inputting surname
            System.out.print("Enter your surname: ");
            while (true) {
                surname = scanner.nextLine().toUpperCase();
                if (!surname.matches("[A-Z]*$")) {
                    System.out.println("Invalid surname");
                    System.out.print("Enter your surname again: ");
                } else break;
            }

            // Inputting email
            while (true) {
                System.out.print("Enter your email: ");
                email = scanner.nextLine();
                if (email.matches("^[a-z0-9]+(?:\\.[a-z0-9]+)*@(?:[a-z]+\\.)+[a-z]{2,7}$")) {
                    break;
                } else
                    System.out.println("Invalid email. Please enter email again.");
            }

            // Creating Person object
            Person person = new Person(name, surname, email);

            // Calculating ticket price
            price = ticketPrice();

            // Creating Ticket object
            Ticket ticket = new Ticket(rowLetter, colNumber, price, person);
            tickets[ticketIndex] = ticket;
            ticketIndex++;
            ticket.createFile();

            // Marking seat as sold
            seats[(rowLetterNum - 64) - 1][colNumber - 1] = 1;
            System.out.println("This seat was sold successfully");

        }
    }

    /**
     * Cancels the reservation of a seat.
     * If the seat is not already cancelled, it marks the seat as cancelled,
     * removes the associated ticket, and deletes the ticket file.
     */
    public static void cancelSeat() {
        select();
        // Checking if the seat is already canceled
        if (seats[(rowLetterNum - 64) - 1][colNumber - 1] == 0) {
            System.out.println("This seat is already canceled.");
        } else {
            // Iterating through the tickets array to find the ticket associated with the seat
            for (int i = 0; i < ticketIndex; i++) {
                if (tickets[i] != null && tickets[i].getRow().equals(rowLetter) && tickets[i].getSeat() == colNumber) {
                    // Removing the ticket from the tickets array
                    tickets[i] = null;
                    // Creating Person object
                    Person person = new Person(name, surname, email);
                    // Creating Ticket object
                    Ticket ticket = new Ticket(rowLetter, colNumber, price, person);
                    // Deleting the ticket file
                    ticket.deleteFile();
                    // Marking the seat as canceled
                    seats[(rowLetterNum - 64) - 1][colNumber - 1] = 0;
                    System.out.println("This seat was canceled successfully.");
                }
            }
        }
    }

    /**
     * Find the first available seat on the plane.
     * Prints the row letter and seat number of the available seat.
     */
    public static void findFirstAvailable() {
        for (int row = 0; row < seats.length; row++) {
            for (int col = 0; col < seats[row].length; col++) {
                if (seats[row][col] == 0) {
                    // Converting the row index to a row letter
                    String rowLetter = (row == 0) ? "A" : (row == 1) ? "B" : (row == 2) ? "C" : "D";
                    // Printing the available seat
                    System.out.println("Available seat is: "+rowLetter + (col + 1));
                    return;
                }
            }
        }
    }

    /**
     * Calculates the price of a ticket based on the selected seat column number.
     *
     * @return the price of the ticket
     */
    public static double ticketPrice() {
        // Determining the ticket price based on the selected seat column number
        if (colNumber > 0 && colNumber < 6) {
            return 200;
        } else if (colNumber > 5 && colNumber < 10) {
            return 150;
        } else {
            return 180;
        }
    }

    /**
     * Displays the seating plan of the plane.
     * Prints the seating arrangement matrix, where each cell represents a seat.
     * 'O' indicates an available seat, and 'X' indicates a sold seat.
     */
    public static void showSeatingPlan() {
        // Printing seat numbers
        for (int i = 0; i < seats[0].length; i++) {
            System.out.print("\t" + (i + 1));
        }
        System.out.println();

        // Iterating over each row of the seating arrangement matrix
        int n = 0;
        for (int[] seatRow : seats) {
            // Printing row letters
            System.out.print((char) ('A' + n) + ": ");
            n++;

            // Printing seat status (O for available, X for sold) for each seat in the row
            for (int status : seatRow) {
                System.out.print(status == 0 ? "\tO" : "\tX");
            }
            System.out.println();
        }
    }

    /**
     * Prints ticket information and total sales during the session.
     * If no tickets were sold, it prints a message indicating that.
     * Otherwise, it displays the details of each ticket sold and calculates the total sales.
     */
    public static void printTicketInfo() {
        double totalTicket = 0;
        boolean isValid = true;
        System.out.println("Tickets sold during the session:");
        for (Ticket ticket : tickets) {
            if (ticket != null) {
                ticket.ticketInfo(); // Printing ticket information
                totalTicket += ticket.getPrice(); // Calculating total sales
                isValid = false;
            }
        }
        if (isValid) {
            System.out.println("No tickets sold during the session.");
        } else {
            System.out.println("Total price: £" + totalTicket);
        }
    }

    /**
     * Searches for a ticket based on the selected row letter and seat number.
     * If a ticket is found for the specified seat, it displays the ticket information.
     * Otherwise, it informs the user that the seat is available.
     */
    public static void searchTicket() {
        select(); // Selecting a seat

        boolean isValid = true;
        // Searching for the ticket in the tickets array
        for (Ticket ticket : tickets) {
            if (ticket != null && ticket.getRow().equals(rowLetter) && ticket.getSeat() == colNumber) {
                isValid = false;
                ticket.ticketInfo(); // Displaying ticket information
            }
        }
        if (isValid) {
            System.out.println("This seat is available.");
        }
    }
}

// code line 230 without comment
