import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Reporting {

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Please enter username and password when running this program.");
            System.exit(0);
        }
        else if (args.length == 2) {
            System.out.println();
            System.out.println("1 - Report Patients Basic Information");
            System.out.println("2 - Report Doctors Basic Information");
            System.out.println("3 - Report Admissions Information");
            System.out.println("4 - Update Admissions Payment");
            System.out.println();
            System.exit(0);
        }
        else if (args[2].equals("1")) {
            System.out.println("Enter Patient SSN:");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String SSN = reader.readLine();
            System.out.println("You entered: " + SSN);

            // TODO program needs to execute a query here over the patient table and print the following:
                // Patient SSN:
                // Patient First Name:
                // Patient Last Name:
                // Patient Address:

            reader.close();
            System.exit(0);
        }
        else if (args[2].equals("2")) {
            System.out.println("Enter Doctor ID:");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String doctorID = reader.readLine();
            System.out.println("You entered: " + doctorID);

            // TODO program needs to execute a query here over the doctor table and print the following:
                // Doctor ID:
                // Doctor First Name:
                // Doctor Last Name:
                // Doctor Gender:
                // Doctor Graduated From:
                // Doctor Specialty:

            reader.close();
            System.exit(0);
        }
        else if (args[2].equals("3")) {
            System.out.println("Enter Admission Number:");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String admissionNumber = reader.readLine();
            System.out.println("You entered: " + admissionNumber);

            // TODO program needs to execute a query here over the admission table and print the following:
                // TODO the Doctor IDS printed need to be unique (no repeats)

                // Admission Number:
                // Patient SSN:
                // Admission Date (start date):
                // Total Payment:
                // Rooms:
                    // RoomNum:     FromDate:     ToDate:
                    // RoomNum:     FromDate:     ToDate:
                    // RoomNum:     FromDate:     ToDate:
                // Doctors examined the patient in this admission:
                    // Doctor ID:
                    // Doctor ID:
                    // Doctor ID:

            reader.close();
            System.exit(0);
        }
        else if (args[2].equals("4")) {
            System.out.println("Enter Admission Number:");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String admissionNumber = reader.readLine();
            System.out.println("You entered: " + admissionNumber);

            System.out.println();

            System.out.println("Enter the new total payment:");
            String newTotalPayment = reader.readLine();
            System.out.println("You entered: " + newTotalPayment);

            // TODO program needs to execute a query that updates the total payment for the given admissions number
            // TODO good to check this with option 3 (execute with this admissions num to see if total payment has been updated)

            reader.close();
            System.exit(0);
        }
    }

}
