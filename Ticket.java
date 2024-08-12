import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Represents a ticket with information about the seat, price, and person.
 */
public class Ticket {

    private String row;
    private int seat;
    private double price;
    private Person person;

    /**
     * Constructs a ticket with the specified row, seat number, price, and person.
     *
     * @param letter the row letter of the seat
     * @param number the seat number
     * @param price  the price of the ticket
     * @param person the person associated with the ticket
     */
    public Ticket(String letter, int number, double price, Person person) {
        this.row = letter;
        this.seat = number;
        this.price = price;
        this.person = person;
    }

    /**
     * Retrieves the row letter of the ticket.
     *
     * @return the row letter of the ticket
     */
    public String getRow() {
        return row;
    }

    /**
     * Sets the row letter of the ticket.
     *
     * @param row the new row letter of the ticket
     */
    public void setRow(String row) {
        this.row = row;
    }

    /**
     * Retrieves the seat number of the ticket.
     *
     * @return the seat number of the ticket
     */
    public int getSeat() {
        return seat;
    }

    /**
     * Sets the seat number of the ticket.
     *
     * @param seat the new seat number of the ticket
     */
    public void setSeat(int seat) {
        this.seat = seat;
    }

    /**
     * Retrieves the price of the ticket.
     *
     * @return the price of the ticket
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the ticket.
     *
     * @param price the new price of the ticket
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Retrieves the person associated with the ticket.
     *
     * @return the person associated with the ticket
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Sets the person associated with the ticket.
     *
     * @param person the new person associated with the ticket
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * Prints information about the ticket including row, seat, price, and personal information of the person.
     */
    public void ticketInfo() {
        System.out.println("Ticket Information:");
        System.out.println("\tRow: " + getRow());
        System.out.println("\tSeat: " + getSeat());
        System.out.println("\tPrice: £" + getPrice());
        person.personalInfo();
        System.out.println("-".repeat(50));
    }


    /**
     * Creates a text file containing ticket information.
     * The file name is generated based on the row and seat number of the ticket.
     * The ticket information, including row, seat, price, and person information, is written to the file.
     * If the file creation is successful, a confirmation message is printed.
     * If an error occurs during file creation, an error message is printed.
     */
    public void createFile() {
        String filename = getRow() + getSeat() + ".txt";
        try {
            FileWriter myWriter = new FileWriter(filename);

            myWriter.write("Row: " + getRow() + "\n");
            myWriter.write("Seat: " + getSeat() + "\n");
            myWriter.write("Price: " + getPrice() + "\n");
            myWriter.write("Person Information:\n");
            myWriter.write("Name: " + getPerson().getName() + "\n");
            myWriter.write("Surname: " + getPerson().getSurname() + "\n");
            myWriter.write("Email: " + getPerson().getEmail() + "\n");
            myWriter.close();
            System.out.println("Ticket information saved to " + filename);
        } catch (IOException e) {
            System.out.println("An error occurred while saving ticket information to " + filename);
            // e.printStackTrace();
        }
    }

    /**
     * Deletes the text file associated with the ticket.
     * The file name is based on the row and seat number of the ticket.
     * If the file exists, it is deleted, and a confirmation message is printed.
     * If the file does not exist, a message indicating its absence is printed.
     * If an error occurs during file deletion, an error message is printed.
     */
    public void deleteFile() {
        String filename = getRow() + getSeat() + ".txt";
        File file = new File(filename);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Deleted the file: " + filename);
            } else {
                System.out.println("Failed to delete the file: " + filename);
            }
        } else {
            System.out.println("File does not exist: " + filename);
        }
    }
}
